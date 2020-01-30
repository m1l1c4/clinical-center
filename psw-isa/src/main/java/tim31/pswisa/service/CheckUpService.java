package tim31.pswisa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.Absence;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CheckUpRepository;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.Room;

@Service
@Transactional(readOnly = true)
public class CheckUpService {

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	CheckUpTypeService checkUpTypeService;

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private UserService userService;

	@Autowired
	private ClinicAdministratorService cladminService;

	@Autowired
	private CheckUpRepository checkupRepository;

	@Autowired
	private EmailService emailService;

	/**
	 * This method servers for getting all check-up from database
	 * 
	 * @return - (Checkup) This method returns all absence from database
	 */
	public List<Checkup> findAll() {
		return checkupRepository.findAll();
	}

	/**
	 * This method servers for getting all check-up from database in one clinic
	 * 
	 * @param id - id of check-up
	 * @return - (List<Checkup>) This method returns all check-ups from database in
	 *         one clinic
	 */
	public List<Checkup> findAllByClinicId(Long id) {
		return checkupRepository.findAllByClinicId(id);
	}

	/**
	 * This method servers for saving check-up in database
	 * 
	 * @param c - check-up that have to be saved
	 * @return - (Checkup) This method returns saved check-up
	 */
	public Checkup save(Checkup c) {
		return checkupRepository.save(c);
	}

	/**
	 * This method servers for getting one check-up by id from database
	 * 
	 * @param id - id of check-up
	 * @return - (Checkup) This method returns all absence from database
	 */
	public Checkup findOneById(Long id) {
		return checkupRepository.findOneById(id);
	}

	/**
	 * This method servers for finding all check-ups that are not scheduled
	 * 
	 * @param ok   - scheduled or not scheduled
	 * @param user - logged user / administrator
	 * @return - (List<CheckupDTO) list of check-ups scheduled appointments
	 */
	public List<CheckupDTO> findAllByScheduled(boolean ok, User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		List<Checkup> temp = checkupRepository.findAll();
		List<CheckupDTO> retVal = new ArrayList<CheckupDTO>();
		for (Checkup c : temp) {
			if (!c.isScheduled() && c.getClinic().getId() == clinic.getId()) {
				retVal.add(new CheckupDTO(c));
			}
		}
		return retVal;
	}

	/**
	 * This method servers for adding new appointment for booking with one click
	 * 
	 * @param c                   - check-up to add for booking with one click
	 * @param mw                  - doctor on examination
	 * @param clinicAdministrator - clinic administrator who adding new appointment
	 * @return - (Checkup) added check-up or null if doctor is busy at that moment
	 */
	public Checkup addAppointment(CheckupDTO c, MedicalWorker mw, ClinicAdministrator clinicAdministrator) {
		Checkup checkup = new Checkup();
		int ok = 0;
		int ok1 = 0;
		for (Checkup cek : mw.getCheckUps()) {
			if (cek.getDate().equals(c.getDate()) || cek.getTime().equals(c.getTime())) {
				ok = 1;
			}
		}
		for (Absence a : mw.getHollydays()) {
			LocalDate d = c.getDate();
			if ((a.getStartVacation().isBefore(d) || a.getStartVacation().isEqual(d))
					&& (a.getEndVacation().isAfter(d) || a.getEndVacation().isEqual(d))) {
				ok = 1;
			}
		}
		if (ok == 0) {
			checkup.getDoctors().add(mw);
			CheckUpType typeC = checkUpTypeService.findOneByName(c.getCheckUpType().getName());
			checkup.setCheckUpType(typeC);
			double temp = typeC.getTypePrice();
			if (c.getPrice() == 0) {
				checkup.setPrice(typeC.getTypePrice());
			} else {
				temp = temp - c.getPrice() / 100 * temp;
				checkup.setPrice(temp);
			}
			checkup.setDate(c.getDate());
			checkup.setTime(c.getTime());
			checkup.setTip(c.getType());
			checkup.setDuration(1);
			checkup.setDiscount(0);
			Room room = new Room();
			Set<Room> rooms = new HashSet<Room>();
			Clinic clinic = new Clinic();
			clinic = clinicAdministrator.getClinic();
			if (clinic != null) {
				checkup.setClinic(clinic);
				rooms = clinic.getRooms();
				Set<Checkup> checkups = new HashSet<Checkup>();
				if (room.getBookedCheckups() != null) {
					checkups = room.getBookedCheckups();
					for (Checkup pom : checkups) { // same room and same time of appointment
						if (c.getDate().equals(pom.getDate()) && c.getTime().equals(pom.getTime())) {
							ok1 = 1;
						}
					}
				}
				for (Room r : rooms) {
					if (r.getNumber() == c.getRoom().getNumber()) {
						checkup.setRoom(roomService.findOneById(r.getId()));
						checkup.getRoom().getBookedCheckups().add(checkup);
						Room pom = roomService.findOneById(r.getId());
						System.out.println(pom.getName());
						checkup.getRoom().setName(pom.getName());
					}
				}
				room.getBookedCheckups().add(checkup);
				checkup.setTip(c.getType());
				checkup = save(checkup);
				mw.getCheckUps().add(checkup);

				mw = medicalWorkerService.update(mw);
			}

		}
		if (ok1 == 0) {
			return checkup;
		} else {
			return null;
		}
	}

