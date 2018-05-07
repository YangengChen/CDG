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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(CdgConstants.USER_CONTROLLER_PATH_PREFIX)
@CrossOrigin(origins = CdgConstants.CROSS_ORIGIN_LOCATION)
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value=CdgConstants.USER_LOGIN_PATH, method=RequestMethod.POST)
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO user, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		session = request.getSession();
		User loggedUser = userService.login(new User(user));
		if(session.getAttribute(CdgConstants.SESSION_USER) == null && loggedUser != null) {
			loggedUser.setGenerator(new MapGenerator());
			session.setAttribute(CdgConstants.SESSION_USER, loggedUser);
			return new ResponseEntity<>(loggedUser.getDTO(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=CdgConstants.USER_REGISTER_PATH, method=RequestMethod.POST)
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO user, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		session = request.getSession();
		User registeredUser = userService.register(new User(user));
		if(registeredUser != null) {
			registeredUser.setGenerator(new MapGenerator());
			session.setAttribute(CdgConstants.SESSION_USER, registeredUser);
			return new ResponseEntity<>(registeredUser.getDTO(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@RequestMapping(value=CdgConstants.USER_LOGOUT_PATH, method=RequestMethod.POST)
	public ResponseEntity<UserDTO> logout(HttpSession session) {
		User sessionUser = (User) session.getAttribute(CdgConstants.SESSION_USER);
		if(sessionUser != null) {
			session.removeAttribute(CdgConstants.SESSION_USER);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}

	@RequestMapping(path=CdgConstants.USER_ALL_USERS_PATH)
	public Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}	

	@RequestMapping(value="/delete_user", method=RequestMethod.POST) 
	public ResponseEntity<User> delete_user(@RequestBody User user,HttpSession session) {
		userService.deleteUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/edit_user", method=RequestMethod.POST) 
	public ResponseEntity<User> edit_user(@RequestBody User user,HttpSession session) {
		userService.editUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(path="/get_user", method=RequestMethod.GET)
	public ResponseEntity<Boolean> get_user(HttpSession session) {
		User user = (User) session.getAttribute(CdgConstants.SESSION_USER);
		if(user != null) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}	
	
}

