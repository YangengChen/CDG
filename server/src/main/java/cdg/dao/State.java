package cdg.dao;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cdg.domain.map.MapType;
import cdg.dto.DistrictDTO;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Basic;
import javax.persistence.CascadeType;

@Entity
@Table(name = "States")
public class State extends Region {
	@OneToMany(mappedBy="state", cascade= {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,CongressionalDistrict> conDistricts;
	@OneToMany(mappedBy="state", cascade= {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,Precinct> precincts;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String stateMapGeoJSON;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String conDistrictMapGeoJSON;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String precinctMapGeoJSON;
	
	public State()
	{
		super();
	}
	public State(String name, String geoJsonGeometry, ElectionResult presidentialVoteTotals) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
	}
	
	public Map<Integer, CongressionalDistrict> getConDistricts() {
		return conDistricts;
	}

	public void setConDistricts(Map<Integer, CongressionalDistrict> conDistricts) {
		this.conDistricts = conDistricts;
	}
	
	public Map<Integer,Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(Map<Integer,Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public String getStateMapGeoJSON()
	{
		return stateMapGeoJSON;
	}
	
	public void setStateMapGeoJSON(String geoJSON) {
		stateMapGeoJSON = geoJSON;
	}
	
	public String getConDistrictMapGeoJSON()
	{
		return conDistrictMapGeoJSON;
	}
	
	public void setConDistrictMapGeoJSON(String geoJSON) {
		conDistrictMapGeoJSON = geoJSON;
	}
	
	public String getPrecinctMapGeoJSON()
	{
		return precinctMapGeoJSON;
	}
	
	public void setPrecinctMapGeoJSON(String geoJSON) {
		precinctMapGeoJSON = geoJSON;
	}
	
	public String getGeoJSON(MapType type) {
		if (type == null)
			throw new IllegalArgumentException();

		String geoJSON = null;
		switch (type)
		{
			case STATE:
				geoJSON = getStateMapGeoJSON();
				break;
			case CONGRESSIONAL:
				geoJSON = getConDistrictMapGeoJSON();
				break;
			case PRECINCT:
				geoJSON = getPrecinctMapGeoJSON();
				break;
			default:
				geoJSON = null;
				break;
		}
		return geoJSON;
	}
	
	public byte[] getMapFile(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();

		String geoJSON = getGeoJSON(type);
		if (geoJSON == null) {
			return null;
		}
		
		byte[] mapFile = getMapFile(geoJSON);
		return mapFile;
	}
	
	private byte[] getMapFile(String geoJson)
	{
		if (geoJson == null) {
			return null;
		}
		return geoJson.getBytes(StandardCharsets.UTF_8);
	}
	
	public MapDTO getMap(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		
		String geoJson = getGeoJSON(type);
		if (geoJson == null)
			return null;
		
		MapDTO map = new MapDTO();
		map.setState(this.getName());
		map.setGeoJson(geoJson);
		
		return map;
	}
	
	public DistrictDTO getDataDTO()
	{
		DistrictDTO data = new DistrictDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		return data;
	}
	
	public MapDataDTO getMapData(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		
		switch (type)
		{
			case STATE:
				return getStateData();
			case CONGRESSIONAL:
				return getCongressionalData();
			case PRECINCT:
				return getPrecinctData();
			default:
				return null;
		}
	}
	
	public MapDataDTO getStateData()
	{
		List<Region> regions = Arrays.asList(this);
		return populateDataDTO(regions);
	}
	
	public MapDataDTO getCongressionalData()
	{
		Map<Integer,CongressionalDistrict> currDistricts = getConDistricts();
		if (currDistricts == null) {
			return null;
		}
		List<Region> regions = new ArrayList<>(currDistricts.values());
		return populateDataDTO(regions);
	}
	
	public MapDataDTO getPrecinctData()
	{
		Map<Integer,Precinct> currPrecincts = getPrecincts();
		if (currPrecincts == null) {
			return null;
		}
		List<Region> regions = new ArrayList<>(currPrecincts.values());
		return populateDataDTO(regions);
	}
	
	private MapDataDTO populateDataDTO(Collection<Region> regions)
	{
		if (regions == null)
			throw new IllegalArgumentException();
		MapDataDTO data = new MapDataDTO();
		data.setState(this.getName());
		List<DistrictDTO> regionDTOs = new ArrayList<>();
		for (Region reg : regions)
		{
			regionDTOs.add(reg.getDataDTO());
		}
		data.setDistricts(regionDTOs);
		return data;
	}
}
