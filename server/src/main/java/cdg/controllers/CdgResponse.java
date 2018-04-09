package cdg.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CdgResponse<String> extends ResponseEntity<String> {
	
	private String err;
	private String status;
	public CdgResponse(HttpStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}
	
	public CdgResponse(String message, HttpStatus status) {
		super(message, status);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setError(String error) {
		this.err = error;
	}
	
	

}
