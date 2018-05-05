package cdg.services;

import java.util.List;

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
		if(userRepository.findOneByEmail(user.getEmail()) == null) {
			user.encryptPassword();
			user = userRepository.save(user);
			return user;
		} else {			
			return null;
		}
	}
	
	public User login(User user) {
		User registeredUser = userRepository.findOneByEmail(user.getEmail());
		if(registeredUser != null && registeredUser.validatePassword(user.getPassword())) {
			return registeredUser;
		} else {
			return null;
		}
	}

	public User deleteUser(User user) {
		User deleteUser = userRepository.findOneByEmail(user.getEmail());
		userRepository.delete(deleteUser);
		return deleteUser;
	}
	
	public User editUser(User user) {
		User oldUser = userRepository.findOneByEmail(user.getEmail());
		user.setId(oldUser.getId());
		String password = (user.getPassword().isEmpty()) ? oldUser.getPassword() : user.encryptPassword();
		user.setPassword(password);
		userRepository.save(user);
		return user;
	}
	
	
}
