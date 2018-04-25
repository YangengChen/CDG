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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.LazyGroup;

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
	@LazyGroup("stateGeo")
	private byte[] stateMapGeoJSON;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup("districtGeo")
	private byte[] conDistrictMapGeoJSON;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup("precinctGeo")
	private byte[] precinctMapGeoJSON;
	
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
	
	public Map<Integer,Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(Map<Integer,Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public byte[] getStateMapGeoJSON()
	{
		return stateMapGeoJSON;
	}
	
	public void setStateMapGeoJSON(byte[] geoJSON) {
		stateMapGeoJSON = geoJSON;
	}
	
	public byte[] getConDistrictMapGeoJSON()
	{
		return conDistrictMapGeoJSON;
	}
	
	public void setConDistrictMapGeoJSON(byte[] geoJSON) {
		conDistrictMapGeoJSON = geoJSON;
	}
	
	public byte[] getPrecinctMapGeoJSON()
	{
		return precinctMapGeoJSON;
	}
	
	public void setPrecinctMapGeoJSON(byte[] geoJSON) {
		precinctMapGeoJSON = geoJSON;
	}
	
	public String getMapGeoJSON(MapType type) {
		if (type == null)
			throw new IllegalArgumentException();

		byte[] geoJSON = getMapFile(type);
		if (geoJSON == null) {
			return null;
		}
		String map = convertGeoToString(geoJSON);
		return map;
	}
	
	public void setMapGeoJSON(String geoJSON, MapType type) {
		if (type == null || geoJSON == null)
			throw new IllegalArgumentException();

		byte[] map = convertStringToGeo(geoJSON);
		setMapFile(map, type);
	}
	
	public byte[] getMapFile(MapType type)
	{
		if (type == null)
			throw new IllegalArgumentException();

		byte[] geoJSON = null;
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
	
	public void setMapFile(byte[] geoJSON, MapType type) {
		if (type == null || geoJSON == null)
			throw new IllegalArgumentException();

		switch (type)
		{
			case STATE:
				setStateMapGeoJSON(geoJSON);
				break;
			case CONGRESSIONAL:
				setConDistrictMapGeoJSON(geoJSON);
				break;
			case PRECINCT:
				setPrecinctMapGeoJSON(geoJSON);
				break;
			default:
				throw new IllegalArgumentException();
		}
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
