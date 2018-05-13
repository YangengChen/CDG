package cdg.dto;

public class SavedMapDTO {
	private String id;
	private double schwarzbergWeight;
	private double hullRatioWeight;
	private double reockWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
	private String state;
	
	public SavedMapDTO(
			String id, 
			double schwarzbergWeight, 
			double hullRatioWeight, 
			double reockWeight, 
			double contiguityWeight, 
			double equalPopWeight, 
			double partisanFairWeight,
			String state) {
		this.id = id;
		this.schwarzbergWeight = schwarzbergWeight;
		this.hullRatioWeight = hullRatioWeight;
		this.reockWeight = reockWeight;
		this.contiguityWeight = contiguityWeight;
		this.equalPopWeight = equalPopWeight;
		this.partisanFairWeight = partisanFairWeight;
		this.state = state;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public double getSchwarzbergWeight() {
		return schwarzbergWeight;
	}

	public void setSchwarzbergWeight(double schwarzbergWeight) {
		this.schwarzbergWeight = schwarzbergWeight;
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

}
