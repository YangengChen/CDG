package cdg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdg.dao.User;
import cdg.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User register(User user) {
		if(userRepository.findOne(user.getEmail()) == null) {
			user.encryptPassword();
			userRepository.save(user);
			return user;
		} else {			
			return null;
		}
	}
	
	public User login(User user) {
		User registeredUser = userRepository.findOne(user.getEmail());
		if(registeredUser != null && registeredUser.validatePassword(user.getPassword())) {
			return registeredUser;
		} else {
			return null;
		}
	}
	
}
