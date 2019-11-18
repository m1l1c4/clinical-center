package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CCAdminRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class LoggingService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CCAdminRepository adminRepo;
	
	public Patient registerUser(Patient p)
	{
		List<User> users = userRepo.findAll() ;
		
		for (User user : users) {
			if (user.getEmail().equals(p.getUser().getEmail()))
				return null;
			
		}
		
		p.getUser().setType("PACIJENT");
		List<ClinicalCenterAdministrator> admins = adminRepo.findAll() ;
		
		for (ClinicalCenterAdministrator ad : admins) {
			ad.getRequests().add(p);
		}
		
		return p;
		
	}
	
	public User loginUser(User u)
	{
		List<User> users = userRepo.findAll() ;
		
		for (User user : users) {
			if (user.getEmail().equals(u.getEmail()))
			{
				if (user.getPassword().equals(u.getPassword()))
				{
					if (user.getType().equals("PACIEJNT") && user.getActivated())
						return user;
						
				}
			}
				
		}
		
		return null;
		
	}
}
