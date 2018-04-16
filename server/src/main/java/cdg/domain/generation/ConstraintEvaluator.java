package cdg.domain.generation;

import java.util.EnumMap;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;

public abstract class ConstraintEvaluator {
	private EnumMap<UserConstraint,Object> constraints;
	
	public ConstraintEvaluator() {
		constraints = new EnumMap<UserConstraint,Object>(UserConstraint.class);
	}
	
	public Object getConstraints(UserConstraint constraint) {
		if (constraint == null) {
			throw new IllegalArgumentException();
		}
		return constraints.get(constraint);
	}
	
	public void setConstraint(UserConstraint constraint, Object obj) {
		if (constraint == null || obj == null) {
			throw new IllegalArgumentException();
		}
		constraints.put(constraint, obj);
	}
	
	public void removeConstraint(UserConstraint constraint) {
		if (constraint == null) {
			throw new IllegalArgumentException();
		}
		constraints.remove(constraint);
	}
	
	public abstract boolean meetsConstraints(CongressionalDistrict district); 
	
	public abstract boolean meetsConstraints(Precinct district);
}
