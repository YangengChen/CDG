package cdg.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygonal;
import com.vividsolutions.jts.geom.TopologyException;

import cdg.dao.CongressionalDistrict;
import cdg.dao.ElectionResult;
import cdg.dao.Precinct;
import cdg.dao.Region;
import cdg.dao.State;
import cdg.domain.generation.CdgGoodnessEvaluator;
import cdg.domain.map.MapType;
import cdg.domain.region.Party;
import cdg.properties.CdgConstants;
import cdg.properties.CdgPropertiesManager;

@Service
public class MapService {
	
	public static String generateMap(State state, MapType type) {
		if (state == null || type == null)
			throw new IllegalArgumentException();
		
		switch (type)
		{
			case STATE:
				return generateStateMap(state);
			case CONGRESSIONAL:
				return generateCongressionalDistrictMap(state, true);
			case PRECINCT:
				return generatePrecinctMap(state);
			default:
				return null;
		}
	}
	
	public static byte[] getMapAsBytes(String map) {
		if (map == null) {
			return null;
		}
		return map.getBytes(StandardCharsets.UTF_8);
	}
	
	public static String generateUnitedStatesMap(Collection<State> states) throws IllegalStateException {
		if (states == null || states.size() == 0) {
			throw new IllegalArgumentException();
		}

		Collection<Geometry> stateGeoms = getAllStateGeometries(states);
		String unitedStatesMap;
		FeatureCollection featureCollection;
		GeoJSONWriter writer = new GeoJSONWriter();
		List<Feature> features = new ArrayList<Feature>();
		Map<String, Object> properties;
		org.wololo.geojson.Geometry currGeoJson;
		Feature currFeature;
		try {
			for (State state : states) {
				currGeoJson = writer.write(state.getGeometry());
				properties = new HashMap<String, Object>();
				properties.put(CdgConstants.STATE_ID, state.getPublicID());
				properties.put(CdgConstants.STATE_NAME, state.getName());
				properties.put(CdgConstants.POPULATION, state.getPopulation());
				ElectionResult election = state.getPresidentialVoteTotals();
				if (election != null) {
					properties.put(CdgConstants.VOTING_AGE_POPULATION, election.getVotingAgePopulation());
					properties.put(CdgConstants.ELECTION_YEAR, election.getYear());
					properties.put(CdgConstants.TOTAL_VOTE, election.getTotalVotes());
					properties.put(CdgConstants.DEM_VOTE, election.getTotal(Party.DEMOCRATIC));
					properties.put(CdgConstants.REP_VOTE, election.getTotal(Party.REPUBLICAN));
					properties.put(CdgConstants.OTH_VOTE, election.getTotal(Party.OTHER));
				}
				currFeature = new Feature(currGeoJson, properties);
				features.add(currFeature);
			}
			featureCollection = writer.write(features);
			unitedStatesMap = featureCollection.toString();
		} catch (Exception e) {
			throw new IllegalStateException();
		}

		return unitedStatesMap;
	}
	
