package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.ClinicAdministratorRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class ClinicAdministratorService {

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;

	@Autowired
	private UserRepository userRepository;

	public ClinicAdministrator findByUser(Long id) {
		return clinicAdministratorRepository.findOneByUserId(id);
	}

	public ClinicAdministrator update(ClinicAdministrator ca) {
		return clinicAdministratorRepository.save(ca);
	}
	
	public ClinicAdministrator updateAdministrator(ClinicAdministrator clinicAdministrator, ClinicAdministrator ca) {
		clinicAdministrator.getUser().setName(ca.getUser().getName());
		clinicAdministrator.getUser().setSurname(ca.getUser().getSurname());
		clinicAdministrator.getUser().setPassword(ca.getUser().getPassword());
		clinicAdministrator = update(clinicAdministrator);
		return clinicAdministrator;
	}

	public ClinicAdministrator findOneById(Long id) {
		return clinicAdministratorRepository.findOneById(id);
	}

	public ClinicAdministrator save(ClinicAdministrator admin) {
		List<User> users = userRepository.findAll();
		for (User u : users) {
			if (u.getEmail().equals(admin.getUser().getEmail())) {
				return null;
			}
		}
		return clinicAdministratorRepository.save(admin);
	}
}
