package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.Absence;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.User;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.repository.CheckUpRepository;

@Service
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
	private CheckUpRepository checkupRepository;

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
			if (a.getStartVacation().toString().equals(c.getDate())) {
				ok = 1;
			}
		}
		if (ok == 0) {
			checkup.setMedicalWorker(mw);
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
			checkup.setType(c.getType());
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
				checkup.setType(c.getType());
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
}