	private static Collection<Geometry> getAllStateGeometries(Collection<State> states) throws IllegalStateException {
		if (states == null) {
			throw new IllegalArgumentException();
		}
		
		GeoJSONReader reader = new GeoJSONReader();
		List<Geometry> stateGeoms = new ArrayList<>();
		Geometry geom;
		try {
			for (State state : states) {
				if (state.getGeoJsonGeometry() == null) {
					geom = createStateGeometry(state);
				}
				else {
					geom = reader.read(state.getGeoJsonGeometry());
				}
				state.setGeometry(geom);
				stateGeoms.add(geom);
			}
		} catch (IllegalStateException ise) {
			throw ise;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return stateGeoms;
	}
	
	public static String generateStateMap(State state) throws IllegalStateException {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		Geometry stateGeom = createStateGeometry(state);
		GeoJSONWriter writer = new GeoJSONWriter();
		Map<String, Object> properties = new HashMap<String, Object>();
		String stateMap;
		try {
			org.wololo.geojson.Geometry geoJson = writer.write(stateGeom);
			properties.put(CdgConstants.STATE_ID, state.getPublicID());
			properties.put(CdgConstants.STATE_NAME, state.getName());
			properties.put(CdgConstants.POPULATION, state.getPopulation());	
			ElectionResult election = state.getPresidentialVoteTotals();
			if (election != null) {
				properties.put(CdgConstants.VOTING_AGE_POPULATION, election.getVotingAgePopulation());
				properties.put(CdgConstants.ELECTION_YEAR, election.getYear());
				properties.put(CdgConstants.TOTAL_VOTE, election.getTotalVotes());
				properties.put(CdgConstants.DEM_VOTE, election.getTotal(Party.DEMOCRATIC));
				properties.put(CdgConstants.REP_VOTE, election.getTotal(Party.REPUBLICAN));
				properties.put(CdgConstants.OTH_VOTE, election.getTotal(Party.OTHER));
			}
			Feature feature = new Feature(geoJson, properties);
			stateMap = feature.toString();
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		
		return stateMap;
	}
	
	public static Geometry createStateGeometry(State state) throws IllegalStateException {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		Map<Integer,CongressionalDistrict> districts = state.getConDistricts();
		if (districts == null || districts.size() == 0) {
			throw new IllegalStateException();
		}
		Collection<Geometry> districtGeoms = getAllDistrictGeometries(districts.values(), false);
		GeometryFactory stateFactory = JTSFactoryFinder.getGeometryFactory(null);
		GeometryCollection districtCombo;
		Geometry stateGeom;
		try {
			districtCombo = (GeometryCollection)stateFactory.buildGeometry(districtGeoms);
			stateGeom = districtCombo.union();
		} catch (TopologyException te) {
			System.err.println(te.getMessage());
			throw new IllegalStateException(te);
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		if (!(stateGeom instanceof Polygonal)) {
			throw new IllegalStateException();
		}
		
		return stateGeom;
	}
	
	public static String generateCongressionalDistrictMap(State state, boolean regenerateDistricts) throws IllegalStateException {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		Map<Integer,CongressionalDistrict> districts = state.getConDistricts();
		if (districts == null || districts.size() == 0) {
			throw new IllegalStateException();
		}
		Collection<Geometry> districtGeoms = getAllDistrictGeometries(districts.values(), regenerateDistricts);
		
		String congressionalDistrictMap;
		FeatureCollection featureCollection;
		GeoJSONWriter writer = new GeoJSONWriter();
		List<Feature> features = new ArrayList<Feature>();
		org.wololo.geojson.Geometry currGeoJson;
		Map<String, Object> properties;
		Feature currFeature;
		try {
			for (CongressionalDistrict district : districts.values()) {
				currGeoJson = writer.write(district.getGeometry());
				properties = new HashMap<String, Object>();
				properties.put(CdgConstants.DISTRICT_ID, district.getPublicID());
				properties.put(CdgConstants.DISTRICT_NAME, district.getName());
				properties.put(CdgConstants.POPULATION, district.getPopulation());
				ElectionResult election = district.getPresidentialVoteTotals();
				if (election != null) {
					properties.put(CdgConstants.VOTING_AGE_POPULATION, election.getVotingAgePopulation());
					properties.put(CdgConstants.ELECTION_YEAR, election.getYear());
					properties.put(CdgConstants.TOTAL_VOTE, election.getTotalVotes());
					properties.put(CdgConstants.DEM_VOTE, election.getTotal(Party.DEMOCRATIC));
					properties.put(CdgConstants.REP_VOTE, election.getTotal(Party.REPUBLICAN));
					properties.put(CdgConstants.OTH_VOTE, election.getTotal(Party.OTHER));
					properties.put(CdgConstants.WASTED_VOTE_RATIO, CdgGoodnessEvaluator.getEfficiencyGap(district));
				}
				properties.put(CdgConstants.SCHWARTZBERG_COMPACTNESS, CdgGoodnessEvaluator.getSchwartzbergCompactness(district) * 100);
				properties.put(CdgConstants.HULL_RATIO_COMPACTNESS, CdgGoodnessEvaluator.getHullRatioCompactness(district) * 100);
				properties.put(CdgConstants.REOCK_COMPACTNESS, CdgGoodnessEvaluator.getReockCompactness(district) * 100);
				properties.put(CdgConstants.GOODNESS, district.getGoodnessValue());
				currFeature = new Feature(currGeoJson, properties);
				features.add(currFeature);
			}
			featureCollection = writer.write(features);
			congressionalDistrictMap = featureCollection.toString();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return congressionalDistrictMap;
	}
	
	private static Collection<Geometry> getAllDistrictGeometries(Collection<CongressionalDistrict> districts, boolean regenerateDistricts)  throws IllegalStateException {
		if (districts == null) {
			throw new IllegalArgumentException();
		}
		
		GeoJSONReader reader = new GeoJSONReader();
		List<Geometry> districtGeoms = new ArrayList<>();
		Geometry geom;
		try {
			for (CongressionalDistrict dist : districts) {
				if (regenerateDistricts || dist.getGeoJsonGeometry() == null) {
					geom = createDistrictGeometry(dist, reader);
				} else {
					geom = reader.read(dist.getGeoJsonGeometry());
				}
				dist.setGeometry(geom);
				districtGeoms.add(geom);
			}
		} catch (IllegalStateException ise) {
			throw ise;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return districtGeoms;
	}
	
	public static String convertToGeoJSONGeometry(Geometry region) throws IllegalStateException {
		if (region == null) {
			throw new IllegalArgumentException();
		}
		
		GeoJSONWriter writer = new GeoJSONWriter();
		try {
			org.wololo.geojson.Geometry regionGeoJson = writer.write(region);
			String geoJson = regionGeoJson.toString();
			return geoJson;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static Geometry createDistrictGeometry(CongressionalDistrict district) throws IllegalStateException {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		
		GeoJSONReader reader = new GeoJSONReader();
		Geometry districtGeom = createDistrictGeometry(district, reader);
		return districtGeom;
	}
	
	private static Geometry createDistrictGeometry(CongressionalDistrict district, GeoJSONReader reader) throws IllegalStateException {
		if (district == null || reader == null) {
			throw new IllegalArgumentException();
		}
		
		Collection<Geometry> precinctGeoms = getAllPrecinctGeometries(district, reader);
		GeometryFactory conDistrictFactory = JTSFactoryFinder.getGeometryFactory(null);
		GeometryCollection precinctCombo = (GeometryCollection)conDistrictFactory.buildGeometry(precinctGeoms);
		Geometry districtGeom;
		try {
			districtGeom = precinctCombo.union();
		} catch (TopologyException te) {
			System.err.println(te.getMessage());
			throw new IllegalStateException(te);
		}
		if (!(districtGeom instanceof Polygonal)) {
			throw new IllegalStateException();
		}
		
		return districtGeom;
	}
	
	public static String generatePrecinctMap(State state) throws IllegalStateException {
		if (state == null) {
			throw new IllegalArgumentException();
		}

		Map<Integer,Precinct> precincts = state.getPrecincts();
		if (precincts == null || precincts.size() == 0) {
			throw new IllegalStateException();
		}
		CdgPropertiesManager propertiesManager = CdgPropertiesManager.getInstance();
		String geoJSON = null;
		GeoJSONReader reader = new GeoJSONReader();
		GeoJSONWriter writer = new GeoJSONWriter();
		FeatureCollection featureCollection;
		List<Feature> features = new ArrayList<Feature>();
		Feature currFeature;
		Map<String, Object> properties;
		org.wololo.geojson.Geometry currGeoJson;
		Geometry currJTSGeom;
		Map<Integer,Region> neighbors;
		List<String> neighborIDs;
		try {
			for (Precinct precinct : precincts.values()) {
				neighbors = precinct.getNeighborRegions();
				if (neighbors == null) {
					neighbors = new HashMap<Integer,Region>();
				}
				neighborIDs = new ArrayList<>();
				for (Region neighbor : neighbors.values()) {
					neighborIDs.add(neighbor.getPublicID());
				}
				currJTSGeom = reader.read(precinct.getGeoJsonGeometry());
				currGeoJson = writer.write(currJTSGeom);
				properties = new HashMap<String, Object>();
				properties.put(CdgConstants.PRECINCT_ID, precinct.getPublicID());
				properties.put(CdgConstants.PRECINCT_NAME, precinct.getName());
				properties.put(CdgConstants.COUNTY, precinct.getCounty());
				properties.put(CdgConstants.POPULATION, precinct.getPopulation());
				properties.put(CdgConstants.DISTRICT_ID, precinct.getConDistrict().getPublicID());
				ElectionResult election = precinct.getPresidentialVoteTotals();
				if (election != null) {
					properties.put(CdgConstants.VOTING_AGE_POPULATION, election.getVotingAgePopulation());
					properties.put(CdgConstants.ELECTION_YEAR, election.getYear());
					properties.put(CdgConstants.TOTAL_VOTE, election.getTotalVotes());
					properties.put(CdgConstants.DEM_VOTE, election.getTotal(Party.DEMOCRATIC));
					properties.put(CdgConstants.REP_VOTE, election.getTotal(Party.REPUBLICAN));
					properties.put(CdgConstants.OTH_VOTE, election.getTotal(Party.OTHER));
				}
				currFeature = new Feature(currGeoJson, properties);
				features.add(currFeature);
			}
			featureCollection = writer.write(features);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		geoJSON = featureCollection.toString();
		return geoJSON;
	}
	
	private static Collection<Geometry> getAllPrecinctGeometries(CongressionalDistrict district, GeoJSONReader reader) {
		if (district == null || reader == null) {
			throw new IllegalArgumentException();
		}
		
		Map<Integer,Precinct> precincts = district.getPrecincts();
		if (precincts == null || precincts.size() == 0) {
			throw new IllegalStateException();
		}
		List<Geometry> precinctGeoms = new ArrayList<>();
		Geometry currPrecinctGeom;
		try {
			for (Precinct precinct : precincts.values()) {
				if (precinct.getGeoJsonGeometry() == null) {
					throw new IllegalStateException();
				}
				currPrecinctGeom = reader.read(precinct.getGeoJsonGeometry());
				precinct.setGeometry(currPrecinctGeom);
				precinctGeoms.add(currPrecinctGeom);
			}
		} catch (IllegalStateException ise) {
			throw ise;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return precinctGeoms;
	}
}
