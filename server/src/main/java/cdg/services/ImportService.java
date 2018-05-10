package cdg.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.DateTimeException;
import java.time.Year;
import java.util.Collection;
import java.util.EnumMap;
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
import cdg.repository.ElectionResultRepository;
import cdg.repository.PrecinctRepository;
import cdg.dao.CongressionalDistrict;
import cdg.dao.ElectionResult;
import cdg.dao.Precinct;
import cdg.dao.Region;
import cdg.dao.State;
import cdg.domain.map.MapType;
import cdg.domain.region.Party;
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
	ElectionResultRepository electionRepo;
	
	public State createState(String geoJSON) {
		if (geoJSON == null) {
			return null;
		}
		CdgPropertiesManager propertiesManager = CdgPropertiesManager.getInstance();
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
			
			setPopulation(state);
			setElectionData(state);
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
	
	/*
	 * Annotate each precinct GeoJSON geometry with its neighboring precints.
	 * Calls a script in resources to annotate the GeoJSON.  
	 * This script converts GeoJSON to topoJSON and uses the TopoJSON client library to find neighbors.
	 */
	private String annotateGeoJSONNeighbors(String geoJSON) {
		if (geoJSON == null) {
			throw new IllegalArgumentException();
		}
		CdgPropertiesManager propertiesManager = CdgPropertiesManager.getInstance();
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
	
	private State generateState(String name, String publicID, String geoJSON) {
		State state = new State();
		state.setName(name);
		state.setPublicID(publicID);
		//fake
//		state.setMapGeoJSON(geoJSON, MapType.PRECINCT);
		
		//store to database and use returned state value
		state = stateRepo.saveAndFlush(state);
		return state;
	}
	
	private void generatePrecinctsAndDistricts(Feature[] features, State state, Map<Integer,CongressionalDistrict> districts, Map<Integer,Precinct> precincts) {
		CdgPropertiesManager propertiesManager = CdgPropertiesManager.getInstance();
		Map<String,String> precinctsPubID = new HashMap<String,String>();
		Map<String,CongressionalDistrict> districtsPubID = new HashMap<String,CongressionalDistrict>();
		Map<String,Object> currProp;
		Geometry currGeom;
		Precinct currPrecinct;
		String currPrecinctName;
		String currPrecinctPubID;
		String currPrecinctCounty;
		long currPrecinctPopulation;
		long currPrecinctVotingAgePop;
		int currPrecinctElectionYear;
		long currPrecinctPresTotal;
		long currPrecinctPresRep;
		long currPrecinctPresDem;
		ElectionResult currPrecinctElection;
		CongressionalDistrict currDistrict;
		String currDistPubID;
		for (int i = 0; i < features.length; i++) {
			currProp = features[i].getProperties();
			currGeom = features[i].getGeometry();
			currPrecinctName = (String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_NAME_FIELD));
			currPrecinctPubID = (String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_IDENTIFIER_FIELD));
			try {
				currPrecinctPopulation = ((Number)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_POPULATION_FIELD))).longValue();	
			} catch (Exception e) {
				currPrecinctPopulation = 0;
			}
			try {
				currPrecinctVotingAgePop = ((Number)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_VOTING_AGE_POPULATION_FIELD))).longValue();
			} catch (Exception e) {
				currPrecinctVotingAgePop = 0;
			}
			currPrecinctElectionYear = (Integer)currProp.get(propertiesManager.getProperty(CdgConstants.PRESIDENTIAL_ELECTION_YEAR_FIELD));	
			currPrecinctPresTotal = ((Number)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_PRESIDENTIAL_TOTAL_VOTE_FIELD))).longValue();		
			currPrecinctPresRep = ((Number)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_PRESIDENTIAL_REP_VOTE_FIELD))).longValue();		
			currPrecinctPresDem = ((Number)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_PRESIDENTIAL_DEM_VOTE_FIELD))).longValue();		
			currPrecinctCounty = (String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_COUNTY_FIELD));
			if (precinctsPubID.containsKey(currPrecinctPubID)) {
				throw new IllegalStateException();
			}
			precinctsPubID.put(currPrecinctPubID, currPrecinctPubID);
			currPrecinctElection = generateElectionResult(currPrecinctVotingAgePop, currPrecinctElectionYear, currPrecinctPresTotal, currPrecinctPresRep, currPrecinctPresDem);
			if (currPrecinctElection == null) {
				System.err.println(currPrecinctPubID);
				throw new IllegalArgumentException();
			}
			currPrecinct = generatePrecinct(currPrecinctPubID, currPrecinctName, currPrecinctCounty, currPrecinctPopulation, currGeom.toString(), currPrecinctElection);
			precincts.put(currPrecinct.getId(), currPrecinct);
			
			currDistPubID = (String)currProp.get(propertiesManager.getProperty(CdgConstants.DISTRICT_IDENTIFIER_FIELD));
			currDistrict = districtsPubID.get(currDistPubID);
			if (currDistrict == null) {
				currDistrict = generateDistrict(currDistPubID);
				districtsPubID.put(currDistrict.getPublicID(), currDistrict);
				districts.put(currDistrict.getId(), currDistrict);
			}
			
			currPrecinct.setConDistrict(currDistrict);
			currPrecinct.setState(state);
			if (currDistrict.getPrecincts() == null) {
				currDistrict.setPrecincts(new HashMap<Integer,Precinct>());
			}
			currDistrict.getPrecincts().put(currPrecinct.getId(), currPrecinct);
			currDistrict.setState(state);
		}
	}
	
	private ElectionResult generateElectionResult(long votingAgePopulation, int electionYear, long totalVotes, long repVotes, long demVotes) {
		try {
			Year.of(electionYear);
		} catch (DateTimeException dte) {
			return null;
		}
		if (votingAgePopulation < 0 || totalVotes < 0 || repVotes < 0 || demVotes < 0) {
			return null;
		}
		if ((repVotes + demVotes) > totalVotes) {
			return null;
		}
		
		ElectionResult result = new ElectionResult();
		result.setVotingAgePopulation(votingAgePopulation);
		result.setYear(electionYear);
		result.setTotalVotes(totalVotes);
		Map<Party,Long> voteTotals = new EnumMap<Party,Long>(Party.class);
		voteTotals.put(Party.REPUBLICAN, repVotes);
		voteTotals.put(Party.DEMOCRATIC, demVotes);
		long otherVotes = totalVotes - (repVotes + demVotes);
		voteTotals.put(Party.OTHER, otherVotes);
		result.setPartyTotals(voteTotals);
		result = electionRepo.saveAndFlush(result);
		return result;
	}
	
	private Precinct generatePrecinct(String publicID, String name, String county, long population, String geoJSON, ElectionResult election) {
		Precinct precinct = new Precinct();
		precinct.setName(name);
		precinct.setPublicID(publicID);
		precinct.setCounty(county);
		precinct.setPopulation(population);
		precinct.setPresidentialVoteTotals(election);
		precinct.setGeoJsonGeometry(geoJSON);
		//store to database and use returned precinct value
		precinct = precinctRepo.saveAndFlush(precinct);
		return precinct;
	}
	
	private CongressionalDistrict generateDistrict(String publicID) {
		CongressionalDistrict district = new CongressionalDistrict();
		district.setName(CdgConstants.DISTRICT_NAME_PREFIX + publicID);
		district.setPublicID(publicID);
		//store to database and use returned district value
		district = districtRepo.saveAndFlush(district);
		return district;
	}

	private void generateNeighbors(Feature[] features, Map<Integer,Precinct> precincts) {
		if (features == null || precincts == null || features.length != precincts.size()) {
			throw new IllegalArgumentException();
		}
		Map<String,Precinct> precinctsPubID = new HashMap<String,Precinct>();
		for (Precinct precinct : precincts.values()) {
			precinctsPubID.put(precinct.getPublicID(), precinct);
		}
		CdgPropertiesManager propertiesManager = CdgPropertiesManager.getInstance();
		Map<String,Object> currProp;
		String currPrecinctPubID;
		List<String> currNeighbors;
		Precinct precinct;
		for (int i = 0; i < features.length; i++) {
			currProp = features[i].getProperties();
			currPrecinctPubID = (String)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_IDENTIFIER_FIELD));	
			currNeighbors = (List<String>)currProp.get(propertiesManager.getProperty(CdgConstants.PRECINCT_NEIGHBORS_FIELD)); //public keys of neighbors
			precinct = precinctsPubID.get(currPrecinctPubID);
			if (precinct == null) {
				throw new IllegalArgumentException();
			}
			
			Precinct neighbor;
			boolean valid;
			for (int j = 0; j < currNeighbors.size(); j++) {
				neighbor = precinctsPubID.get(currNeighbors.get(j));
				if (neighbor.getId() == precinct.getId()) {
					continue;
				}
				valid = validateNeighbor(precinct, neighbor);
				if (!valid) {
					/* Approximate neighbors from script - invalid neighbor means the neighbor isn't considered
					 * a neighbor by JTS, so just continue loop without creating neighbor association.
					 * If an error is thrown, this means that JTS finds the geometry invalid, so the state
					 * cannot be imported.*/
					continue;
				}
				if (precinct.getNeighborRegions() == null) {
					precinct.setNeighborRegions(new HashMap<Integer,Region>());
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
			valid = currGeom.touches(neighborGeom); 		//see JTS Geometry "touches" definition
		} catch (TopologyException te) {
			throw new IllegalArgumentException();
		}
		return valid;
	}
	
	private void setMaps(State state) {
		if (state == null || state.getConDistricts() == null) {
			throw new IllegalArgumentException();
		}
		
		String precinctMap = MapService.generatePrecinctMap(state);
		state.setMapGeoJSON(precinctMap, MapType.PRECINCT);
		
		String congressionalDistrictMap = MapService.generateCongressionalDistrictMap(state, true);
		state.setMapGeoJSON(congressionalDistrictMap, MapType.CONGRESSIONAL);
		String stateMap = MapService.generateStateMap(state);
		state.setMapGeoJSON(stateMap, MapType.STATE);
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
	
	private void setPopulation(State state) {
		if (state == null || state.getConDistricts() == null) {
			throw new IllegalArgumentException();
		}
		long totalStatePopulation = 0;
		long totalDistrictPopulation;
		for (CongressionalDistrict district : state.getConDistricts().values()) {
			totalDistrictPopulation = 0;
			if (district.getPrecincts() == null) {
				throw new IllegalArgumentException();
			}
			for (Precinct precinct : district.getPrecincts().values()) {
				totalDistrictPopulation +=  precinct.getPopulation();
			}
			district.setPopulation(totalDistrictPopulation);
			totalStatePopulation += totalDistrictPopulation;
		}
		state.setPopulation(totalStatePopulation);
	}
	
	private void setElectionData(State state) {
		if (state == null || state.getConDistricts() == null) {
			throw new IllegalArgumentException();
		}
		ElectionResult stateElection = new ElectionResult();
		stateElection.setPartyTotals(new EnumMap<Party,Long>(Party.class));
		stateElection.setTotal(Party.DEMOCRATIC, 0);
		stateElection.setTotal(Party.REPUBLICAN, 0);
		stateElection.setTotal(Party.OTHER, 0);
		ElectionResult districtElection;
		ElectionResult precinctElection;
		for (CongressionalDistrict district : state.getConDistricts().values()) {
			if (district.getPrecincts() == null) {
				throw new IllegalArgumentException();
			}
			districtElection = new ElectionResult();
			districtElection.setPartyTotals(new EnumMap<Party,Long>(Party.class));
			districtElection.setTotal(Party.DEMOCRATIC, 0);
			districtElection.setTotal(Party.REPUBLICAN, 0);
			districtElection.setTotal(Party.OTHER, 0);
			for (Precinct precinct : district.getPrecincts().values()) {
				precinctElection = precinct.getPresidentialVoteTotals();
				if (precinctElection == null) {
					throw new IllegalArgumentException();
				}
				districtElection.setYear(precinctElection.getYear());
				districtElection.addElectionResult(precinctElection);
			}
			electionRepo.saveAndFlush(districtElection);
			district.setPresidentialVoteTotals(districtElection);
			
			stateElection.setYear(districtElection.getYear());
			stateElection.addElectionResult(districtElection);
		}
		electionRepo.saveAndFlush(stateElection);
		state.setPresidentialVoteTotals(stateElection);
		
		stateRepo.saveAndFlush(state);
	}
}
