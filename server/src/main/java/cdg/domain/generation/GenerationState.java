package cdg.domain.generation;

public class GenerationState {
	private int currDistrictID;
	private int currDistrictIteration;
	private double currDistrictStartGoodness;
	private int currNeighborID;
	private int currGenIteration;
	private int candidatePrecinctUID;
	private double lastTotalGoodness;
	private long genStartTime;
	private long genStopTime;
	/**
	 * @return the currDistrictID
	 */
	public int getCurrDistrictID() {
		return currDistrictID;
	}
	/**
	 * @param currDistrictID the currDistrictID to set
	 */
	public void setCurrDistrictID(int currDistrictID) {
		this.currDistrictID = currDistrictID;
	}
	/**
	 * @return the currDistrictIteration
	 */
	public int getCurrDistrictIteration() {
		return currDistrictIteration;
	}
	/**
	 * @param currDistrictIteration the currDistrictIteration to set
	 */
	public void setCurrDistrictIteration(int currDistrictIteration) {
		this.currDistrictIteration = currDistrictIteration;
	}
	/**
	 * @return the currDistrictStartGoodness
	 */
	public double getCurrDistrictStartGoodness() {
		return currDistrictStartGoodness;
	}
	/**
	 * @param currDistrictStartGoodness the currDistrictStartGoodness to set
	 */
	public void setCurrDistrictStartGoodness(double currDistrictStartGoodness) {
		this.currDistrictStartGoodness = currDistrictStartGoodness;
	}
	/**
	 * @return the currNeighborID
	 */
	public int getCurrNeighborID() {
		return currNeighborID;
	}
	/**
	 * @param currNeighborID the currNeighborID to set
	 */
	public void setCurrNeighborID(int currNeighborID) {
		this.currNeighborID = currNeighborID;
	}
	/**
	 * @return the currGenIteration
	 */
	public int getCurrGenIteration() {
		return currGenIteration;
	}
	/**
	 * @param currGenIteration the currGenIteration to set
	 */
	public void setCurrGenIteration(int currGenIteration) {
		this.currGenIteration = currGenIteration;
	}
	/**
	 * @return the candidatePrecinctUID
	 */
	public int getCandidatePrecinctUID() {
		return candidatePrecinctUID;
	}
	/**
	 * @param candidatePrecinctUID the candidatePrecinctUID to set
	 */
	public void setCandidatePrecinctUID(int candidatePrecinctUID) {
		this.candidatePrecinctUID = candidatePrecinctUID;
	}
	/**
	 * @return the lastTotalGoodness
	 */
	public double getLastTotalGoodness() {
		return lastTotalGoodness;
	}
	/**
	 * @param lastTotalGoodness the lastTotalGoodness to set
	 */
	public void setLastTotalGoodness(double lastTotalGoodness) {
		this.lastTotalGoodness = lastTotalGoodness;
	}
	/**
	 * @return the genStartTime
	 */
	public long getGenStartTime() {
		return genStartTime;
	}
	/**
	 * @param genStartTime the genStartTime to set
	 */
	public void setGenStartTime(long genStartTime) {
		this.genStartTime = genStartTime;
	}
	/**
	 * @return the genStopTime
	 */
	public long getGenStopTime() {
		return genStopTime;
	}
	/**
	 * @param genStopTime the genStopTime to set
	 */
	public void setGenStopTime(long genStopTime) {
		this.genStopTime = genStopTime;
	}
}
