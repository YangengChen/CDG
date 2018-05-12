package cdg.domain.generation;

public class PrecinctDistrictMap {
	private String precinctID;
	private String districtID;
	
	public PrecinctDistrictMap() {}
	
	public PrecinctDistrictMap(String precinctID, String districtID) {
		this.precinctID = precinctID;
		this.districtID = districtID;
	}
	
	public String getPrecinctID() {
		return precinctID;
	}
	public void setPrecinctID(String precinctID) {
		this.precinctID = precinctID;
	}
	public String getDistrictID() {
		return districtID;
	}
	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}
}
