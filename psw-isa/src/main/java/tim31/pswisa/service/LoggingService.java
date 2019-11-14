package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tim31.pswisa.model.User;
import tim31.pswisa.repository.UserRepository;

public class LoggingService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User registerUser(User u)
	{
		List<User> users = userRepo.findAll() ;
		
		for (User user : users) {
			if (user.getEmail().equals(u.getEmail()))
				return null;
		}
				
		return u;
		
	}
}
