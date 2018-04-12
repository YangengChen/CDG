package cdg.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cdg.responses.CdgResponseBuilder;
import cdg.responses.UserResponse;

import java.util.Properties;
import javax.servlet.http.HttpSession;

import static cdg.properties.CdgConstants.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	Properties prop = new Properties();
	
	@RequestMapping( value = "/login", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<UserResponse>  login(@RequestBody Credentials credentials, HttpSession session)
	{
		CdgUser loggedInUser = (CdgUser) session.getAttribute(SESSION_USER);
		if(loggedInUser != null) {
			return CdgResponseBuilder.generateErrorResponse("Already Logged In");
		}
		//Replace with repo call
		CdgUser user = new CdgUser("USERNAME", credentials.getPassword());
//		if(user == null) {
//			return responseBuilder.errorMessage("Account Doesn't Exist");
//		}
		if(credentials.compare(user.getPassword())) {
			session.setAttribute(SESSION_USER, user);
			return  CdgResponseBuilder.generateSuccessResponse(new UserResponse(user));
		}
		else {
			return CdgResponseBuilder.generateErrorResponse("Incorrect Password");
		}
	}
	
	
	@RequestMapping( value = "/logout", method=RequestMethod.GET)
	public ResponseEntity<UserResponse> logout(HttpSession session)
	{	
		CdgUser user = (CdgUser) session.getAttribute(SESSION_USER);
		if(user == null) {
			return CdgResponseBuilder.generateErrorResponse("Not Logged In");
		}
		session.removeAttribute(SESSION_USER);
		return CdgResponseBuilder.generateSuccessResponse(new UserResponse(user));
	}
}

