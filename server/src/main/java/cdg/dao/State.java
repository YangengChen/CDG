package cdg.dao;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cdg.domain.map.MapType;
import cdg.dto.DistrictDTO;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;

public class State extends Region {
	private Map<Integer,CongressionalDistrict> conDistricts;
	private Map<Integer,Precinct> precincts;
	private Blob stateMapFile;
	private Blob conDistrictMapFile;
	private Blob precinctMapFile;
	private String stateMapGeoJSON;
	private String conDistrictMapGeoJSON;
	private String precinctMapGeoJSON;
	
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
	
	public byte[] getMapFile(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		
		byte[] mapFile = null;
		switch (type)
		{
			case STATE:
				mapFile = getStateMapFile();
				break;
			case CONGRESSIONAL:
				mapFile = getConDistrictMapFile();
				break;
			case PRECINCT:
				mapFile = getPrecinctMapFile();
				break;
			default:
				mapFile = null;
				break;
		}
		return mapFile;
	}
	
	public byte[] getStateMapFile()
	{
		return null;
	}
	
	public byte[] getConDistrictMapFile()
	{
		if (conDistrictMapGeoJSON == null) {
			return null;
		}
		//fake
		return conDistrictMapGeoJSON.getBytes(StandardCharsets.UTF_8);
	}
	
	public byte[] getPrecinctMapFile()
	{
		if (precinctMapGeoJSON == null) {
			return null;
		}
		//fake
		return precinctMapGeoJSON.getBytes(StandardCharsets.UTF_8);
	}
	
	
	public MapDTO getMap(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		
		String geoJson = null;
		switch (type)
		{
			case STATE:
				geoJson = getStateMapGeoJson();
				break;
			case CONGRESSIONAL:
				geoJson = getCongressionalMapGeoJson();
				break;
			case PRECINCT:
				geoJson = getPrecinctMapGeoJson();
				break;
			default:
				geoJson = null;
				break;
		}
		if (geoJson == null)
			return null;
		
		MapDTO map = new MapDTO();
		map.setState(this.getName());
		map.setGeoJson(geoJson);
		
		return map;
	}
	
	public String getStateMapGeoJson()
	{
		return null;
	}
	
	public String getCongressionalMapGeoJson()
	{
		//fake
		return conDistrictMapGeoJSON;
	}
	
	public void setCongressionalMapGeoJson(String geoJSON) {
		conDistrictMapGeoJSON = geoJSON;
	}
	
	public String getPrecinctMapGeoJson()
	{
		//fake
		return precinctMapGeoJSON;
	}
	
	public void setPrecinctMapGeoJson(String geoJSON) {
		precinctMapGeoJSON = geoJSON;
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
		List<Region> districts = Arrays.asList(this);
		return populateDataDTO(districts);
	}
	
	public MapDataDTO getCongressionalData()
	{
		List<Region> districts = new ArrayList<>(conDistricts.values());
		return populateDataDTO(districts);
	}
	
	public MapDataDTO getPrecinctData()
	{
		List<Region> districts = new ArrayList<>(precincts.values());
		return populateDataDTO(districts);
	}
	
	private MapDataDTO populateDataDTO(Collection<Region> regions)
	{
		if (regions == null)
			throw new IllegalArgumentException();
		MapDataDTO data = new MapDataDTO();
		data.setState(this.getName());
		List<DistrictDTO> districts = new ArrayList<>();
		for (Region dist : regions)
		{
			districts.add(dist.getDataDTO());
		}
		data.setDistricts(districts);
		return data;
	}

	public Blob getStateMapBlob() {
		return stateMapFile;
	}

	public void setStateMapBlob(Blob stateMapFile) {
		this.stateMapFile = stateMapFile;
	}

	public Blob getConDistrictMapBlob() {
		return conDistrictMapFile;
	}

	public void setConDistrictMapBlob(Blob conDistrictMapFile) {
		this.conDistrictMapFile = conDistrictMapFile;
	}

	public Blob getPrecinctMapBlob() {
		return precinctMapFile;
	}

	public void setPrecinctMapBlob(Blob precinctMapFile) {
		this.precinctMapFile = precinctMapFile;
	}
}
