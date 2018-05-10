package cdg.domain.generation;

import java.util.List;
import java.util.Map;

public class GenerationConfiguration {
	private String stateId;
	private List<String> permConDist;
	private List<String> permPrecinct;
	private double compactnessWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
	private double racialFairWeight;
	private Map<String,String> precinctToDistrict;
	
	public List<String> getPermConDist() {
		return permConDist;
	}

	public void setPermConDist(List<String> permConDist) {
		this.permConDist = permConDist;
	}

	public List<String> getPermPrecinct() {
		return permPrecinct;
	}

	public void setPermPrecinct(List<String> permPrecinct) {
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

	public Map<String, String> getPrecinctToDistrict() {
		return precinctToDistrict;
	}

	public void setPrecinctToDistrict(Map<String, String> precinctToDistrict) {
		this.precinctToDistrict = precinctToDistrict;
	}
}
