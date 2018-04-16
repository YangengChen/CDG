package cdg.domain.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;

public class CdgConstraintEvaluator extends ConstraintEvaluator {
	
	public static ConstraintEvaluator toConstraintEvaluator(GenerationConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		ConstraintEvaluator evaluator = new CdgConstraintEvaluator();
		List<Integer> permConDistIDs = configuration.getPermConDist();
		List<Integer> permPrecinctIDs = configuration.getPermPrecinct();
		if (permConDistIDs != null) {
			Map<Integer,Integer> permConDists = new HashMap<Integer,Integer>();
			for (int id : permConDistIDs) {
				permConDists.put(id, id);
			}
			evaluator.setConstraint(UserConstraint.PERMCONDIST, permConDists);
		}
		if (permPrecinctIDs != null) {
			Map<Integer,Integer> permPrecincts = new HashMap<Integer,Integer>();
			for (int id : permPrecinctIDs) {
				permPrecincts.put(id, id);
			}
			evaluator.setConstraint(UserConstraint.PERMPRECINCT, permPrecincts);
		}
		
		return evaluator;
	}
	
	public boolean meetsConstraints(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		
		int id = district.getPublicID();
		Map<Integer,Integer> permConDistricts;
		try {
			Object constraintValue = this.getConstraints(UserConstraint.PERMCONDIST);
			if (constraintValue == null) {
				throw new IllegalStateException();
			}
			permConDistricts = (Map<Integer,Integer>)constraintValue;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		boolean constraintsMet = permConDistricts.get(id) == null;
		return constraintsMet;
	}

	public boolean meetsConstraints(Precinct district) {
		return false;
	}
}
