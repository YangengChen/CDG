package cdg.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CdgResponseBuilder {
			
	static public <T> ResponseEntity<T> generateSuccessResponse(T body) {
		ResponseEntity<T> response = new ResponseEntity<T>(body, HttpStatus.OK);
		return  response;

	}
	
	static public <T> ResponseEntity<T> generateErrorResponse(String error){
		ErrorResponse body = new ErrorResponse(error);
		ResponseEntity<ErrorResponse> response =  new ResponseEntity<ErrorResponse>(body, HttpStatus.OK);
		return (ResponseEntity<T>) response;
	}
			
}
