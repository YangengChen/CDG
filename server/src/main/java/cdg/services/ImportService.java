package cdg.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.geojson.Geometry;
import org.wololo.jts2geojson.GeoJSONReader;

import com.vividsolutions.jts.geom.TopologyException;

import cdg.repository.StateRepository;
import cdg.repository.DistrictRepository;
import cdg.repository.PrecinctRepository;
import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;
import cdg.dao.State;
import cdg.properties.CdgConstants;
import cdg.properties.CdgPropertiesManager;

@Service
public class ImportService {

	@Autowired
	StateRepository stateRepo;
	@Autowired
	DistrictRepository districtRepo;
	@Autowired
	PrecinctRepository precinctRepo;
	@Autowired
	CdgPropertiesManager propertiesManager;
	
	public State createState(String geoJSON) {
		if (geoJSON == null) {
			return null;
		}
 		State state;
		try {
			String annotatedGeoJSON = annotateGeoJSONNeighbors(geoJSON);
			FeatureCollection stateFeatures = (FeatureCollection)GeoJSONFactory.create(annotatedGeoJSON);
			Feature[] features = stateFeatures.getFeatures();
			
			String stateName = (String)features[0].getProperties().get(propertiesManager.getProperty(CdgConstants.STATE_NAME_FIELD));
			String statePubID = (String)features[0].getProperties().get(propertiesManager.getProperty(CdgConstants.STATE_IDENTIFIER_FIELD));
			state = generateState(stateName, statePubID, annotatedGeoJSON);
		
			Map<Integer,CongressionalDistrict> districts = new HashMap<Integer,CongressionalDistrict>();
			Map<Integer,Precinct> precincts = new HashMap<Integer,Precinct>();
			generatePrecinctsAndDistricts(features, state, districts, precincts);
			generateNeighbors(features, precincts);
			state.setConDistricts(districts);
			state.setPrecincts(precincts);
			
			setGeometries(state);
			setMaps(state);
			
			//store to database and use returned state value - will generate all mappings
			state = stateRepo.saveAndFlush(state);
		} catch (Exception e) {
			//remove any partial data from database
			System.err.print(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return state;
	}
	
	private State generateState(String name, String publicID, String geoJSON) {
		State state = new State();
		state.setName(name);
		state.setPublicID(publicID);
		//fake
		state.setPrecinctMapGeoJson(geoJSON);
		
		//store to database and use returned state value
		state = stateRepo.saveAndFlush(state);
		return state;
	}
	
	private void generatePrecinctsAndDistricts(Feature[] features, State state, Map<Integer,CongressionalDistrict> districts, Map<Integer,Precinct> precincts) {
		Map<String,CongressionalDistrict> districtsPubID = new HashMap<String,CongressionalDistrict>();
		Map<String,Object> currProp;
		Geometry currGeom;
		Precinct currPrecinct;
		CongressionalDistrict currDistrict;
		String currDistPubID;
		for (int i = 0; i < features.length; i++) {
			currProp = features[i].getProperties();
			currGeom = features[i].getGeometry();
			currPrecinct = new Precinct();
			currPrecinct.setName((String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_NAME_FIELD)));
			currPrecinct.setPublicID((String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_IDENTIFIER_FIELD)));
			currPrecinct.setGeoJsonGeometry(currGeom.toString());
			
			currDistPubID = (String)currProp.get(propertiesManager.getProperty(CdgConstants.DISTRICT_IDENTIFIER_FIELD));
			currDistrict = districtsPubID.get(currDistPubID);
			if (currDistrict == null) {
				currDistrict = new CongressionalDistrict();
				currDistrict.setName(CdgConstants.DISTRICT_NAME_PREFIX + currDistPubID);
				currDistrict.setPublicID(currDistPubID);
				//store to database and use returned district value
				currDistrict = districtRepo.saveAndFlush(currDistrict);
				districtsPubID.put(currDistrict.getPublicID(), currDistrict);
				districts.put(currDistrict.getId(), currDistrict);
			}
			
			//store to database and use returned precinct value
			currPrecinct = precinctRepo.saveAndFlush(currPrecinct);
			precincts.put(currPrecinct.getId(), currPrecinct);
			
			currPrecinct.setConDistrict(currDistrict);
			currPrecinct.setState(state);
			currDistrict.getPrecincts().put(currPrecinct.getId(), currPrecinct);
			currDistrict.setState(state);
		}
	}

	private void generateNeighbors(Feature[] features, Map<Integer,Precinct> precincts) {
		if (features == null || precincts == null || features.length != precincts.size()) {
			throw new IllegalArgumentException();
		}
		Map<String,Precinct> precinctsPubID = new HashMap<String,Precinct>();
		for (Precinct precinct : precincts.values()) {
			precinctsPubID.put(precinct.getPublicID(), precinct);
		}
		Map<String,Object> currProp;
		List<String> currNeighbors;
		int i = 0;
		for (Precinct precinct : precincts.values()) {
			currProp = features[i++].getProperties();
			currNeighbors = (List<String>)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_NEIGHBORS_FIELD)); //public keys of neighbors
			
