package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CCAdminRepository;
import tim31.pswisa.repository.PatientRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class LoggingService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CCAdminRepository adminRepo;
	
	@Autowired
	private PatientRepository patientRepo;
	
	public Patient registerUser(Patient p)
	{
		List<User> users = userRepo.findAll() ;		
		
		for (User user : users) {
			if (p.getUser().getActivated() == true || p.getUser().getEmail().equals(user.getEmail()))		// already activated account or already sent request
				return null;			
		}
		
		
		p.getUser().setActivated(false);
		p.getUser().setType("PACIJENT");
		patientRepo.save(p);
		
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
					if (user.getType().equals("PACIJENT") && user.getActivated())
						return user;
					else if (!user.getFirstLogin() && !user.getType().equals("PACIJENT"))
						user.setFirstLogin(true);
						return user;
				}
			}
				
		}
		
		return null;
		
	}
}
