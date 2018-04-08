package cdg.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import cdg.dto.MapDataDTO;
import cdg.repository.FakeData;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {
	//Fake data repository
	@Autowired
	FakeData fakeRepo;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(MapType.class, new MapTypeEnumConverter());
	}
	
	@RequestMapping( value = "/geojson/{stateid}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDTO> getStaticStateMap(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type) {
		//get state from database
		//fake data
		Optional<State> result = fakeRepo.findById(stateID);
		
		if (!result.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		State state = result.get();

		MapDTO map = state.getMap(type);
		if (map == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping( value = "data/{stateid}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDataDTO> getStaticStateData(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type)
	{
		//get state from database
		//fake data
		Optional<State> result = fakeRepo.findById(stateID);
		
		if (!result.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		State state = result.get();

		MapDataDTO data = state.getMapData(type);
		if (data == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/states", method=RequestMethod.GET)
	public List<String> getAllStates() {
		//Get all state's name fields from database, ordered alphabetically
		//fake data
		Collection<NameOnly> stateNames = fakeRepo.findAllProjectedBy();
		
		//Convert to readable format
		List<String> names = new ArrayList<String>();
		for (NameOnly stateName : stateNames)
		{
			names.add(stateName.getName());
		}
		
		return names;
	}
}