	/**
	 * adds new checkup request to all clinical administrators, after that method
	 * calls aspect for sending email to all clinical administrators
	 * 
	 * @input CheckupDTO ch - checkup that needs to be booked
	 * @output boolean flag - defining wheather and request is successfully added or
	 *         not
	 */
	public boolean checkupToAdmin(CheckupDTO ch, String email) {
		Checkup newCh = new Checkup(0, false, ch.getDate(), ch.getTime(), ch.getType(), 1, 0, null, false);
		User u = userService.findOneByEmail(email);
		Patient p = patientService.findOneByUserId(u.getId());
		MedicalWorker mw = medicalWorkerService.findOneById(ch.getMedicalWorker().getId());
		Clinic c = clinicService.findOneByName(ch.getClinic().getName());
		CheckUpType chType = checkUpTypeService.findOneByName(ch.getCheckUpType().getName());
		ArrayList<ClinicAdministrator> clAdmins = (ArrayList<ClinicAdministrator>) cladminService.findAll();

		if (u == null || p == null || mw == null || c == null || clAdmins == null || chType == null) {
			return false;
		} else {
			newCh.setPatient(p);
			newCh.getDoctors().add(mw);
			newCh.setClinic(c);
			newCh.setCheckUpType(chType);
			newCh.setPending(true);
			newCh.setScheduled(false);
			checkupRepository.save(newCh);

			return true;
		}

	}

	/**
	 * Method for finding all check-ups by room id, scheduled and date
	 * 
	 * @param id        - key of the room
	 * @param scheduled - is scheduled
	 * @param date      - date of the check-ups
	 * @return - (List<Checkup>) This method returns list of found check-ups
	 */
	public List<Checkup> findAllByRoomIdAndScheduledAndDate(Long id, boolean scheduled, LocalDate date) {
		return checkupRepository.findAllByRoomIdAndScheduledAndDate(id, scheduled, date);
	}

	/**
	 * Method for finding all check-ups by room id, scheduled and date
	 * 
	 * @param time - time of the defined check-up
	 * @param date - date of the check-ups
	 * @return - (List<Checkup>) This method returns list of found check-ups
	 */
	public List<Checkup> findAllByTimeAndDate(String time, LocalDate date) {
		return checkupRepository.findAllByTimeAndDate(time, date);
	}

	/**
	 * Method for changing check-up after finding a room, date and time for the appointment/operation by clinic administrator
	 * @param c - check-up with the id of the check-up that has to be updated and new informations about appointment
	 * @return - This method returns updated check-up
	 */
	@Transactional(readOnly = false)
	public Checkup update(CheckupDTO c) {
		Checkup checkup = checkupRepository.findOneById(c.getId());
		checkup.setDate(c.getDate());
		checkup.setTime(c.getTime());
		Room room = roomService.findOneById(c.getRoom().getId());
		checkup.setRoom(room);
		checkup.setScheduled(true);
		return checkupRepository.save(checkup);
	}

