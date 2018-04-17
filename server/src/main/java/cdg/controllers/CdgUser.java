package cdg.controllers;

import cdg.domain.generation.MapGenerator;

public class CdgUser {
	private String email;
	private String password;
	private final MapGenerator GENERATOR;
	
	CdgUser(String email, String password){
		this.email = email;
		this.password = password;
		GENERATOR = new MapGenerator();
	}
	
	public String getPassword() {
		return this.password;
	}
	public String getEmail() {
		return this.email;
	}

	public MapGenerator getGenerator() {
		return GENERATOR;
	}
}
