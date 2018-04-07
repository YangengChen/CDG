package cdg.dto;

public class MapDTO {
	private String state;
	private String geoJson;
	private MapDataDTO data;
	
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
	 * @return the geoJson
	 */
	public String getGeoJson() {
		return geoJson;
	}
	/**
	 * @param geoJson the geoJson to set
	 */
	public void setGeoJson(String geoJson) {
		this.geoJson = geoJson;
	}
	/**
	 * @return the data
	 */
	public MapDataDTO getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(MapDataDTO data) {
		this.data = data;
	}
}
