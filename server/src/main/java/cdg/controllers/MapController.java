package cdg.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import org.springframework.web.bind.annotation.CrossOrigin;

import cdg.dao.NameOnly;
import cdg.dao.State;
import cdg.domain.map.MapType;
import cdg.domain.map.MapTypeEnumConverter;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;
import cdg.properties.CdgConstants;
import cdg.repository.FakeData;
import cdg.services.MapService;

@RestController
@RequestMapping(CdgConstants.MAP_CONTROLLER_PATH_PREFIX)
@CrossOrigin(origins = CdgConstants.CROSS_ORIGIN_LOCATION)
public class MapController {
	//Fake data repository
	@Autowired
	FakeData fakeRepo;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(MapType.class, new MapTypeEnumConverter());
	}
	
	@RequestMapping( value = CdgConstants.MAP_STATIC_MAP_PATH, method=RequestMethod.GET)
	public ResponseEntity<MapDTO> getStaticStateMap(@PathVariable(CdgConstants.MAP_STATEID_PATH_VARIABLE) String stateID, @PathVariable(CdgConstants.MAP_MAPTYPE_PATH_VARIABLE) MapType type) {
		//get state from database
		//fake data
		State state = fakeRepo.findByPublicId(stateID, State.class);
		
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		MapDTO map = state.getMap(type);
		if (map == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.MAP_STATIC_MAP_FILE_PATH, method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<byte[]> getStaticStateMapFile(@PathVariable(CdgConstants.MAP_STATEID_PATH_VARIABLE) String stateID, @PathVariable(CdgConstants.MAP_MAPTYPE_PATH_VARIABLE) MapType type) {
		//get state from database
		//fake data
		State state = fakeRepo.findByPublicId(stateID, State.class);
				
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		byte[] file = state.getMapFile(type);
		if (file == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    httpHeaders.setContentLength(file.length);
		return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);	
	}
	
	@RequestMapping( value = CdgConstants.MAP_STATIC_MAP_DATA_PATH, method=RequestMethod.GET)
	public ResponseEntity<MapDataDTO> getStaticStateData(@PathVariable(CdgConstants.MAP_STATEID_PATH_VARIABLE) String stateID, @PathVariable(CdgConstants.MAP_MAPTYPE_PATH_VARIABLE) MapType type) {
		//get state from database
		//fake data
		State state = fakeRepo.findByPublicId(stateID, State.class);
		
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		MapDataDTO data = state.getMapData(type);
		if (data == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.MAP_US_MAP_PATH, method=RequestMethod.GET)
	public ResponseEntity<byte[]> getUnitedStatesMap() {
		//fake - get states from repo
		Iterable<State> states = fakeRepo.findAll();
		String usMap;
		try {
			usMap = MapService.generateUnitedStatesMap(Lists.newArrayList(states));
		} catch (IllegalStateException ise) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		byte[] usMapFile = MapService.getMapAsBytes(usMap);
		
		return new ResponseEntity<>(usMapFile, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.MAP_ALL_STATES_PATH, method=RequestMethod.GET)
	public List<NameOnly> getAllStates() {
		//Get all state's name fields from database, ordered alphabetically
		//fake data
		Collection<NameOnly> stateNames = fakeRepo.findAllProjectedBy();
		
		//Convert to readable format
		List<NameOnly> names = new ArrayList<NameOnly>();
		names.addAll(stateNames);
		
		return names;
	}
}
