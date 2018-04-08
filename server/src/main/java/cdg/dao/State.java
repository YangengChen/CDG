package cdg.dao;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cdg.domain.map.MapType;
import cdg.dto.DistrictDTO;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;

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
	
	public MapDTO getMap(MapType type)
	{
		if (type == null)
			throw new NullPointerException();
		
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
		return null;
	}
	
	public String getPrecinctMapGeoJson()
	{
		String geoJson = null;
		try {
			Resource resource = new ClassPathResource("minnesota_precincts.geojson");
			StringWriter writer = new StringWriter();
			IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8);
			geoJson = writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return geoJson;
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
			throw new NullPointerException();
		
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
			throw new NullPointerException();
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
}
