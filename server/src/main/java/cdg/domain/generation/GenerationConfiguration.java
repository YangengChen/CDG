package cdg.domain.generation;

import java.util.List;

public class GenerationConfiguration {
	private String stateId;
	private List<Integer> permConDist;
	private List<Integer> permPrecinct;
	private double compactnessWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
	private double racialFairWeight;
	
	public List<Integer> getPermConDist() {
		return permConDist;
	}

	public void setPermConDist(List<Integer> permConDist) {
		this.permConDist = permConDist;
	}

	public List<Integer> getPermPrecinct() {
		return permPrecinct;
	}

	public void setPermPrecinct(List<Integer> permPrecinct) {
		this.permPrecinct = permPrecinct;
	}

	public double getCompactnessWeight() {
		return compactnessWeight;
	}

	public void setCompactnessWeight(double compactnessWeight) {
		this.compactnessWeight = compactnessWeight;
	}

	public double getContiguityWeight() {
		return contiguityWeight;
	}

	public void setContiguityWeight(double contiguityWeight) {
		this.contiguityWeight = contiguityWeight;
	}

	public double getEqualPopWeight() {
		return equalPopWeight;
	}

	public void setEqualPopWeight(double equalPopWeight) {
		this.equalPopWeight = equalPopWeight;
	}

	public double getPartisanFairWeight() {
		return partisanFairWeight;
	}

	public void setPartisanFairWeight(double partisanFairWeight) {
		this.partisanFairWeight = partisanFairWeight;
	}

	public double getRacialFairWeight() {
		return racialFairWeight;
	}

	public void setRacialFairWeight(double racialFairWeight) {
		this.racialFairWeight = racialFairWeight;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
}
