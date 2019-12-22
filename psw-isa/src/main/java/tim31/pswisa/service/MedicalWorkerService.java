package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.model.Authority;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalRecord;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.ClinicRepository;
import tim31.pswisa.repository.MedicalWorkerRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class MedicalWorkerService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private MedicalWorkerRepository medicalWorkerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private UserService userService;

	@Autowired
	private CheckUpTypeService checkUpTypeService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private ClinicRepository clinicRepository;

	@Autowired
	private CheckUpService checkupService;

	public Set<MedicalWorker> findAllByClinicId(Long id) {
		return medicalWorkerRepository.findAllByClinicId(id);
	}

	/**
	 * This method servers for updating medical worker
	 * 
	 * @param medWorker - medical worker who has to be changed
	 * @param mw        - new information about medical worker
	 * @return - (MedicalWorker) This method returns updated medicalWorker
	 */
	public MedicalWorker updateMedicalWorker(MedicalWorker medWorker, MedicalWorkerDTO mw) {
		medWorker.getUser().setName(mw.getUser().getName());
		medWorker.getUser().setSurname(mw.getUser().getSurname());
		medWorker.setPhone(mw.getPhone());
		medWorker = update(medWorker);
		return medWorker;
	}

	public MedicalWorker findByUser(Long id) {
		return medicalWorkerRepository.findOneByUserId(id);
	}

	/**
	 * This method servers for getting medical workers in clinic
	 * 
	 * @param clinic - clinic where medical workers work
	 * @return - (List<MedicalWorker>) This method returns list of medical workers
	 *         in clinic
	 */
	public List<MedicalWorkerDTO> getDoctors(Clinic clinic) {
		Set<MedicalWorker> temp = findAllByClinicId(clinic.getId());
		List<MedicalWorkerDTO> returnVal = new ArrayList<MedicalWorkerDTO>();

		for (MedicalWorker med : temp) {
			returnVal.add(new MedicalWorkerDTO(med));
		}
		return returnVal;
	}

	/**
	 * This method servers for deleting medical worker
	 * 
	 * @param email               - medical worker who has to be changed
	 * @param clinicAdministrator - logged clinic administrator
	 * @return - (String) This method returns string ok or ""
	 */
	public String deleteDoctor(String email, ClinicAdministrator clinicAdministrator) {
		Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
		User user = userService.findOneByEmail(email);
		MedicalWorker med = findByUser(user.getId());
		// if(med.getCheckUps().size() != 0) {
		clinic.getMedicalStuff().remove(med);
		clinicRepository.save(clinic);
		med.setClinic(null);
		medicalWorkerRepository.save(med);
		return "Obrisano";
		// }
		// else {
		// return "Greska";
		// }

	}

	/**
	 * This method servers for booking appointment by doctor
	 * 
	 * @param user - logged doctor
	 * @param c    - check-up of patient
	 * @return - (void) This method has no return value
	 */
	public void bookForPatient(User user, CheckupDTO c) throws MailException, InterruptedException {
		if (user != null) {
			MedicalWorker medWorker = findByUser(user.getId());
			Clinic clinic = medWorker.getClinic();
			Checkup checkup = new Checkup();
			User patient = userService.findOneByEmail(c.getPatient().getUser().getEmail());
			Patient p = patientService.findOneByUserId(patient.getId());
			checkup.setPatient(p);
			checkup.setScheduled(false);
			checkup.setType(c.getType());
			checkup.setMedicalWorker(medWorker);
			checkup.setTime(c.getTime());
			checkup.setDuration(1);
			checkup.setClinic(clinic);
			CheckUpType temp = checkUpTypeService.findOneByName(medWorker.getType());
			checkup.setCheckUpType(temp);
			List<Room> rooms = roomService.findAllByClinicId(clinic.getId());
			checkup.setPrice(temp.getTypePrice());
			checkup.setDate(c.getDate());
			checkup.setRoom(rooms.get(1));
			checkupService.save(checkup);
			emailService.sendNotificationToAmin(clinic, medWorker, p);
		}
	}

	/**
	 * This method servers for checking if doctor can access to medical record of
	 * patient
	 * 
	 * @param user - logged doctor
	 * @param pom  - email of patient
	 * @return - (String) This method returns string 'DA' or 'NE'
	 */
	public String canAccess(User user, String pom) {
		String retVal = "";
		User user1 = userService.findOneByEmail(pom);
		Patient p = patientService.findOneByUserId(user1.getId());
		MedicalWorker medWorker = findByUser(user.getId());
		for (Checkup c : p.getAppointments()) {
			if (c.getMedicalWorker().getUser().getEmail().equals((medWorker.getUser().getEmail()))) {
				retVal = "DA";
			}
		}
		if (retVal.equals("DA")) {
			return retVal;
		} else {
			return "NE";
		}
	}

	public List<MedicalWorkerDTO> findDoctors(Clinic clinic, String name, String typeD) {
		Set<MedicalWorker> temp = findAllByClinicId(clinic.getId());
		List<MedicalWorkerDTO> returnVal = new ArrayList<MedicalWorkerDTO>();

		if (name.equals("")) {
			for (MedicalWorker med : temp) {
				if (med.getType().equals(typeD)) {
					returnVal.add(new MedicalWorkerDTO(med));
					return returnVal;
				}
			}
		}

		for (MedicalWorker med : temp) {
			if (med.getUser().getName().equals(name) && med.getType().equals(typeD)) {
				returnVal.add(new MedicalWorkerDTO(med));
			}
		}
		return returnVal;
	}

	public MedicalWorker findOne(Long id) {
		return medicalWorkerRepository.findById(id).orElseGet(null);
	}

	public MedicalWorker update(MedicalWorker mw) {
		return medicalWorkerRepository.save(mw);
	}

	public MedicalWorker save(MedicalWorkerDTO mw) {
		User user = userRepository.findOneByEmail(mw.getUser().getEmail());
		if (user != null) {
			return null;
		}
		MedicalWorker medicalWorker = new MedicalWorker();
		user = new User();
		user.setName(mw.getUser().getName());
		user.setSurname(mw.getUser().getSurname());
		user.setEmail(mw.getUser().getEmail());
		user.setType(mw.getUser().getType());
		medicalWorker.setUser(user);
		Clinic clinic = clinicService.findOneById(mw.getClinic().getId());
		medicalWorker.setClinic(clinic);
		medicalWorker.setPhone(mw.getPhone());
		medicalWorker.setEndHr(mw.getEndHr());
		medicalWorker.setStartHr(mw.getStartHr());
		medicalWorker.getUser().setPassword(passwordEncoder.encode("sifra123"));
		medicalWorker.getUser().setFirstLogin(false);
		medicalWorker.getUser().setEnabled(true);
		medicalWorker.getUser().setActivated(true);
		if (user.getType().equals("MEDICINAR")) {
			medicalWorker.setType("");
		} else {
			medicalWorker.setType(mw.getType());
		}
		List<Authority> auth = authorityService.findByname(medicalWorker.getUser().getType());
		medicalWorker.getUser().setAuthorities(auth);

		return medicalWorkerRepository.save(medicalWorker);
	}

	public MedicalWorker findOneById(Long id) {
		return medicalWorkerRepository.findOneById(id);
	}

	public List<MedicalWorkerDTO> searchDoctors(String[] params) {
		List<MedicalWorkerDTO> forSearch = clinicService.doctorsInClinic(params[0], params[1], params[2]);
		String name = params[3].equals("") ? "" : params[3];
		String surname = params[4].equals("") ? "" : params[4];
		int rating = 0;
		List<MedicalWorkerDTO> ret = new ArrayList<MedicalWorkerDTO>();

		if (!params[5].equals("")) {
			rating = Integer.parseInt(params[5]);
		}

		for (MedicalWorkerDTO mw : forSearch) {
			if (checkParams(mw, name, surname, rating)) {
				ret.add(mw);
			}
		}

		return ret;

	}

	public boolean checkParams(MedicalWorkerDTO mw, String name, String surname, int rating) {

		if (!name.equals("") && !mw.getUser().getName().equals(name))
			return false;
		if (!surname.equals("") && !mw.getUser().getSurname().equals(surname))
			return false;
		if (rating != 0 && mw.getRating() != rating)
			return false;

		return true;
	}
}