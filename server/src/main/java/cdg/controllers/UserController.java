package cdg.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	String SESSION = "usersession";
	String USERNAME = "username";
	String PASSWORD = "password";
	CdgResponseBuilder responseBuilder = new CdgResponseBuilder();
	
	@RequestMapping( value = "/login", method=RequestMethod.POST)
	public @ResponseBody CdgResponse<String>  login(@RequestBody Credentials creds, HttpSession session)
	{
		String loggedInUser = (String) session.getAttribute(SESSION);
		if(loggedInUser != null) {
			return responseBuilder.errorMessage("Already Logged In");
		}
		if(creds.compare(PASSWORD)) {
			session.setAttribute(SESSION, USERNAME);
			return responseBuilder.successMessage("Logged In");
		}
		else {
			return responseBuilder.errorMessage("Incorrect Password");
		}
	}
	
	
	@RequestMapping( value = "/logout", method=RequestMethod.GET)
	public CdgResponse<String>  logout(HttpSession session)
	{	
		String user = (String) session.getAttribute(SESSION);
		if(user == null) {
			return responseBuilder.errorMessage("Not Logged In");
		}
		session.removeAttribute(SESSION);
		return responseBuilder.successMessage("Logged Out");
	}
}

