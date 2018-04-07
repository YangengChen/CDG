package cdg.dto;

import java.util.List;

public class MapDataDTO {
	private String state;
	private List<DistrictDTO> districts;
	
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
	/**
	 * @return the districts
	 */
	public List<DistrictDTO> getDistricts() {
		return districts;
	}
	/**
	 * @param districts the districts to set
	 */
	public void setDistricts(List<DistrictDTO> districts) {
		this.districts = districts;
	}
}
