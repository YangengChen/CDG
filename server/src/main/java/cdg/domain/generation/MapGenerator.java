package cdg.domain.generation;

import org.springframework.beans.factory.annotation.Autowired;

import cdg.repository.StateRepository;

public class MapGenerator {
	private GoodnessEvaluator goodnessEvaluator;
	private ConstraintEvaluator constraintEvaluator;
	private int stateId;
	private GenerateMapAlgorithm currAlgorithmRun;
	
	@Autowired
	private StateRepository stateRepo;
	
	public boolean startGeneration()
	{
		return false;
	}
	
	public boolean stopGeneration()
	{
		return false;
	}
	
	public GenerationStatus getStatus()
	{
		return null;
	}
	
	
	public void setState(int stateId)
	{
		
	}
	
	public void setGoodnessEvaluator(GoodnessEvaluator goodnessEvaluator) {
		this.goodnessEvaluator = goodnessEvaluator;
	}

	public void setConstraintEvaluator(ConstraintEvaluator constraintEvaluator) {
		this.constraintEvaluator = constraintEvaluator;
	}
	
	public void resetGenerator(int stateId, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval)
	{
		reset();
	}
	
	private void reset()
	{
		goodnessEvaluator = null;
		constraintEvaluator = null;
		stateId = 0;
		currAlgorithmRun = null;
	}
}
