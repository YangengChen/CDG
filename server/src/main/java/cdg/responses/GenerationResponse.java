package cdg.responses;



import java.util.List;

import cdg.domain.generation.GenerationStatus;
import cdg.domain.generation.GoodnessResult;
import cdg.domain.generation.PrecinctDistrictMap;

public class GenerationResponse {
	private GenerationStatus status;
	private String startTime;
	private String stopTime;
	private String timestamp;
	private double startTotalGoodness;
	private double currTotalGoodness;
	private int currIteration;
	private List<PrecinctDistrictMap> precinctToDistrict;
	private List<GoodnessResult> districtsGoodness;

	public GenerationResponse() {}
	
	public GenerationResponse(GenerationStatus status) {
		this.status = status;
	}
	
	public GenerationResponse(GenerationStatus status, String startTime, String stopTime, double startTotalGoodness,
			double currTotalGoodness, int currIteration) {
		this(status);
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.startTotalGoodness = startTotalGoodness;
		this.currTotalGoodness = currTotalGoodness;
		this.currIteration = currIteration;
	}
	
	public void setStatus(GenerationStatus status) {
		this.status = status;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
	public void setStartTotalGoodness(double startTotalGoodness) {
		this.startTotalGoodness = startTotalGoodness;
	}
	public void setCurrTotalGoodness(double currTotalGoodness) {
		this.currTotalGoodness = currTotalGoodness;
	}
	public void setCurrIteration(int currIteration) {
		this.currIteration = currIteration;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public GenerationStatus getStatus() {
		return status;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public double getStartTotalGoodness() {
		return startTotalGoodness;
	}

	public double getCurrTotalGoodness() {
		return currTotalGoodness;
	}

	public int getCurrIteration() {
		return currIteration;
	}

	public List<PrecinctDistrictMap> getPrecinctToDistrict() {
		return precinctToDistrict;
	}

	public void setPrecinctToDistrict(List<PrecinctDistrictMap> precinctToDistrict) {
		this.precinctToDistrict = precinctToDistrict;
	}

	public List<GoodnessResult> getDistrictsGoodness() {
		return districtsGoodness;
	}

	public void setDistrictsGoodness(List<GoodnessResult> districtsGoodness) {
		this.districtsGoodness = districtsGoodness;
	}
}
