package cdg.domain.generation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GenerateMapAlgorithm {
	private final GenerationState STATE;
	private final CongressionalDistrictMap CONDISTRICTMAP;
	private final GoodnessEvaluator GOODNESSEVAL;
	private final ConstraintEvaluator CONSTRAINTEVAL;
	private final double THRESHOLD_PERCENT;
	private final int MAX_DISTRICT_ITERATIONS;
	private final int MAX_GENERATION_ITERATIONS;
	private final double DISTRICT_ITERATION_MAX_PERCENT;
	private final double END_THRESHOLD_PERCENT;
	private final NextDistrictPolicy POLICY;
	private Future<Boolean> generation;
	private ExecutorService executor;
	
	public GenerateMapAlgorithm(int stateID, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval)
	{
		STATE = null;
		CONDISTRICTMAP = null;
		GOODNESSEVAL = goodnessEval;
		CONSTRAINTEVAL = constraintEval;
		THRESHOLD_PERCENT = 0;
		MAX_DISTRICT_ITERATIONS = 0;
		MAX_GENERATION_ITERATIONS = 0;
		DISTRICT_ITERATION_MAX_PERCENT = 0;
		END_THRESHOLD_PERCENT = 0;
		POLICY = null;
	}
	
	public boolean start()
	{
		return false;
	}
	
	public boolean stop()
	{
		return false;
	}
	
	public boolean isComplete()
	{
		return false;
	}
	
	public boolean isCancelled()
	{
		return false;
	}
	
	public boolean getGenerationResult()
	{
		return false;
	}
	
	private boolean generate()
	{
		return false;
	}
	
	private void chooseNextCandidatePrecinct()
	{
		
	}
	
	private void moveCandidatePrecinct()
	{
		
	}
	
	private void unmoveCandidatePrecinct()
	{
		
	}
	
	private void validatePrecinctMove() 
	{
		
	}
	
	private boolean isValidMove(double currOldGoodness, double currNewGoodness, double neighborOldGoodness, double neighborNewGoodness)
	{
		return false;
	}
	
	private boolean continueWithDistrict()
	{
		return false;
	}
	
	private boolean continueWithGeneration()
	{
		return false;
	}
}
