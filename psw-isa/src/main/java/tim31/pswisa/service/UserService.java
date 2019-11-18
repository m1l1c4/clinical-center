package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.repository.UserRepository;
import tim31.pswisa.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findOneByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
}
