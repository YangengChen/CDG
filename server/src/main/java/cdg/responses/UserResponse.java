package cdg.responses;


import cdg.controllers.CdgUser;

public class UserResponse {
	private String email;
	public UserResponse(CdgUser user) {
		this.email = user.getEmail();
	}
	
	public String getEmail() {
		return this.email;
				
	}

}
