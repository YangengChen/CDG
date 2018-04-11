package cdg.domain.generation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.geojson.Geometry;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;
import cdg.dao.State;

@Service
public class GeometryService {
	
	public static void updateGeometry(CongressionalDistrict district) {
		
	}
	
	public static void updateGeometry(Precinct precinct) {
				
	}
	
	public static void updateGeometry(State state) {
		//fake
		String jsonString = state.getPrecinctMapGeoJson();
		FeatureCollection stateFeatures = (FeatureCollection)GeoJSONFactory.create(jsonString);
		Feature[] features = stateFeatures.getFeatures();
	
		//CongressionalDistrict currDistrict = state.getConDistricts().values().iterator().next();
		Map<Integer,CongressionalDistrict> districts = new HashMap<Integer,CongressionalDistrict>();
		Map<Integer,Precinct> precincts = new HashMap<Integer,Precinct>();
		Map<String,Object> currProp;
		Geometry currGeom;
		Precinct currPrecinct;
		CongressionalDistrict currDistrict;
		int currDistID;
		for (int i = 0; i < features.length; i++) {
			currProp = features[i].getProperties();
			currGeom = features[i].getGeometry();
			currPrecinct = new Precinct();
			currPrecinct.setId(Integer.parseInt((String)currProp.get("PrecinctID")));
			currPrecinct.setName((String)currProp.get("Precinct"));
			currPrecinct.setPublicID(i+1);
			currPrecinct.setState(state);
			currPrecinct.setGeoJsonGeometry(currGeom.toString());
			precincts.put(currPrecinct.getId(), currPrecinct);
			
			currDistID = Integer.parseInt((String)currProp.get("CongDist"));
			currDistrict = districts.get(currDistID);
			if (currDistrict == null) {
				currDistrict = new CongressionalDistrict();
				currDistrict.setId(currDistID);
				districts.put(currDistrict.getId(), currDistrict);
			}
			currPrecinct.setConDistrict(currDistrict);
		}
		state.setConDistricts(districts);
		state.setPrecincts(precincts);
		
		Map<Integer,Map<Integer,Precinct>> districtPrecincts = new HashMap<Integer,Map<Integer,Precinct>>();
		Map<Integer,Precinct> currPrecincts;
		for (Precinct precinct : precincts.values()) {
			currDistrict = precinct.getConDistrict();
			currPrecincts = districtPrecincts.get(currDistrict.getId());
			if (currPrecincts == null) {
				currPrecincts = new HashMap<Integer,Precinct>();
				districtPrecincts.put(currDistrict.getId(), currPrecincts);
			}
			currPrecincts.put(precinct.getId(), precinct);
		}
		int key = 1;
		for (CongressionalDistrict district : districts.values()) {
			district.setPublicID(key++);
			district.setName("Congressional District " + district.getId());
			district.setState(state);
			currPrecincts = districtPrecincts.get(district.getId());
			district.setPrecincts(currPrecincts);
		}
	}
}
