package cdg.domain.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygonal;
import com.vividsolutions.jts.geom.TopologyException;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;

public class CdgConstraintEvaluator extends ConstraintEvaluator {
	
	public static ConstraintEvaluator toConstraintEvaluator(GenerationConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		ConstraintEvaluator evaluator = new CdgConstraintEvaluator();
		List<String> permConDistIDs = configuration.getPermConDist();
		List<String> permPrecinctIDs = configuration.getPermPrecinct();
		Map<String,String> precinctToDistrict = configuration.getPrecinctToDistrict();
		Map<String,String> permConDists = new HashMap<String,String>();
		if (permConDistIDs != null) {
			for (String id : permConDistIDs) {
				permConDists.put(id, id);
			}
		}
		evaluator.setConstraint(UserConstraint.PERMCONDIST, permConDists);
		Map<String,String> permPrecincts = new HashMap<String,String>();
		if (permPrecinctIDs != null) {
			for (String id : permPrecinctIDs) {
				permPrecincts.put(id, id);
			}
		}
		if (precinctToDistrict != null) {
			for (String precinctID : precinctToDistrict.keySet()) {
				permPrecincts.put(precinctID, precinctID);
			}
		}
		evaluator.setConstraint(UserConstraint.PERMPRECINCT, permPrecincts);
		
		
		return evaluator;
	}
	
	public boolean meetsConstraints(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		
		String id = district.getPublicID();
		Map<String,String> permConDistricts;
		try {
			Object constraintValue = this.getConstraints(UserConstraint.PERMCONDIST);
			if (constraintValue == null) {
				throw new IllegalStateException();
			}
			permConDistricts = (Map<String,String>)constraintValue;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		boolean constraintsMet = permConDistricts.get(id) == null;
		return constraintsMet;
	}

	public boolean meetsConstraints(Precinct precinct) {
		if (precinct == null) {
			throw new IllegalArgumentException();
		}
		Map<String,String> permPrecincts;
		try {
			Object constraintValue = this.getConstraints(UserConstraint.PERMPRECINCT);
			if (constraintValue == null) {
				throw new IllegalStateException();
			}
			permPrecincts = (Map<String,String>)constraintValue;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		// is the precinct permanent?
		boolean constraintsMet = permPrecincts.get(precinct.getPublicID()) == null;
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		CongressionalDistrict dist = precinct.getConDistrict();
		if (dist == null) {
			throw new IllegalStateException();
		}
		//is the precinct on the border?
		constraintsMet = dist.isBorderPrecinct(precinct.getId());
		if (!constraintsMet) {
			return constraintsMet;
		}
		//is there more than one precinct in this congressional district?
		constraintsMet = dist.numPrecincts() > 1;
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		Precinct neighborPrecinct = precinct.getFromNeighborConDistrict(); // should be same if precinct move is called if precinct obj is not changed
		//is there a neighboring district?
		constraintsMet = neighborPrecinct != null;
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		Map<String,String> permConDistricts;
		try {
			Object constraintValue = this.getConstraints(UserConstraint.PERMCONDIST);
			if (constraintValue == null) {
				throw new IllegalStateException();
			}
			permConDistricts = (Map<String,String>)constraintValue;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		CongressionalDistrict neighborDist = neighborPrecinct.getConDistrict();
		if (neighborDist == null) {
			throw new IllegalStateException();
		}
		//is the neighboring district permanent?
		constraintsMet = permConDistricts.get(neighborDist.getPublicID()) == null;
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		Geometry precinctGeom = precinct.getGeometry();
		Geometry districtGeom = dist.getGeometry();
		if (precinctGeom == null || districtGeom == null) {
			throw new IllegalStateException();
		}
		Geometry resultGeom;
		try {
			resultGeom = districtGeom.difference(precinctGeom);
		} catch (TopologyException te) {
			return false;
		}
		//if precinct removed from district, is district geom valid, nonempty, and polygonal?
		constraintsMet = resultGeom.isValid() && !resultGeom.isEmpty() && (resultGeom instanceof Polygonal);
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		constraintsMet = resultGeom.getNumGeometries() <= districtGeom.getNumGeometries();
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		constraintsMet = !precinct.isLonePrecinct();
		if (!constraintsMet) {
			return constraintsMet;
		}
		
		
		return true;
	}
	
}
