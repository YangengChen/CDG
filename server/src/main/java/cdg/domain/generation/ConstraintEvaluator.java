package cdg.domain.generation;

import java.util.EnumMap;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;

public class ConstraintEvaluator {
	private EnumMap<UserConstraint,Object> constraints;
	
	public ConstraintEvaluator()
	{
		constraints = new EnumMap<UserConstraint,Object>(UserConstraint.class);
	}
	
	public void setConstraint(UserConstraint constraint, Object value)
	{
		
	}
	
	public void removeConstraint(UserConstraint constraint)
	{
		
	}
	
	public static ConstraintEvaluator toConstraintEvaluator(GenerationConfiguration configuration) 
	{
		return new ConstraintEvaluator();
	}
	
	public boolean meetsConstraints(CongressionalDistrict district)
	{
		return false;
	}
	
	public boolean meetsConstraints(Precinct district)
	{
		return false;
	}
}
