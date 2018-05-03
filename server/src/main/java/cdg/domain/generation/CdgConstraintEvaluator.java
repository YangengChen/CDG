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
		List<String> permConDistIDs = configuration.getPermConDist();
		List<String> permPrecinctIDs = configuration.getPermPrecinct();
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

	public boolean meetsConstraints(Precinct district) {
		//fake
		return true;
	}
}
