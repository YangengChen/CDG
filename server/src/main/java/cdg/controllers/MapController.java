package cdg.controllers;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

import cdg.dao.NameOnly;
import cdg.dao.State;
import cdg.domain.map.MapType;
import cdg.domain.map.MapTypeEnumConverter;
import cdg.dto.MapDTO;

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
	
	@RequestMapping( value = "/geojson/{statename}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDTO> getStaticStateMap(@PathVariable("statename") String stateName, @PathVariable("maptype") MapType type) {
		//get state from database
		//fake data
		State state = new State(stateName.toLowerCase(),null,null);

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
		names.addAll(Arrays.asList("minnesota","washington","wisconsin"));
		
		return names;
	}
	
	private MapDTO populateMapDTO(String state, String geoJson) {
		MapDTO responseDTO = new MapDTO();
		responseDTO.setState(state);
		responseDTO.setGeoJson(geoJson);
		return responseDTO;
	}
}
