package cdg.domain.generation;

import java.util.Calendar;
import java.util.Date;

import cdg.dao.State;
import cdg.responses.GenerationResponse;

public class MapGenerator {
	private GoodnessEvaluator goodnessEvaluator;
	private ConstraintEvaluator constraintEvaluator;
	private String stateId;
	private GenerateMapAlgorithm currAlgorithmRun;

	public boolean startGeneration(State state) {
		if (goodnessEvaluator == null || constraintEvaluator == null || state == null) {
			throw new IllegalStateException();
		}

		try {
			currAlgorithmRun = new GenerateMapAlgorithm(state, goodnessEvaluator, constraintEvaluator);
		} catch (IllegalArgumentException iae) {
			throw iae;
		} catch (Exception e) {
			throw new IllegalStateException();
		}
		
		return currAlgorithmRun.start();
	}
	
	public void stopGeneration() {
		if (currAlgorithmRun != null) {
			currAlgorithmRun.stop();
		}
	}
	
	public GenerationStatus getStatus() {
		if (currAlgorithmRun == null) {
			return GenerationStatus.NOTSTARTED;
		}
		if (currAlgorithmRun.isComplete()) {
			if (currAlgorithmRun.isCancelled()) {
				return GenerationStatus.CANCELED;
			}
			else {
				boolean generationSuccess = currAlgorithmRun.getGenerationResult();
				if (generationSuccess) {
					return GenerationStatus.COMPLETE;
				} else {
					return GenerationStatus.ERROR;
				}
			}
		} else {
			return GenerationStatus.INPROGRESS;
		}
	}
	
	public GenerationResponse getGenerationState() {
		GenerationStatus currStatus = getStatus();
		GenerationResponse response = new GenerationResponse(currStatus);
		if (currStatus.equals(GenerationStatus.INPROGRESS) || currStatus.equals(GenerationStatus.COMPLETE)) {
			GenerationState currGenState = currAlgorithmRun.getState();
			response.setStartTotalGoodness(currGenState.getStartTotalGoodness());
			response.setCurrTotalGoodness(currGenState.getLastTotalGoodness());
			response.setCurrIteration(currGenState.getCurrGenIteration());
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(currGenState.getGenStartTime());
			response.setStartTime(calendar.getTime().toString());
			if (currStatus.equals(GenerationStatus.COMPLETE)) {
				calendar.setTimeInMillis(currGenState.getGenStopTime());
				response.setStopTime(calendar.getTime().toString());
			}
			calendar.setTimeInMillis(System.currentTimeMillis());
			response.setTimestamp(calendar.getTime().toString());
		}
		return response;
	}
	
	public State getGeneratedState() {
		if (currAlgorithmRun == null) {
			return null;
		}
		if (!getStatus().equals(GenerationStatus.COMPLETE)) {
			return null;
		}
		State genState = currAlgorithmRun.getGeneratedState();
		return genState;
	}
	
	
	public void setState(String stateId) {
		this.stateId = stateId;
	}
	
	public void setGoodnessEvaluator(GoodnessEvaluator goodnessEvaluator) {
		this.goodnessEvaluator = goodnessEvaluator;
	}

	public void setConstraintEvaluator(ConstraintEvaluator constraintEvaluator) {
		this.constraintEvaluator = constraintEvaluator;
	}
	
	public void resetGenerator(String stateId, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval) {
		reset();
		setState(stateId);
		setGoodnessEvaluator(goodnessEval);
		setConstraintEvaluator(constraintEval);
	}
	
	private void reset() {
		goodnessEvaluator = null;
		constraintEvaluator = null;
		stateId = null;
		stopGeneration();
		currAlgorithmRun = null;
	}
}
