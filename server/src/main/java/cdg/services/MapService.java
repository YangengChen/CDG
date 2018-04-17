package cdg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.springframework.stereotype.Service;
import org.wololo.jts2geojson.GeoJSONReader;

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
	
	public static void generateCongressionalDistrictMap(State state) {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		Map<Integer,CongressionalDistrict> districts = state.getConDistricts();
		if (districts == null) {
			throw new IllegalStateException();
		}
		List<Geometry> districtGeoms = new ArrayList<>();
		Geometry districtGeom;
		for (CongressionalDistrict district : districts.values()) {
			districtGeom = createDistrictGeometry(district);
			districtGeoms.add(districtGeom);
		}
		//
	}
	
	private static Geometry createDistrictGeometry(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		GeoJSONReader reader = new GeoJSONReader();
		GeometryFactory conDistrictFactory = JTSFactoryFinder.getGeometryFactory(null);
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
