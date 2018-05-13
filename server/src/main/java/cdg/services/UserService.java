package cdg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import cdg.dao.User;
import cdg.dto.UserDTO;
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
	
	public User editUser(UserDTO user) {
		User savedUser = userRepository.findOneByEmail(user.getEmail());
		if(!user.getPassword().isEmpty()) {
			String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			savedUser.setPassword(password);
		}
		if(!user.getFirstName().isEmpty()) {
			System.out.println(user.getFirstName());
			savedUser.setFirstName(user.getFirstName());
		}
		if(!user.getLastName().isEmpty()) {
			savedUser.setLastName(user.getLastName());
		}
		userRepository.save(savedUser);
		return savedUser;
	}
	
}
