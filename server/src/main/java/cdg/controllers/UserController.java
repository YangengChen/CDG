package cdg.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdg.dao.User;
import cdg.repository.UserRepository;

import cdg.responses.CdgResponseBuilder;
import cdg.responses.UserResponse;

import java.util.Properties;
import javax.servlet.http.HttpSession;


import static cdg.properties.CdgConstants.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
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
		if(credentials.compare(user.getPassword())) {
			session.setAttribute(SESSION_USER, user);
			return  CdgResponseBuilder.generateSuccessResponse(new UserResponse(user));
		}
		else {
			return CdgResponseBuilder.generateErrorResponse("Incorrect Password");
		}
	}
	
	@RequestMapping(path="/register") // Map ONLY GET Requests
	public ResponseEntity<UserResponse> register(@RequestBody User user,HttpSession session) {
		user.setPassword(user.encryptPassword(user.getPassword()));
		userRepository.save(user);
		return CdgResponseBuilder.generateErrorResponse("User Registered");
	} 
	
	@RequestMapping(value= "/logout", method=RequestMethod.GET)
	public ResponseEntity<UserResponse> logout(HttpSession session)
	{	
		CdgUser user = (CdgUser) session.getAttribute(SESSION_USER);
		if(user == null) {
			return CdgResponseBuilder.generateErrorResponse("Not Logged In");
		}
		session.removeAttribute(SESSION_USER);
		return CdgResponseBuilder.generateSuccessResponse(new UserResponse(user));
	}
	
	@RequestMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}	
	
}

