package cdg.dao;

import java.util.HashMap;
import java.util.Map;

public class State extends Region {
	private Map<Integer,CongressionalDistrict> conDistricts;
	private Map<Integer,Precinct> precincts;
	
	public State()
	{
		super();
		conDistricts = new HashMap<>();
		setPrecincts(new HashMap<>());
	}
	public State(String name, String geoJsonGeometry, ElectionResult presidentialVoteTotals) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
		conDistricts = new HashMap<>();
		setPrecincts(new HashMap<>());
	}
	
	/**
	 * @return the conDistricts
	 */
	public Map<Integer, CongressionalDistrict> getConDistricts() {
		return conDistricts;
	}
	/**
	 * @param conDistricts the conDistricts to set
	 */
	public void setConDistricts(Map<Integer, CongressionalDistrict> conDistricts) {
		this.conDistricts = conDistricts;
	}
	/**
	 * @return the precincts
	 */
	public Map<Integer,Precinct> getPrecincts() {
		return precincts;
	}
	/**
	 * @param precincts the precincts to set
	 */
	public void setPrecincts(Map<Integer,Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public String getStateMapGeoJson()
	{
		return null;
	}
	
	public String getCongressionalMapGeoJson()
	{
		return null;
	}
	
	public String getPrecinctMapGeoJson()
	{
		return null;
	}
}
