package cdg.domain.generation;

import java.util.List;

public class GenerationConfiguration {
	private String state;
	private List<Integer> permConDist;
	private List<Integer> permPrecinct;
	private double compactnessWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
	private double racialFairWeight;
	
	/**
	 * @return the permConDist
	 */
	public List<Integer> getPermConDist() {
		return permConDist;
	}
	/**
	 * @param permConDist the permConDist to set
	 */
	public void setPermConDist(List<Integer> permConDist) {
		this.permConDist = permConDist;
	}
	/**
	 * @return the permPrecinct
	 */
	public List<Integer> getPermPrecinct() {
		return permPrecinct;
	}
	/**
	 * @param permPrecinct the permPrecinct to set
	 */
	public void setPermPrecinct(List<Integer> permPrecinct) {
		this.permPrecinct = permPrecinct;
	}
	/**
	 * @return the compactnessWeight
	 */
	public double getCompactnessWeight() {
		return compactnessWeight;
	}
	/**
	 * @param compactnessWeight the compactnessWeight to set
	 */
	public void setCompactnessWeight(double compactnessWeight) {
		this.compactnessWeight = compactnessWeight;
	}
	/**
	 * @return the contiguityWeight
	 */
	public double getContiguityWeight() {
		return contiguityWeight;
	}
	/**
	 * @param contiguityWeight the contiguityWeight to set
	 */
	public void setContiguityWeight(double contiguityWeight) {
		this.contiguityWeight = contiguityWeight;
	}
	/**
	 * @return the equalPopWeight
	 */
	public double getEqualPopWeight() {
		return equalPopWeight;
	}
	/**
	 * @param equalPopWeight the equalPopWeight to set
	 */
	public void setEqualPopWeight(double equalPopWeight) {
		this.equalPopWeight = equalPopWeight;
	}
	/**
	 * @return the partisanFairWeight
	 */
	public double getPartisanFairWeight() {
		return partisanFairWeight;
	}
	/**
	 * @param partisanFairWeight the partisanFairWeight to set
	 */
	public void setPartisanFairWeight(double partisanFairWeight) {
		this.partisanFairWeight = partisanFairWeight;
	}
	/**
	 * @return the racialFairWeight
	 */
	public double getRacialFairWeight() {
		return racialFairWeight;
	}
	/**
	 * @param racialFairWeight the racialFairWeight to set
	 */
	public void setRacialFairWeight(double racialFairWeight) {
		this.racialFairWeight = racialFairWeight;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
