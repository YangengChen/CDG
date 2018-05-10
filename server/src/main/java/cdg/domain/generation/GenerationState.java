package cdg.domain.generation;

import java.util.HashMap;
import java.util.Map;

public class GenerationState implements Cloneable {
	private int currDistrictID;
	private int currDistrictIteration;
	private double currDistrictStartGoodness;
	private int currNeighborID;
	private int currGenIteration;
	private int candidatePrecinctUID;
	private double lastTotalGoodness;
	private double startTotalGoodness;
	private long genStartTime;
	private long genStopTime;
	private int timesBelowGenThreshold;
	private Map<String,String> precinctToDistrict;
	private Map<String,Double> districtsGoodness;
	private boolean paused;

	public GenerationState() {
		precinctToDistrict = new HashMap<String,String>();
	}
	
	public int getCurrDistrictID() {
		return currDistrictID;
	}

	public void setCurrDistrictID(int currDistrictID) {
		this.currDistrictID = currDistrictID;
	}

	public int getCurrDistrictIteration() {
		return currDistrictIteration;
	}

	public void setCurrDistrictIteration(int currDistrictIteration) {
		this.currDistrictIteration = currDistrictIteration;
	}
	
	public void incrementDistrictIteration() {
		currDistrictIteration++;
	}

	public double getCurrDistrictStartGoodness() {
		return currDistrictStartGoodness;
	}

	public void setCurrDistrictStartGoodness(double currDistrictStartGoodness) {
		this.currDistrictStartGoodness = currDistrictStartGoodness;
	}

	public int getCurrNeighborID() {
		return currNeighborID;
	}

	public void setCurrNeighborID(int currNeighborID) {
		this.currNeighborID = currNeighborID;
	}

	public int getCurrGenIteration() {
		return currGenIteration;
	}

	public void setCurrGenIteration(int currGenIteration) {
		this.currGenIteration = currGenIteration;
	}
	
	public void incrementGenIteration() {
		currGenIteration++;
	}

	public int getCandidatePrecinctUID() {
		return candidatePrecinctUID;
	}

	public void setCandidatePrecinctUID(int candidatePrecinctUID) {
		this.candidatePrecinctUID = candidatePrecinctUID;
	}

	public double getLastTotalGoodness() {
		return lastTotalGoodness;
	}

	public void setLastTotalGoodness(double lastTotalGoodness) {
		this.lastTotalGoodness = lastTotalGoodness;
	}

	public long getGenStartTime() {
		return genStartTime;
	}

	public long getGenStopTime() {
		return genStopTime;
	}

	public void startTime() {
		genStartTime = System.currentTimeMillis();
	}
	
	public void stopTime() {
		genStopTime = System.currentTimeMillis();
	}

	public double getStartTotalGoodness() {
		return startTotalGoodness;
	}

	public void setStartTotalGoodness(double startTotalGoodness) {
		this.startTotalGoodness = startTotalGoodness;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getTimesBelowGenThreshold() {
		return timesBelowGenThreshold;
	}

	public void setTimesBelowGenThreshold(int timesBelowGenThreshold) {
		this.timesBelowGenThreshold = timesBelowGenThreshold;
	}
	
	public void incrementTimesBelowGenThreshold() {
		timesBelowGenThreshold++;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public Map<String, String> getPrecinctToDistrict() {
		return precinctToDistrict;
	}

	public void setPrecinctToDistrict(Map<String, String> precinctToDistrict) {
		this.precinctToDistrict = precinctToDistrict;
	}

	public Map<String,Double> getDistrictsGoodness() {
		return districtsGoodness;
	}

	public void setDistrictsGoodness(Map<String,Double> districtsGoodness) {
		this.districtsGoodness = districtsGoodness;
	}
}
