package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
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
	private RoomService roomService;

	@Autowired
	private CheckUpRepository checkupRepository;

	public List<Checkup> findAll() {
		return checkupRepository.findAll();
	}

	public List<Checkup> findAllByClinicId(Long id) {
		return checkupRepository.findAllByClinicId(id);
	}

	public Checkup save(Checkup c) {
		return checkupRepository.save(c);
	}

	public Checkup findOneById(Long id) {
		return checkupRepository.findOneById(id);
	}
	public List<CheckupDTO>findAllByScheduled(boolean ok){
		List<Checkup>temp = checkupRepository.findAll();
		List<CheckupDTO>retVal = new ArrayList<CheckupDTO>();
		for(Checkup c : temp) {
			if(!c.isScheduled()) {
				retVal.add(new CheckupDTO(c));
			}
		}
		return retVal;
	}

	public Checkup addAppointment(CheckupDTO c, MedicalWorker mw, ClinicAdministrator clinicAdministrator) {
		Checkup checkup = new Checkup();
		checkup.setMedicalWorker(mw);
		CheckUpType typeC = checkUpTypeService.findOneByName(c.getCheckUpType().getName());
		checkup.setCheckUpType(typeC);
		checkup.setPrice(c.getPrice());
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
						return null;
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

			return checkup;
		} else
			return null;
	}

}