	/**
	 * Method for adding doctors which clinic administrator has selected to must attend the operation 
	 * @param id - id of the check-up in the database
	 * @param workers - id's of the doctors which will be assigned to operation
	 * @return - This method returns message about success of adding doctors to check-up
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW) 	// transakcija Milica
	public Checkup addDoctors(Long id, Long[] workers) throws Exception {
		Checkup checkup = checkupRepository.findOneById(id);
		checkup.setDoctors(new HashSet<MedicalWorker>());
		ArrayList<Long> doctors = new ArrayList<>();
		for(Long mw_id :workers) {
			doctors.add(mw_id);
		}
		List<Checkup> checkups = checkupRepository.findAllByTimeAndDate(checkup.getTime(), checkup.getDate());
		for(Checkup c:checkups) {
			if(c.isScheduled()) {
				for(MedicalWorker doctor:c.getDoctors()) {
					if(doctors.contains(doctor.getId())){
						doctors.remove(doctor.getId());
					}
				}
			}
		}
		for(Long mwId : doctors) {
			MedicalWorker mw = medicalWorkerService.findOneById(mwId);
			checkup.getDoctors().add(mw);
		}
		
		if(checkup.getDoctors().size() == 0) {
			return null;
		}
		else {
			return checkupRepository.save(checkup);
		}
	}

	public List<CheckupDTO> getAllQuicks(Long id) {
		List<MedicalWorker> doctors = medicalWorkerService.findAllDoctors("DOKTOR", id);
		List<CheckupDTO> ret = new ArrayList<CheckupDTO>();
		for (MedicalWorker mw : doctors) {
			for (Checkup ch : mw.getCheckUps()) {
				if (ch.getPatient() == null) {
					ret.add(new CheckupDTO(ch));
				}
			}
		}

		return ret;
	}

	public boolean bookQuickApp(Long id, String email) {
		boolean ret = true;
		Checkup foundCheckup = checkupRepository.findOneById(id);
		if (foundCheckup == null) {
			ret = false;
		} else {
			User u = userService.findOneByEmail(email);
			Patient p = patientService.findOneByUserId(u.getId());
			foundCheckup.setPatient(p);
			double price = foundCheckup.getPrice() - foundCheckup.getPrice() * (foundCheckup.getDiscount() / 100);
			foundCheckup.setPrice(price); // when checkup is finished, set price to previous without discount
			checkupRepository.save(foundCheckup); // because of adding patient to checkup

			try {
				emailService.quickAppConfirmationEmail(email, foundCheckup);
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret = true;
		}

		return ret;
	}
	
	/**
	 * Method for getting all check-ups of the logged in user
	 * @param user - logged in user
	 * @param id - id of the room if logged in user is administrator of clinic
	 * @return - This method returns all check-ups of the logged in user
	 */
	public Set<Checkup> getAllCheckups(User user, Long id) {
		if (user.getType().equals("DOKTOR")) {
			MedicalWorker worker = medicalWorkerService.findOneByUserId(user.getId());
			return worker.getCheckUps();
			}
		if(user.getType().equals("PACIJENT")) {
			Patient patient = patientService.findOneByUserId(user.getId());
			return patient.getAppointments();
		}
		if(user.getType().equals("ADMINISTRATOR")){
			Room room = roomService.findOneById(id);
			return room.getBookedCheckups();
		}
		return new HashSet<>();
	}
	
	/**
	 * Method for finding all check-ups by room id, scheduled and date
	 * 
	 * @param scheduled - is scheduled
	 * @param date - date of the check-ups
	 * @param id - key of the patient
	 * @return - (List<Checkup>) This method returns list of found check-ups
	 */
	public List<Checkup> findCheckupsByScheduledAndDateAndPatientIdAndType(boolean scheduled, LocalDate date, Long id, String type){
		return checkupRepository.findAllByScheduledAndDateAndPatientIdAndTip(scheduled, date, id, type);
	}
	
	/**
	 * method for finding scheduled check-up for today for logged doctor and selected patient
	 * @param email - email of the patient
	 * @param id - key of the logged medical worker
	 * @return CheckupDTO - found check-up if exist, else null
	 */
	public CheckupDTO findCheckup(String email, Long id) {
		User user = userService.findOneByEmail(email);
		MedicalWorker doctor = medicalWorkerService.findOneByUserId(user.getId());
		Patient patient = patientService.findOneByUserId(id);
		LocalDate date = LocalDate.now();
		List<Checkup> checkups = findCheckupsByScheduledAndDateAndPatientIdAndType(true, date, patient.getId(), "PREGLED");
		CheckupDTO checkup;
		for (Checkup c : checkups) {
			checkup = new CheckupDTO(c);
			if(checkup.getMedicalWorker().getId() == doctor.getId() && !checkup.isFinished()) {
				return checkup;
			}
		}
		return null;
	}

}
