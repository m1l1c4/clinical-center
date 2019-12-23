package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.AbsenceDTO;
import tim31.pswisa.dto.ClinicAdministratorDTO;
import tim31.pswisa.dto.UserDTO;
import tim31.pswisa.model.Absence;
import tim31.pswisa.model.Authority;
import tim31.pswisa.model.Clinic;
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

	@Autowired
	private EmailService emailService;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private AbsenceService absenceService;

	/**
	 * This method servers for finding administrator by user id
	 * 
	 * @param id - id of user
	 * @return - (ClinicAdministrator>) This method returns clinic administrator
	 */
	public ClinicAdministrator findByUser(Long id) {
		return clinicAdministratorRepository.findOneByUserId(id);
	}

	/**
	 * This method servers for updating administrator
	 * 
	 * @param ca - new information about clinic administrator
	 * @return - (ClinicAdministrator>) This method returns clinic administrator
	 */
	public ClinicAdministrator update(ClinicAdministrator ca) {
		return clinicAdministratorRepository.save(ca);
	}

	/**
	 * This method servers for sending email to medical worker after accepting or
	 * refusing request
	 * 
	 * @param user   - user / medical worker who has to be informed
	 * @param a      - absence of medical worker
	 * @param reason - reason of refusing request or "ok" if request is accepted
	 * @return - (void) This method doesn't have return value
	 */
	public void sendEmailToMw(User user, AbsenceDTO a, String reason) throws MailException, InterruptedException {
		emailService.sendReasonToMw(user, a, reason);
	}

	/**
	 * This method servers for updating clinic administrator
	 * 
	 * @param clinicAdministrator - clinic administrator that have to be updated
	 * @param ca                  - new data of clinic administrator
	 * @return - updated clinic administrator
	 */
	public ClinicAdministrator updateAdministrator(ClinicAdministrator clinicAdministrator, ClinicAdministratorDTO ca) {
		clinicAdministrator.getUser().setName(ca.getUser().getName());
		clinicAdministrator.getUser().setSurname(ca.getUser().getSurname());
		clinicAdministrator = update(clinicAdministrator);
		return clinicAdministrator;
	}

	/**
	 * This method servers for getting requests for vacation
	 * 
	 * @param user - logged clinic administrator
	 * @return - (List<AbsenceDTO>) This method returns list of request for vacation
	 */
	public List<AbsenceDTO> getRequestForVacation(User user) {
		ClinicAdministrator clinicAdministrator = findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		List<Absence> returnValue = new ArrayList<Absence>();
		List<AbsenceDTO> returnValue1 = new ArrayList<AbsenceDTO>();
		returnValue = absenceService.findAllByClinicOfAbsenceId(clinic.getId());
		for (Absence a : returnValue) {
			if (a.getAccepted().equals("SENT")) {
				returnValue1.add(new AbsenceDTO(a));
			}
		}
		if (returnValue1.size() == 0) {
			return null;
		} else {
			return returnValue1;
		}
	}

	/**
	 * This method servers for finding clinic administrator by id
	 * 
	 * @param id - id of clinic administrator that has to be found
	 * @return - (ClinicAdministrator) This method returns found clinic
	 *         administrator
	 */
	public ClinicAdministrator findOneById(Long id) {
		return clinicAdministratorRepository.findOneById(id);
	}

	public ClinicAdministrator save(ClinicAdministratorDTO ca) {
		User user = userRepository.findOneByEmail(ca.getUser().getEmail());
		if (user != null) {
			return null;
		}
		ClinicAdministrator admin = new ClinicAdministrator();
		user = new User();
		UserDTO u = ca.getUser();
		user.setName(u.getName());
		user.setSurname(u.getSurname());
		user.setEmail(u.getEmail());
		user.setType(u.getType());
		admin.setUser(user);
		admin.getUser().setPassword(passwordEncoder.encode("admin"));
		admin.getUser().setFirstLogin(false);
		admin.getUser().setEnabled(true);
		admin.getUser().setActivated(true);
		Clinic clinic = clinicService.findOneById(ca.getClinic().getId());
		admin.setClinic(clinic);
		List<Authority> auth = authorityService.findByname("ADMINISTRATOR");
		admin.getUser().setAuthorities(auth);

		return clinicAdministratorRepository.save(admin);
	}
	
	public List<ClinicAdministrator> findAll() {
		return clinicAdministratorRepository.findAll();
	}
}