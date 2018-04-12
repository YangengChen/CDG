package cdg.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import cdg.dao.NameOnly;
import cdg.dao.State;
import cdg.domain.map.MapType;
import cdg.domain.map.MapTypeEnumConverter;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;
import cdg.repository.FakeData;
import org.wololo.jts2geojson.GeoJSONReader;
import com.vividsolutions.jts.geom.Geometry;
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
		State state = fakeRepo.findByPublicId(stateID, State.class);
		
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		MapDTO map = state.getMap(type);
		if (map == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/file/{stateid}/{maptype}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource> getStaticStateMapFile(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type)
	{
		//get state from database
		//fake data
		State state = fakeRepo.findByPublicId(stateID, State.class);
				
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		FileSystemResource file = state.getMapFile(type);
		if (file == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(file, HttpStatus.OK);	
	}
	
	@RequestMapping( value = "data/{stateid}/{maptype}", method=RequestMethod.GET)
	public ResponseEntity<MapDataDTO> getStaticStateData(@PathVariable("stateid") int stateID, @PathVariable("maptype") MapType type)
	{
		//get state from database
		//fake data
		State state = fakeRepo.findByPublicId(stateID, State.class);
		
		if (state == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		MapDataDTO data = state.getMapData(type);
		if (data == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	/*@RequestMapping( value = "/us", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileSystemResource> getStaticStateMapFile()
	{
		State state = fakeRepo.findByPublicId(1000, State.class);
		
		GeoJSONReader reader = new GeoJSONReader();
		Geometry geom = reader.read(state.getPrecinctMapGeoJson());
	}*/
	
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
