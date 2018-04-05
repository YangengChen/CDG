package cdg.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MapController {
	
	@RequestMapping( value = "/map", method=RequestMethod.GET)
	public ResponseEntity<String> getMap() {
		return new ResponseEntity<String>("Testing The Mapperino\n", HttpStatus.OK);
	}
}
