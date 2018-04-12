package cdg.dao;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
	/**
	 * @param precincts the precincts to set
	 */
	public void setPrecincts(Map<Integer,Precinct> precincts) {
		this.precincts = precincts;
	}
	
	public FileSystemResource getMapFile(MapType type)
	{
		if (type == null)
			throw new NullPointerException();
		
		File mapFile = null;
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
		if (mapFile == null)
			return null;
		
		return new FileSystemResource(mapFile);
	}
	
	public File getStateMapFile()
	{
		return null;
	}
	
	public File getConDistrictMapFile()
	{
		return null;
	}
	
	public File getPrecinctMapFile()
	{
		try 
		{
			Resource resource = null;
			if (this.getName().equals("minnesota"))
				resource = new ClassPathResource("minnesota_precincts.geojson");
			else if (this.getName().equals("wisconsin"))
				resource = new ClassPathResource("wisconsin_precincts.geojson");
			else if (this.getName().equals("washington"))
				resource = new ClassPathResource("washington_precincts.geojson");
			if (resource == null)
				return null;
			return resource.getFile();
		}
		catch (IOException ioe)
		{
			return null;
		}
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
		//fake data
		String geoJson = null;
		try {
			Resource resource = null;
			if (this.getName().equals("minnesota"))
				resource = new ClassPathResource("minnesota_precincts.geojson");
			else if (this.getName().equals("wisconsin"))
				resource = new ClassPathResource("wisconsin_precincts.geojson");
			else if (this.getName().equals("washington"))
				resource = new ClassPathResource("washington_precincts.geojson");
			if (resource == null)
				return null;
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
	/**
	 * @return the stateMapFile
	 */
	public Blob getStateMapBlob() {
		return stateMapFile;
	}
	/**
	 * @param stateMapFile the stateMapFile to set
	 */
	public void setStateMapBlob(Blob stateMapFile) {
		this.stateMapFile = stateMapFile;
	}
	/**
	 * @return the conDistrictMapFile
	 */
	public Blob getConDistrictMapBlob() {
		return conDistrictMapFile;
	}
	/**
	 * @param conDistrictMapFile the conDistrictMapFile to set
	 */
	public void setConDistrictMapBlob(Blob conDistrictMapFile) {
		this.conDistrictMapFile = conDistrictMapFile;
	}
	/**
	 * @return the precinctMapFile
	 */
	public Blob getPrecinctMapBlob() {
		return precinctMapFile;
	}
	/**
	 * @param precinctMapFile the precinctMapFile to set
	 */
	public void setPrecinctMapBlob(Blob precinctMapFile) {
		this.precinctMapFile = precinctMapFile;
	}
}
