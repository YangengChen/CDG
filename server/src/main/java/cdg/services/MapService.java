package cdg.services;

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
import cdg.dao.Precinct;
import cdg.dao.State;

@Service
public class MapService {
	
	public static String generateCongressionalDistrictMap(State state, boolean regenerateDistricts) throws IllegalStateException {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		Map<Integer,CongressionalDistrict> districts = state.getConDistricts();
		if (districts == null) {
			throw new IllegalStateException();
		}
		Collection<Geometry> districtGeoms = getAllDistrictGeometries(districts.values(), regenerateDistricts);
		
		GeoJSONWriter writer = new GeoJSONWriter();
		List<Feature> features = new ArrayList<Feature>();
		Map<String, Object> properties = new HashMap<String, Object>();
		org.wololo.geojson.Geometry currGeoJson;
		Feature currFeature;
		for (Geometry geom : districtGeoms) {
			currGeoJson = writer.write(geom);
			currFeature = new Feature(currGeoJson, properties);
			features.add(currFeature);
		}
		
		FeatureCollection featureCollection = writer.write(features);
		String congressionalDistrictMap = featureCollection.toString();
		return congressionalDistrictMap;
	}
	
	private static Collection<Geometry> getAllDistrictGeometries(Collection<CongressionalDistrict> districts, boolean regenerateDistricts)  throws IllegalStateException {
		GeoJSONReader reader = new GeoJSONReader();
		GeometryFactory conDistrictFactory = JTSFactoryFinder.getGeometryFactory(null);
		List<Geometry> districtGeoms = new ArrayList<>();
		Geometry geom;
		try {
			for (CongressionalDistrict dist : districts) {
				if (regenerateDistricts || dist.getGeoJsonGeometry() == null) {
					geom = createDistrictGeometry(dist, reader, conDistrictFactory);
				} else {
					geom = reader.read(dist.getGeoJsonGeometry());
				}
				districtGeoms.add(geom);
			}
		} catch (IllegalStateException ise) {
			throw ise;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return districtGeoms;
	}
	
	public static Geometry createDistrictGeometry(CongressionalDistrict district) throws IllegalStateException {
		GeoJSONReader reader = new GeoJSONReader();
		GeometryFactory conDistrictFactory = JTSFactoryFinder.getGeometryFactory(null);
		return createDistrictGeometry(district, reader, conDistrictFactory);
	}
	
	private static Geometry createDistrictGeometry(CongressionalDistrict district, GeoJSONReader reader, GeometryFactory conDistrictFactory) throws IllegalStateException {
		if (district == null || reader == null || conDistrictFactory == null) {
			throw new IllegalArgumentException();
		}
		List<Geometry> precinctGeoms = new ArrayList<>();
		Map<Integer,Precinct> currPrecincts;
		Geometry currPrecinctGeom;
		GeometryCollection precinctCombo;
		Geometry districtGeom;
		
		currPrecincts = district.getPrecincts();
		if (currPrecincts == null) {
			throw new IllegalStateException();
		}
		for (Precinct precinct : currPrecincts.values()) {
			if (precinct.getGeoJsonGeometry() == null) {
				throw new IllegalStateException();
			}
			currPrecinctGeom = reader.read(precinct.getGeoJsonGeometry());
			precinctGeoms.add(currPrecinctGeom);
		}
		precinctCombo = (GeometryCollection)conDistrictFactory.buildGeometry(precinctGeoms);
		try {
			districtGeom = precinctCombo.union();
		} catch (TopologyException te) {
			System.err.println(te.getMessage());
			throw new IllegalStateException();
		}
		if (!(districtGeom instanceof Polygonal)) {
			throw new IllegalStateException();
		}
		return districtGeom;
	}
}
