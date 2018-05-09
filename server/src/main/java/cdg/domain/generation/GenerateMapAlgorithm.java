package cdg.domain.generation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cdg.dao.State;
import cdg.properties.CdgConstants;
import cdg.properties.CdgPropertiesManager;

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
	private final int END_THRESHOLD_FORGIVENESS;
	private final NextDistrictPolicy POLICY;
	private Future<Boolean> generation;
	private ExecutorService executor;
	private final UUID generationID;
	
	public GenerateMapAlgorithm(State state, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval, Map<String,String> manualMappings) {
		if (goodnessEval == null || constraintEval == null || state == null) {
			throw new IllegalArgumentException();
		}
		try {
			CONDISTRICTMAP = new CongressionalDistrictMap(state, goodnessEval, constraintEval, manualMappings);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		STATE = new GenerationState();
		if (manualMappings != null) {
			STATE.getPrecinctToDistrict().putAll(manualMappings);
		}
		GOODNESSEVAL = goodnessEval;
		CONSTRAINTEVAL = constraintEval;
		generationID = createGenerationID();
		//from properties
		CdgPropertiesManager properties = CdgPropertiesManager.getInstance();
		THRESHOLD_PERCENT = Double.parseDouble((String)properties.getProperty(CdgConstants.THRESHOLD_PERCENT));
		MAX_DISTRICT_ITERATIONS = Integer.parseInt((String)properties.getProperty(CdgConstants.MAX_DISTRICT_ITERATIONS));
		MAX_GENERATION_ITERATIONS = Integer.parseInt((String)properties.getProperty(CdgConstants.MAX_GENERATION_ITERATIONS));
		DISTRICT_ITERATION_MAX_PERCENT = Double.parseDouble((String)properties.getProperty(CdgConstants.DISTRICT_ITERATION_MAX_PERCENT));
		END_THRESHOLD_PERCENT = Double.parseDouble((String)properties.getProperty(CdgConstants.END_THRESHOLD_PERCENT));
		END_THRESHOLD_FORGIVENESS = Integer.parseInt((String)properties.getProperty(CdgConstants.END_THRESHOLD_FORGIVENESS));
		POLICY = NextDistrictPolicy.valueOf(((String)properties.getProperty(CdgConstants.POLICY)));
	}
	
	public boolean start() {
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
	
	public UUID getGenerationID() {
		return generationID;
	}
	
	public void stop() {
		if (generation != null) {
			generation.cancel(true);
		}
	}
	
	public boolean pause() {
		if (generation == null) {
			return false;
		}
		if (this.isComplete()) {
			return true;
		}
		STATE.setPaused(true);
		return true;
	}
	
	public boolean isComplete() {
		if (generation == null) {
			return false;
		}
		return generation.isDone();
	}
	
	public boolean isCancelled() {
		if (generation == null) {
			return false;
		}
		return generation.isCancelled();
	}
	
	public GenerationState getState() {
		try {
			GenerationState genState = (GenerationState)STATE.clone();
			genState.setPrecinctToDistrict((Map)(((HashMap)STATE.getPrecinctToDistrict()).clone()));
			return genState;
		} catch (CloneNotSupportedException ce) {
			return null;
		}
	}
	
	public State getGeneratedState() {
		if (isComplete()) {
			State genState = CONDISTRICTMAP.getGeneratedState();
			return genState;
		}
		return null;
	}
	
	public boolean getGenerationResult() {
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
	
	private boolean generate() {
		STATE.startTime();

		try {
			double startStateGoodness = CONDISTRICTMAP.getTotalGoodness();
			STATE.setStartTotalGoodness(startStateGoodness);
			while (continueWithGeneration()) {
				double currStateGoodness = CONDISTRICTMAP.getTotalGoodness();
				STATE.setLastTotalGoodness(currStateGoodness);
				STATE.setDistrictsGoodness(CONDISTRICTMAP.getAllDistrictGoodness());
				STATE.incrementGenIteration();
				chooseNextStartingDistrict();
				operateOnDistrict();
			}
			double endStateGoodness = CONDISTRICTMAP.getTotalGoodness();
			STATE.setLastTotalGoodness(endStateGoodness);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		STATE.stopTime();
		return true;
	}
	
	private void operateOnDistrict() {
		double startDistrictGoodness = CONDISTRICTMAP.getGoodness(STATE.getCurrDistrictID());
		STATE.setCurrDistrictStartGoodness(startDistrictGoodness);
		if (!CONDISTRICTMAP.resetPrecinctQueue(STATE.getCurrDistrictID())) {
			throw new IllegalStateException();
		}
		STATE.setCurrDistrictIteration(0);
		do {
			STATE.incrementDistrictIteration();
			boolean precinctChosen = chooseNextCandidatePrecinct();
			if (precinctChosen) {
				moveCandidatePrecinct();
				validatePrecinctMove();
			}
			else {
				break;
			}
		}
		while (continueWithDistrict());
		System.err.println("Goodness: " + CONDISTRICTMAP.getGoodness(STATE.getCurrDistrictID()));
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
		if (distID < 0) {
			throw new IllegalStateException();
		}
		STATE.setCurrDistrictID(distID);
	}
	
	private boolean chooseNextCandidatePrecinct() {
		int precinctID = CONDISTRICTMAP.getNextCandidatePrecinct(STATE.getCurrDistrictID(), CONSTRAINTEVAL);
		if (precinctID >= 0) {
			STATE.setCandidatePrecinctUID(precinctID);
			return true;
		}
		return false;
	}
	
	private void moveCandidatePrecinct() {
		int distID = STATE.getCurrDistrictID();
		int precinctID = STATE.getCandidatePrecinctUID();
		int neighborID = CONDISTRICTMAP.movePrecinct(distID, -1, precinctID);
		if (neighborID < 0) {
			throw new IllegalStateException();
		}
		STATE.setCurrNeighborID(neighborID);
	}
	
	private void unmoveCandidatePrecinct() {
		int distID = STATE.getCurrDistrictID();
		int neighborID = STATE.getCurrNeighborID();
		int precinctID = STATE.getCandidatePrecinctUID();
		if (CONDISTRICTMAP.movePrecinct(neighborID, distID, precinctID) != distID) {
			throw new IllegalStateException();
		}
	}
	
	private void validatePrecinctMove() {
		int currDistID = STATE.getCurrDistrictID();
		double currOldGoodness = CONDISTRICTMAP.getGoodness(currDistID);
		int neighborDistID = STATE.getCurrNeighborID();
		double neighborOldGoodness = CONDISTRICTMAP.getGoodness(neighborDistID);
		
		double currNewGoodness = CONDISTRICTMAP.evaluateGoodness(currDistID, GOODNESSEVAL);
		double neighborNewGoodness = CONDISTRICTMAP.evaluateGoodness(neighborDistID, GOODNESSEVAL);
		
		boolean validMove = isValidMove(currOldGoodness,currNewGoodness, neighborOldGoodness, neighborNewGoodness);
		if (!validMove) {
			unmoveCandidatePrecinct();
			CONDISTRICTMAP.evaluateGoodness(currDistID, GOODNESSEVAL);
			CONDISTRICTMAP.evaluateGoodness(neighborDistID, GOODNESSEVAL);
		} else {
			int precinctID = STATE.getCandidatePrecinctUID();
			STATE.getPrecinctToDistrict().put(CONDISTRICTMAP.getPrecinctPublicID(precinctID), CONDISTRICTMAP.getDistrictPublicID(neighborDistID));
		}
	}
	
	private boolean isValidMove(double currOldGoodness, double currNewGoodness, double neighborOldGoodness, double neighborNewGoodness)
	{
		boolean valid = ((currNewGoodness - currOldGoodness)/currOldGoodness > THRESHOLD_PERCENT &&  
				(neighborNewGoodness - neighborOldGoodness)/neighborOldGoodness > THRESHOLD_PERCENT);
		return valid;
	}
	
	private boolean continueWithDistrict()
	{
		int iteration = STATE.getCurrDistrictIteration();
		if (iteration >= MAX_DISTRICT_ITERATIONS) {
			System.err.println("District reached max iterations");
			return false;
		}
		double startDistGoodness = STATE.getCurrDistrictStartGoodness();
		int distID = STATE.getCurrDistrictID();
		double currDistGoodness = CONDISTRICTMAP.getGoodness(distID);
		double percentageChange = (currDistGoodness - startDistGoodness)/startDistGoodness;
		if (percentageChange >= DISTRICT_ITERATION_MAX_PERCENT) {
			System.err.println("District reached max percent changed");
			return false;
		}
		return true;
	}
	
	private boolean continueWithGeneration()
	{
		if (STATE.isPaused()) {
			return false;
		}
		int iteration = STATE.getCurrGenIteration();
		if (iteration >= MAX_GENERATION_ITERATIONS) {
			System.err.println("Reached max iterations");
			return false;
		}
		/*double lastTotalGoodness = STATE.getLastTotalGoodness();
		double currTotalGoodness = CONDISTRICTMAP.getTotalGoodness();
		if (lastTotalGoodness == 0) {
			return true;
		}
		double percentageChange = (currTotalGoodness - lastTotalGoodness)/lastTotalGoodness;
		if (percentageChange < END_THRESHOLD_PERCENT) {
			System.err.println("Below threshold- " + percentageChange);
			STATE.incrementTimesBelowGenThreshold();
			if (STATE.getTimesBelowGenThreshold() < END_THRESHOLD_FORGIVENESS) {
				return true;
			}
			return false;
		}
		STATE.setTimesBelowGenThreshold(0);*/
		return true;
	}
}
