package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Authority;
import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CCAdminRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class CCAdminService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CCAdminRepository ccadminRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityService authorityService;

	public ClinicalCenterAdministrator save(User u) {
		ClinicalCenterAdministrator admin = new ClinicalCenterAdministrator();
		admin.setUser(u);
		admin.getUser().setPassword(passwordEncoder.encode("admin"));
		admin.getUser().setFirstLogin(false);
		admin.getUser().setEnabled(true);
		admin.getUser().setActivated(true);
		List<Authority> auth = authorityService.findByname("CCADMIN");
		admin.getUser().setAuthorities(auth);

		User user = userRepository.findOneByEmail(u.getEmail());
		if (user != null) {
			return null;
		}

		return ccadminRepository.save(admin);
	}

}