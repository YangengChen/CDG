package cdg.controllers;

public class CdgUser {
	private String email;
	private String password;
	
	CdgUser(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	public String getEmail() {
		return this.email;
	}
}
