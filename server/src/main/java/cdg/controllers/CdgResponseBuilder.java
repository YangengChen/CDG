package cdg.controllers;

import org.springframework.http.HttpStatus;

public class CdgResponseBuilder {

	public CdgResponse<String> successMessage(String successMessage){
		CdgResponse<String> response = new CdgResponse<String>(successMessage, HttpStatus.OK);
		return response;
	}
	
	public CdgResponse<String> errorMessage(String errorMessage){
		CdgResponse<String> response = new CdgResponse<String> (errorMessage, HttpStatus.OK);
		return response;
	}
}
