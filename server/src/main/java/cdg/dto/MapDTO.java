package cdg.dto;

public class MapDTO {
	private String state;
	private String geoJson;
	private MapDataDTO data;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGeoJson() {
		return geoJson;
	}

	public void setGeoJson(String geoJson) {
		this.geoJson = geoJson;
	}

	public MapDataDTO getData() {
		return data;
	}

	public void setData(MapDataDTO data) {
		this.data = data;
	}
}
