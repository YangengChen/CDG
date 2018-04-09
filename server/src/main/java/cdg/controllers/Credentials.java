package cdg.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class Credentials {
	private String email;
	private String password;
	
	public String getUsername() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean compare(String toCompare) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(toCompare, encoder.encode(this.password));
	}
}