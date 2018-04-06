package cdg.dao;

import java.util.HashMap;
import java.util.Map;

public class Region {
	private int id;
	private String name;
	private String geoJsonGeometry;
	private ElectionResult presidentialVoteTotals;
	private Map<Integer,Region> neighborRegions;
	
	public Region()
	{
		neighborRegions = new HashMap<>();
	}
	public Region(String name, String geoJsonGeometry, ElectionResult presidentialVoteTotals) {
		this();
		this.name = name;
		this.geoJsonGeometry = geoJsonGeometry;
		this.presidentialVoteTotals = presidentialVoteTotals;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the geoJsonGeometry
	 */
	public String getGeoJsonGeometry() {
		return geoJsonGeometry;
	}

	/**
	 * @param geoJsonGeometry the geoJsonGeometry to set
	 */
	public void setGeoJsonGeometry(String geoJsonGeometry) {
		this.geoJsonGeometry = geoJsonGeometry;
	}

	/**
	 * @return the neighborRegions
	 */
	public Map<Integer, Region> getNeighborRegions() {
		return neighborRegions;
	}

	/**
	 * @param neighborRegions the neighborRegions to set
	 */
	public void setNeighborRegions(Map<Integer, Region> neighborRegions) {
		this.neighborRegions = neighborRegions;
	}
	/**
	 * @return the presidentialVoteTotals
	 */
	public ElectionResult getPresidentialVoteTotals() {
		return presidentialVoteTotals;
	}
	/**
	 * @param presidentialVoteTotals the presidentialVoteTotals to set
	 */
	public void setPresidentialVoteTotals(ElectionResult presidentialVoteTotals) {
		this.presidentialVoteTotals = presidentialVoteTotals;
	}

}
