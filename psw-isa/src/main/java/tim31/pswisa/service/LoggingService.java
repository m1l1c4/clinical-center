package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CCAdminRepository;
import tim31.pswisa.repository.PatientRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class LoggingService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CCAdminRepository adminRepo;
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
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
		
		User user = (User) loadUserByUsername(u.getEmail());
		
		if (user!= null && user.getType().equals("PACIJENT") && user.getActivated())
			return user;
		else if (!user.getFirstLogin() && !user.getType().equals("PACIJENT"))
			{
				user.setFirstLogin(true);
				return user;
			}
			
		else		
			return null;	
		
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = userRepo.findOneByEmail(email);
		if (user == null) {			
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));			
		} 
		return user;
		
	}
}
