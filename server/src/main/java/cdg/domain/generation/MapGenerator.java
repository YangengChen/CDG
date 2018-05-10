package cdg.domain.generation;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;
import cdg.dao.SavedMap;
import cdg.dao.SavedMapping;
import cdg.dao.State;
import cdg.repository.StateRepository;
import cdg.responses.GenerationResponse;

public class MapGenerator {
	private GoodnessEvaluator goodnessEvaluator;
	private ConstraintEvaluator constraintEvaluator;
	private String stateId;
	private GenerateMapAlgorithm currAlgorithmRun;

	public boolean startGeneration(State state, Map<String,String> manualMappings) {
		if (goodnessEvaluator == null || constraintEvaluator == null || state == null) {
			throw new IllegalStateException();
		}

		try {
			currAlgorithmRun = new GenerateMapAlgorithm(state, goodnessEvaluator, constraintEvaluator, manualMappings);
		} catch (IllegalArgumentException iae) {
			System.err.println(iae);
			throw iae;
		} catch (Exception e) {
			System.err.println(e);
			throw new IllegalStateException();
		}
		
		return currAlgorithmRun.start();
	}
	
	public void stopGeneration() {
		if (currAlgorithmRun != null) {
			currAlgorithmRun.stop();
		}
	}
	
	public boolean pauseGeneration() {
		if (currAlgorithmRun == null) {
			return false;
		}
		return currAlgorithmRun.pause();
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
			response.setPrecinctToDistrict(currGenState.getPrecinctToDistrict());
			response.setDistrictsGoodness(currGenState.getDistrictsGoodness());
			
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
	
	public SavedMap createSavedMap(StateRepository repo) {
		if (currAlgorithmRun == null) {
			return null;
		}
		if (!getStatus().equals(GenerationStatus.COMPLETE)) {
			return null;
		}
		State genState = currAlgorithmRun.getGeneratedState();
		repo.detach(genState);
		SavedMap savedMap = new SavedMap(currAlgorithmRun.getGenerationID().toString());
		savedMap.setState(genState);
		savedMap.setDistricts(new HashSet<>());
		SavedMapping currDistrictMapping;
		Set<Precinct> currPrecinctSet;
		for (CongressionalDistrict district : genState.getConDistricts().values()) {
			currDistrictMapping = new SavedMapping();
			currDistrictMapping.setDistrict(district);
			currPrecinctSet = new HashSet<>(district.getPrecincts().values());
			currDistrictMapping.setPrecincts(currPrecinctSet);
			savedMap.getDistricts().add(currDistrictMapping);
		}
		return savedMap;
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
	
	public String getCurrGenerationID() {
		if (currAlgorithmRun == null) {
			return null;
		}
		String generationID = currAlgorithmRun.getGenerationID().toString();
		return generationID;
	}
	
	private void reset() {
		goodnessEvaluator = null;
		constraintEvaluator = null;
		stateId = null;
		stopGeneration();
		currAlgorithmRun = null;
	}
}
