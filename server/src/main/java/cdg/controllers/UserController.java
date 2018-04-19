package cdg.controllers;

import org.springframework.http.HttpStatus;
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
import cdg.properties.CdgConstants;
import cdg.repository.UserRepository;
import cdg.services.UserService;

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
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping( value = "/login", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<User>  login(@RequestBody User user, HttpSession session) {
		User loggedUser = userService.login(user);
		if(session.getAttribute(CdgConstants.SESSION_USER) == null && loggedUser != null) {
			session.setAttribute(CdgConstants.SESSION_USER, loggedUser);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			System.out.println("Here");
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(path="/register", method=RequestMethod.POST) // Map ONLY GET Requests
	public ResponseEntity<User> register(@RequestBody User user,HttpSession session) {
		User registeredUser = userService.register(user);
		if(registeredUser != null) {
			session.setAttribute(CdgConstants.SESSION_USER, registeredUser);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@RequestMapping(path="/logout", method=RequestMethod.POST) // Map ONLY GET Requests
	public @ResponseBody ResponseEntity<User> logout(HttpSession session) {
		User sessionUser = (User) session.getAttribute(CdgConstants.SESSION_USER);
		if(sessionUser != null) {
			session.removeAttribute(CdgConstants.SESSION_USER);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@RequestMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}	
	
}

