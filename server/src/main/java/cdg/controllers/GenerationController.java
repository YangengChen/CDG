package cdg.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdg.domain.generation.GenerationConfiguration;
import cdg.domain.generation.GenerationStatus;
import cdg.dto.MapDTO;
import cdg.dto.MapDataDTO;

@RestController
@RequestMapping("/api/generation")
@CrossOrigin(origins = "http://localhost:4200")
public class GenerationController {

	@RequestMapping( value = "/start", method=RequestMethod.POST)
	public void startGeneration(@RequestBody GenerationConfiguration config)
	{
		
	}
	
	@RequestMapping( value = "/cancel", method=RequestMethod.POST)
	public void cancelGeneration()
	{
		
	}
	
	@RequestMapping( value = "/status", method=RequestMethod.GET)
	public GenerationStatus getGenerationStatus()
	{
		return null;
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
