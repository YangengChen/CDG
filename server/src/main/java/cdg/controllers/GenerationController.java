package cdg.controllers;

import static cdg.properties.CdgConstants.SESSION_USER;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdg.dao.SavedMap;
import cdg.dao.State;
import cdg.dao.User;
import cdg.domain.generation.CdgConstraintEvaluator;
import cdg.domain.generation.CdgGoodnessEvaluator;
import cdg.domain.generation.ConstraintEvaluator;
import cdg.domain.generation.GenerationConfiguration;
import cdg.domain.generation.GenerationStatus;
import cdg.domain.generation.GoodnessEvaluator;
import cdg.domain.generation.MapGenerator;
import cdg.domain.map.MapType;
import cdg.domain.map.MapTypeEnumConverter;
import cdg.dto.MapDataDTO;
import cdg.properties.CdgConstants;
import cdg.repository.SavedMapRepository;
import cdg.repository.StateRepository;
import cdg.responses.GenerationResponse;
import cdg.services.MapService;

@RestController
@RequestMapping(CdgConstants.GENERATION_CONTROLLER_PATH_PREFIX)
@CrossOrigin(origins = CdgConstants.CROSS_ORIGIN_LOCATION)
public class GenerationController {
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private SavedMapRepository savedMapRepo;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
	    dataBinder.registerCustomEditor(MapType.class, new MapTypeEnumConverter());
	}

	@RequestMapping( value = CdgConstants.GENERATION_START_PATH, method=RequestMethod.POST)
	public ResponseEntity<GenerationStatus> startGeneration(@RequestBody GenerationConfiguration config, HttpSession session)
	{
		if (config == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.BAD_REQUEST);
		}
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.UNAUTHORIZED);
		}
		Optional<State> stateOpt = stateRepo.findByPublicID(config.getStateId(), State.class);
		if (!stateOpt.isPresent()) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.NOT_FOUND);
		}
		State state = stateOpt.get();
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Map<String,String> manualMappings = null;
		ConstraintEvaluator constraintEval;
		GoodnessEvaluator goodnessEval;
		try {
			manualMappings = config.getPrecinctToDistrict();
			constraintEval = CdgConstraintEvaluator.toConstraintEvaluator(config);
			goodnessEval = CdgGoodnessEvaluator.toGoodnessEvaluator(CdgConstants.MAX_GOODNESS, config);
		} catch (IllegalArgumentException iae) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.BAD_REQUEST);
		}
		generator.resetGenerator(config.getStateId(), goodnessEval, constraintEval);
		
		try {
			generator.startGeneration(state, manualMappings);
		} catch (IllegalArgumentException iae) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.BAD_REQUEST);
		} catch (IllegalStateException ise) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<GenerationStatus>(GenerationStatus.INPROGRESS, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_CANCEL_PATH, method=RequestMethod.POST)
	public ResponseEntity<GenerationStatus> cancelGeneration(HttpSession session)
	{
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		generator.stopGeneration();
		
		return new ResponseEntity<GenerationStatus>(GenerationStatus.CANCELED, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_PAUSE_PATH, method=RequestMethod.POST)
	public ResponseEntity<?> pauseGeneration(HttpSession session)
	{
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		boolean paused = generator.pauseGeneration();
		if (!paused) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_STATUS_PATH, method=RequestMethod.GET)
	public ResponseEntity<GenerationResponse> getGenerationStatus(HttpSession session)
	{
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationResponse>(new GenerationResponse(GenerationStatus.ERROR), HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<GenerationResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		GenerationResponse response = generator.getGenerationState();
		return new ResponseEntity<GenerationResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_MAP_PATH, method=RequestMethod.GET)
	public ResponseEntity<byte[]> getGeneratedMap(HttpSession session, @PathVariable(CdgConstants.MAP_MAPTYPE_PATH_VARIABLE) MapType type) {
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!generator.getStatus().equals(GenerationStatus.COMPLETE)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		State generatedState = generator.getGeneratedState();
		String generatedMap;
		try {
			generatedMap = MapService.generateMap(generatedState, type);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (generatedMap == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		generatedState.setMapGeoJSON(generatedMap, type);
		byte[] mapFile = generatedState.getMapFile(type);

		return new ResponseEntity<byte[]>(mapFile, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_SAVE_MAP_PATH, method=RequestMethod.POST)
	public ResponseEntity<?> saveGeneratedMap(HttpSession session)
	{
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!generator.getStatus().equals(GenerationStatus.COMPLETE)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		SavedMap savedMap = generator.createSavedMap(stateRepo);
		if (savedMap == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		savedMapRepo.save(savedMap);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_MAP_DATA_PATH, method=RequestMethod.GET)
	public ResponseEntity<MapDataDTO> getGeneratedData(HttpSession session, @PathVariable(CdgConstants.MAP_MAPTYPE_PATH_VARIABLE) MapType type) 
	{
		User user = (User) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!generator.getStatus().equals(GenerationStatus.COMPLETE)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		State generatedState = generator.getGeneratedState();
		MapDataDTO data = generatedState.getMapData(type);
		if (data == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_SAVE_MAP_DATA_PATH, method=RequestMethod.POST)
	public void saveGeneratedData(@RequestBody MapDataDTO dataToSave) 
	{

	}
}
