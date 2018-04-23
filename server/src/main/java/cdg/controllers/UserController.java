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
import cdg.domain.generation.MapGenerator;
import cdg.dto.UserDTO;
import cdg.properties.CdgConstants;
import cdg.repository.UserRepository;
import cdg.services.UserService;


import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
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
	public @ResponseBody ResponseEntity<UserDTO> login(@RequestBody UserDTO user, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		session = request.getSession();
		System.out.println("login session: " + session.toString());
		User loggedUser = userService.login(new User(user));
		if(session.getAttribute(CdgConstants.SESSION_USER) == null && loggedUser != null) {
			loggedUser.setGenerator(new MapGenerator());
			session.setAttribute(CdgConstants.SESSION_USER, loggedUser);
			return new ResponseEntity<>(loggedUser.getDTO(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST) // Map ONLY GET Requests
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO user, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		session = request.getSession();
		User registeredUser = userService.register(new User(user));
		if(registeredUser != null) {
			registeredUser.setGenerator(new MapGenerator());
			session.setAttribute(CdgConstants.SESSION_USER, registeredUser);
			System.out.println("register session: " + session.toString());
			return new ResponseEntity<>(registeredUser.getDTO(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST) // Map ONLY GET Requests
	public @ResponseBody ResponseEntity<UserDTO> logout(HttpSession session) {
		User sessionUser = (User) session.getAttribute(CdgConstants.SESSION_USER);
		System.out.println("logout session: " + session.toString());
		if(sessionUser != null) {
			System.out.println("Success");
			session.removeAttribute(CdgConstants.SESSION_USER);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			System.out.println("Error");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}

	@RequestMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}	
	
}

