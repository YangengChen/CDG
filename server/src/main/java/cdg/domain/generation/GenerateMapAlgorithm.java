package cdg.domain.generation;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cdg.dao.State;

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
	private final UUID generationID;
	
	public GenerateMapAlgorithm(State state, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval)
	{
		if (goodnessEval == null || constraintEval == null || state == null) {
			throw new IllegalArgumentException();
		}
		try {
			CONDISTRICTMAP = new CongressionalDistrictMap(state, goodnessEval, constraintEval);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		STATE = new GenerationState();
		GOODNESSEVAL = goodnessEval;
		CONSTRAINTEVAL = constraintEval;
		generationID = createGenerationID();
		//from properties
		THRESHOLD_PERCENT = 0;
		MAX_DISTRICT_ITERATIONS = 0;
		MAX_GENERATION_ITERATIONS = 0;
		DISTRICT_ITERATION_MAX_PERCENT = 0;
		END_THRESHOLD_PERCENT = 0;
		POLICY = NextDistrictPolicy.RANDOM;
	}
	
	public boolean start()
	{
		if (executor != null) {
			return false;
		}
		executor = Executors.newSingleThreadExecutor();
		Callable<Boolean> generationTask = () -> generate();
		generation = executor.submit(generationTask);
		return true;
	}
	
	private UUID createGenerationID() {
		UUID uid = UUID.randomUUID();
		return uid;
	}
	
	public void stop()
	{
		if (generation != null) {
			generation.cancel(true);
		}
	}
	
	public boolean isComplete()
	{
		if (generation == null) {
			return false;
		}
		return generation.isDone();
	}
	
	public boolean isCancelled()
	{
		if (generation == null) {
			return false;
		}
		return generation.isCancelled();
	}
	
	public GenerationState getState() {
		try {
			GenerationState genState = (GenerationState)STATE.clone();
			return genState;
		} catch (CloneNotSupportedException ce) {
			return null;
		}
	}
	
	public boolean getGenerationResult()
	{
		if (generation == null) {
			return false;
		}
		try {
			return generation.get();
		} catch (InterruptedException | CancellationException e) {
			return false;
		} catch (ExecutionException e) {
			//print error
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	private boolean generate()
	{
		STATE.startTime();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
		
		double startStateGoodness = CONDISTRICTMAP.getTotalGoodness();
		STATE.setStartTotalGoodness(startStateGoodness);
		while (continueWithGeneration()) {
			STATE.incrementGenIteration();
			chooseNextStartingDistrict();
			operateOnDistrict();
		}
		
		STATE.stopTime();
		return true;
	}
	
	private void operateOnDistrict() {
		double startDistrictGoodness = CONDISTRICTMAP.getGoodness(STATE.getCurrDistrictID());
		STATE.setCurrDistrictStartGoodness(startDistrictGoodness);
		do {
			STATE.incrementDistrictIteration();
			boolean precinctChosen = chooseNextCandidatePrecinct();
			if (precinctChosen) {
				moveCandidatePrecinct();
				validatePrecinctMove();
			}
		}
		while (continueWithDistrict());
	}
	
	private void chooseNextStartingDistrict() {
		int distID = -1;
		switch (POLICY) {
			case RANDOM:
				distID = CONDISTRICTMAP.getRandomDistrict();
				break;
			case LOWESTGOODNESS:
				distID = CONDISTRICTMAP.getLowestGoodnessDistrict();
				break;
			default:
				throw new IllegalStateException();
		}
		if (distID <= 0) {
			throw new IllegalStateException();
		}
		STATE.setCurrDistrictID(distID);
	}
	
	private boolean chooseNextCandidatePrecinct()
	{
		///fake
		
		return false;
	}
	
	private void moveCandidatePrecinct()
	{
		int distID = STATE.getCurrDistrictID();
		int precinctID = STATE.getCandidatePrecinctUID();
		int neighborID = CONDISTRICTMAP.movePrecinct(distID, -1, precinctID);
		if (neighborID <= 0) {
			throw new IllegalStateException();
		}
		STATE.setCurrNeighborID(neighborID);
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
