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

import cdg.dao.User;
import cdg.properties.CdgConstants;
import cdg.repository.UserRepository;
import cdg.services.UserService;


import java.util.Properties;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	Properties prop = new Properties();
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> login(@RequestBody User user, HttpSession session) {
		System.out.println("login session: " + session.toString());
		User loggedUser = userService.login(user);
		if(session.getAttribute(CdgConstants.SESSION_USER) == null && loggedUser != null) {
			session.setAttribute(CdgConstants.SESSION_USER, loggedUser);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST) // Map ONLY GET Requests
	public ResponseEntity<User> register(@RequestBody User user,HttpSession session) {
		User registeredUser = userService.register(user);
		if(registeredUser != null) {
			session.setAttribute(CdgConstants.SESSION_USER, registeredUser);
			System.out.println("register session: " + session.toString());
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST) // Map ONLY GET Requests
	public @ResponseBody ResponseEntity<User> logout(HttpSession session) {
		User sessionUser = (User) session.getAttribute(CdgConstants.SESSION_USER);
		System.out.println("logout session: " + session.toString());
		if(sessionUser != null) {
			System.out.println("Success");
			session.removeAttribute(CdgConstants.SESSION_USER);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			System.out.println("Error");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}

	@RequestMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}	
	
}