			Precinct neighbor;
			boolean valid;
			for (int j = 0; j < currNeighbors.size(); j++) {
				neighbor = precinctsPubID.get(currNeighbors.get(j));
				valid = validateNeighbor(precinct, neighbor);
				if (!valid) {
					/* Approximate neighbors from script - invalid neighbor means the neighbor isn't considered
					 * a neighbor by JTS, so just continue loop without creating neighbor association.
					 * If an error is thrown, this means that JTS finds the geometry invalid, so the state
					 * cannot be imported.*/
					continue;
				}
				precinct.getNeighborRegions().put(neighbor.getId(), neighbor);
			}
		}
	}
	
	private boolean validateNeighbor(Precinct curr, Precinct neighbor) {
		if (curr == null || neighbor == null) {
			throw new IllegalArgumentException();
		}
		String currGeomStr = curr.getGeoJsonGeometry();
		String neighborGeomStr = neighbor.getGeoJsonGeometry();
		if (currGeomStr == null || neighborGeomStr == null) {
			throw new IllegalArgumentException();
		}
		
		GeoJSONReader reader = new GeoJSONReader();
		com.vividsolutions.jts.geom.Geometry currGeom = null;
		com.vividsolutions.jts.geom.Geometry neighborGeom = null;
		try {
			currGeom = reader.read(currGeomStr);
			neighborGeom = reader.read(neighborGeomStr);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		if (!(currGeom instanceof com.vividsolutions.jts.geom.Polygonal) || !(neighborGeom instanceof com.vividsolutions.jts.geom.Polygonal)) {
			throw new IllegalArgumentException();
		}
		boolean valid = false;
		try {
			valid = currGeom.intersects(neighborGeom); 		//see JTS Geometry "intersects" definition
		} catch (TopologyException te) {
			throw new IllegalArgumentException();
		}
		return valid;
	}
	
	private void setMaps(State state) {
		if (state == null || state.getConDistricts() == null) {
			throw new IllegalArgumentException();
		}
		String congressionalDistrictMap = MapService.generateCongressionalDistrictMap(state, true);
		state.setCongressionalMapGeoJson(congressionalDistrictMap);
		String stateMap = MapService.generateStateMap(state);
		state.setStateMapGeoJson(stateMap);
	}
	
	private void setGeometries(State state) {
		if (state == null || state.getConDistricts() == null) {
			throw new IllegalArgumentException();
		}
		setDistrictsGeoJSON(state.getConDistricts().values());
		setStateGeoJSON(state);
	}
	
	private void setDistrictsGeoJSON(Collection<CongressionalDistrict> districts) {
		if (districts == null) {
			throw new IllegalArgumentException();
		}
		com.vividsolutions.jts.geom.Geometry districtGeom;
		String districtGeoJson;
		for (CongressionalDistrict district : districts) {
			districtGeom = MapService.createDistrictGeometry(district);
			districtGeoJson = MapService.convertToGeoJSONGeometry(districtGeom);
			district.setGeoJsonGeometry(districtGeoJson);
		}
	}
	
	private void setStateGeoJSON(State state) {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		com.vividsolutions.jts.geom.Geometry stateGeom = MapService.createStateGeometry(state);
		String stateGeoJson = MapService.convertToGeoJSONGeometry(stateGeom);
		state.setGeoJsonGeometry(stateGeoJson);
	}
	

	private String annotateGeoJSONNeighbors(String geoJSON) {
		if (geoJSON == null) {
			throw new IllegalArgumentException();
		}
		ScriptEngine engine = new ScriptEngineManager().getEngineByName(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_ENGINE);
		Bindings bindings = engine.createBindings();
		bindings.put(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_JSON_BINDING, geoJSON);
		bindings.put(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_CLIENT_BINDING, CdgConstants.TOPOJSON_CLIENT_SCRIPT_PATH);
		bindings.put(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_SERVER_BINDING, CdgConstants.TOPOJSON_SERVER_SCRIPT_PATH);
		bindings.put(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_NEIGHBOR_KEY_BINDING, propertiesManager.getProperty(CdgConstants.PRECINCT_NEIGHBORS_FIELD));
		bindings.put(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_PRECINCT_KEY_BINDING, propertiesManager.getProperty(CdgConstants.PRECINCT_IDENTIFIER_FIELD));
		Reader script = null;
		String result = null;
		try {
			Resource resource = new ClassPathResource(CdgConstants.NEIGHBOR_ANNOTATION_SCRIPT_PATH);
			script = new InputStreamReader(resource.getInputStream());
			result = (String)engine.eval(script,bindings);
			if (result == null) {
				throw new IllegalArgumentException();
			}
		} catch (IOException ioe) {
			throw new IllegalStateException();
		} catch (ScriptException e) {
			System.err.println(e.getMessage());
			throw new IllegalArgumentException();
		}
		return result;
	}	
}
