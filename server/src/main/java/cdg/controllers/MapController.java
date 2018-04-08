package cdg.controllers;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import cdg.dao.CongressionalDistrict;
import cdg.dao.NameOnly;
import cdg.dao.Precinct;
import cdg.dao.State;
import cdg.domain.map.MapType;
import cdg.domain.map.MapTypeEnumConverter;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {
	@Autowired
	private ResourceLoader resourceLoader;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(MapType.class, new MapTypeEnumConverter());
	}
	
	@RequestMapping( value = "/geojson/{stateid}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDTO> getStaticStateMap(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type) {
		//get state from database
		//fake data
		State state = getFakeStates().get(stateID);

		//get maps based on map type
		String geoJson = null;
		switch (type)
		{
			case STATE:
				geoJson = state.getStateMapGeoJson();
				break;
			case CONGRESSIONAL:
				geoJson = state.getCongressionalMapGeoJson();
				break;
			case PRECINCT:
				//geoJson = state.getPrecinctMapGeoJson();
				//fake data
				try {
					Resource resource = resourceLoader.getResource("classpath:minnesota_precincts.geojson");
					StringWriter writer = new StringWriter();
					IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8);
					geoJson = writer.toString();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				geoJson = null;
				break;
		}
		if (geoJson == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		//generate DTO
		MapDTO responseDTO = populateMapDTO(state.getName(), geoJson);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@RequestMapping( value = "data/{stateid}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDataDTO> getStaticStateData(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type)
	{
		//get state from database
		//fake data
		State state = getFakeStates().get(stateID);

		MapDataDTO data = state.getMapData(type);
		if (data == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/states", method=RequestMethod.GET)
	public List<String> getAllStates() {
		//Get all state's name fields from database, ordered alphabetically
		//fake data
		Collection<NameOnly> stateNames = new ArrayList<NameOnly>();
		
		//Convert to readable format
		List<String> names = new ArrayList<String>();
		for (NameOnly stateName : stateNames)
		{
			names.add(stateName.getName());
		}
		
		//fake data
		for (State state : getFakeStates().values())
		{
			names.add(state.getName());
		}
		
		return names;
	}
	
	private MapDTO populateMapDTO(String state, String geoJson) {
		MapDTO responseDTO = new MapDTO();
		responseDTO.setState(state);
		responseDTO.setGeoJson(geoJson);
		return responseDTO;
	}
	
	private Map<Integer,State> getFakeStates()
	{
		Map<Integer,State> states = new HashMap<>();
		State state;
		Map<Integer,CongressionalDistrict> districts;
		Map<Integer,Precinct> precincts;
		CongressionalDistrict conDist;
		Precinct pre;
		
		state  = new State("minnesota", null, null);
		state.setPublicID(1000);
		districts = new HashMap<>();
		conDist = new CongressionalDistrict("Min 11", null, null, null, 100);
		conDist.setPublicID(11);
		districts.put(1, conDist);
		conDist = new CongressionalDistrict("Min 22", null, null, null, 200);
		conDist.setPublicID(22);
		districts.put(2, conDist);
		state.setConDistricts(districts);
		precincts = new HashMap<>();
		pre = new Precinct("Min 33", null, null, null, null);
		pre.setPublicID(33);
		precincts.put(3, pre);
		pre = new Precinct("Min 44", null, null, null, null);
		pre.setPublicID(44);
		precincts.put(4, pre);
		state.setPrecincts(precincts);
		states.put(1, state);
		

		states.put(2, new State("washington",null,null));
		states.put(3, new State("wisconsin",null,null));
		return states;
	}
}
