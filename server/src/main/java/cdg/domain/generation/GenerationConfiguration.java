package cdg.domain.generation;

import java.util.List;
import java.util.Map;

public class GenerationConfiguration {
	private String stateId;
	private List<String> permConDist;
	private List<String> permPrecinct;
	private boolean sameCounty;
	private double schwartzbergWeight;
	private double hullRatioWeight;
	private double reockWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
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

	public double getSchwartzbergWeight() {
		return schwartzbergWeight;
	}

	public void setSchwartzbergWeight(double schwarzbergWeight) {
		this.schwartzbergWeight = schwarzbergWeight;
	}

	public double getHullRatioWeight() {
		return hullRatioWeight;
	}

	public void setHullRatioWeight(double hullRatioWeight) {
		this.hullRatioWeight = hullRatioWeight;
	}

	public double getReockWeight() {
		return reockWeight;
	}

	public void setReockWeight(double reockWeight) {
		this.reockWeight = reockWeight;
	}

	public boolean isSameCounty() {
		return sameCounty;
	}

	public void setSameCounty(boolean sameCounty) {
		this.sameCounty = sameCounty;
	}
}
