package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.ClinicAdministratorDTO;
import tim31.pswisa.model.Authority;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.ClinicAdministratorRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class ClinicAdministratorService {

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityService authorityService;

	public ClinicAdministrator findByUser(Long id) {
		return clinicAdministratorRepository.findOneByUserId(id);
	}

	public ClinicAdministrator update(ClinicAdministrator ca) {
		return clinicAdministratorRepository.save(ca);
	}
	
	public ClinicAdministrator updateAdministrator(ClinicAdministrator clinicAdministrator, ClinicAdministratorDTO ca) {
		clinicAdministrator.getUser().setName(ca.getUser().getName());
		clinicAdministrator.getUser().setSurname(ca.getUser().getSurname());
		clinicAdministrator.getUser().setPassword(ca.getUser().getPassword());
		clinicAdministrator = update(clinicAdministrator);
		return clinicAdministrator;
	}

	public ClinicAdministrator findOneById(Long id) {
		return clinicAdministratorRepository.findOneById(id);
	}

	public ClinicAdministrator save(User u) {
		ClinicAdministrator admin = new ClinicAdministrator();
		admin.setUser(u);
		admin.getUser().setPassword(passwordEncoder.encode("admin"));
		admin.getUser().setFirstLogin(false);
		admin.getUser().setEnabled(true);
		admin.getUser().setActivated(true);
		List<Authority> auth = authorityService.findByname("ADMINISTRATOR");
		admin.getUser().setAuthorities(auth);

		User user = userRepository.findOneByEmail(u.getEmail());
		if (user != null) {
			return null;
		}

		return clinicAdministratorRepository.save(admin);
	}
}
