package cdg.controllers;

import static cdg.properties.CdgConstants.SESSION_USER;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdg.dao.State;
import cdg.dao.User;
import cdg.domain.generation.CdgConstraintEvaluator;
import cdg.domain.generation.CdgGoodnessEvaluator;
import cdg.domain.generation.ConstraintEvaluator;
import cdg.domain.generation.GenerationConfiguration;
import cdg.domain.generation.GenerationStatus;
import cdg.domain.generation.GoodnessEvaluator;
import cdg.domain.generation.MapGenerator;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;
import cdg.properties.CdgConstants;
import cdg.repository.FakeData;
import cdg.responses.GenerationResponse;

@RestController
@RequestMapping(CdgConstants.GENERATION_CONTROLLER_PATH_PREFIX)
@CrossOrigin(origins = CdgConstants.CROSS_ORIGIN_LOCATION)
public class GenerationController {
	@Autowired
	private FakeData stateRepo;

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
		State state = stateRepo.findByPublicId(config.getStateId(), State.class);
		if (state == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.NOT_FOUND);
		}
		
		MapGenerator generator = user.getGenerator();
		if (generator == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ConstraintEvaluator constraintEval;
		GoodnessEvaluator goodnessEval;
		try {
			constraintEval = CdgConstraintEvaluator.toConstraintEvaluator(config);
			goodnessEval = CdgGoodnessEvaluator.toGoodnessEvaluator(CdgConstants.MAX_GOODNESS, config);
		} catch (IllegalArgumentException iae) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.BAD_REQUEST);
		}
		generator.resetGenerator(config.getStateId(), goodnessEval, constraintEval);
		
		try {
			generator.startGeneration(state);
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
	public MapDTO getGeneratedMap() {
		return null;
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_SAVE_MAP_PATH, method=RequestMethod.POST)
	public void saveGeneratedMap(@RequestBody MapDTO mapToSave)
	{
		
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_MAP_DATA_PATH, method=RequestMethod.GET)
	public MapDataDTO getGeneratedData() 
	{
		return null;
	}
	
	@RequestMapping( value = CdgConstants.GENERATION_SAVE_MAP_DATA_PATH, method=RequestMethod.POST)
	public void saveGeneratedData(@RequestBody MapDataDTO dataToSave) 
	{

	}
}
