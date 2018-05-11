package cdg.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cdg.domain.map.MapType;
import cdg.dto.DistrictDTO;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;
import cdg.services.MapService;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name = "States")
public class State extends Region {
	@OneToMany(mappedBy="state", cascade= {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,CongressionalDistrict> conDistricts;
	@OneToMany(mappedBy="state", cascade= {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,Precinct> precincts;
	
	public State()
	{
		super();
	}
	public State(String name, byte[] geoJsonGeometry, ElectionResult presidentialVoteTotals) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
	}
	
	public Map<Integer, CongressionalDistrict> getConDistricts() {
		return conDistricts;
	}

	public void setConDistricts(Map<Integer, CongressionalDistrict> conDistricts) {
		this.conDistricts = conDistricts;
	}
	
	public int numConDistricts() {
		if (getConDistricts() == null) {
			return 0;
		}
		return conDistricts.size();
	}
	
	public Map<Integer,Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(Map<Integer,Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public int numPrecincts() {
		if (getPrecincts() == null) {
			return 0;
		}
		return precincts.size();
	}
	
	public String getMapGeoJSON(MapType type) {
		if (type == null)
			throw new IllegalArgumentException();

		String map;
		try {
			map = MapService.generateMap(this, type);
		} catch (IllegalStateException ise) {
			return null;
		}
		return map;
	}
	
	public byte[] getMapFile(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		String geoJSONStr = getMapGeoJSON(type);
		if (geoJSONStr == null) {
			return null;
		}
		byte[] geoJSON = MapService.getMapAsBytes(geoJSONStr);
		return geoJSON;
	}
	
	public MapDTO getMap(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();
		
		String geoJson = getMapGeoJSON(type);
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
		data.setPopulation(this.getPopulation());
		data.setPresidentialElection(this.getPresidentialVoteTotals());
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
