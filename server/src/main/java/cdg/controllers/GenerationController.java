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
import cdg.domain.generation.CdgConstraintEvaluator;
import cdg.domain.generation.CdgGoodnessEvaluator;
import cdg.domain.generation.ConstraintEvaluator;
import cdg.domain.generation.GenerationConfiguration;
import cdg.domain.generation.GenerationStatus;
import cdg.domain.generation.GoodnessEvaluator;
import cdg.domain.generation.MapGenerator;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;
import cdg.repository.FakeData;
import cdg.responses.GenerationResponse;

@RestController
@RequestMapping("/api/generation")
@CrossOrigin(origins = "http://localhost:4200")
public class GenerationController {
	//fake
	public static double MAX_GOODNESS = 100;
	@Autowired
	private FakeData stateRepo;

	@RequestMapping( value = "/start", method=RequestMethod.POST)
	public ResponseEntity<GenerationStatus> startGeneration(@RequestBody GenerationConfiguration config, HttpSession session)
	{
		if (config == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.BAD_REQUEST);
		}
		CdgUser user = (CdgUser) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.UNAUTHORIZED);
		}
		State state = stateRepo.findByPublicId(config.getStateId(), State.class);
		if (state == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.NOT_FOUND);
		}
		
		MapGenerator generator = user.getGenerator();
		ConstraintEvaluator constraintEval;
		GoodnessEvaluator goodnessEval;
		try {
			constraintEval = CdgConstraintEvaluator.toConstraintEvaluator(config);
			goodnessEval = CdgGoodnessEvaluator.toGoodnessEvaluator(MAX_GOODNESS, config);
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
	
	@RequestMapping( value = "/cancel", method=RequestMethod.POST)
	public ResponseEntity<GenerationStatus> cancelGeneration(HttpSession session)
	{
		CdgUser user = (CdgUser) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationStatus>(GenerationStatus.ERROR, HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		generator.stopGeneration();
		
		return new ResponseEntity<GenerationStatus>(GenerationStatus.CANCELED, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/status", method=RequestMethod.GET)
	public ResponseEntity<GenerationResponse> getGenerationStatus(HttpSession session)
	{
		CdgUser user = (CdgUser) session.getAttribute(SESSION_USER);
		if (user == null) {
			return new ResponseEntity<GenerationResponse>(new GenerationResponse(GenerationStatus.ERROR), HttpStatus.UNAUTHORIZED);
		}
		
		MapGenerator generator = user.getGenerator();
		GenerationResponse response = generator.getGenerationState();
		return new ResponseEntity<GenerationResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping( value = "/map", method=RequestMethod.GET)
	public MapDTO getGeneratedMap() {
		return null;
	}
	
	@RequestMapping( value = "/save/map", method=RequestMethod.POST)
	public void saveGeneratedMap(@RequestBody MapDTO mapToSave)
	{
		
	}
	
	@RequestMapping( value = "/data", method=RequestMethod.GET)
	public MapDataDTO getGeneratedData() 
	{
		return null;
	}
	
	@RequestMapping( value = "/save/data", method=RequestMethod.POST)
	public void saveGeneratedData(@RequestBody MapDataDTO dataToSave) 
	{

	}
}
