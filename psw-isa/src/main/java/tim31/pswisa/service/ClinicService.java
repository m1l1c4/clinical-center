package tim31.pswisa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckUpTypeDTO;
import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CheckUpTypeRepository;
import tim31.pswisa.repository.ClinicRepository;
import tim31.pswisa.repository.RoomRepository;

@Service
public class ClinicService {

	@Autowired
	private ClinicRepository clinicRepository;

	@Autowired
	private RoomService roomService;

	@Autowired
	private CheckUpTypeService checkUpTypeService;

	@Autowired
	private CheckUpTypeRepository checkupTypeRepository;

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private CheckUpService checkupService;

	@Autowired
	private RoomRepository roomRepository;

	/**
	 * This method servers for finding room in clinic by room id
	 * 
	 * @param id - id of room that has to be found
	 * @return - (Room) This method returns found room
	 */
	public Room findRoomById(Long id) {
		return clinicRepository.findRoomById(id);
	}

	/**
	 * This method servers for finding all rooms from database
	 * 
	 * @return - (List<Room>) This method returns all room from database
	 */
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}

	/**
	 * This method servers for finding clinic by clinic id
	 * 
	 * @param id - id of clinic that has to be found
	 * @return - (Clinic) This method returns found clinic
	 */
	public Clinic findOneById(Long id) {
		return clinicRepository.findOneById(id);
	}

	/**
	 * This method servers for finding clinic by clinic name
	 * 
	 * @param id - id of room that has to be found
	 * @return - (Clinic) This method returns found clinic
	 */
	public Clinic findOneByName(String clinic) {
		return clinicRepository.findOneByName(clinic);
	}

	/**
	 * This method servers for getting raiting of clinic
	 * 
	 * @param user - logged clinic administrator
	 * @return - (double) This method returns raiting of clinic
	 */
	public double getClinicRaiting(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		return clinic.getRating();
	}

	/**
	 * This method servers for getting revenue in clinic for entered period
	 * 
	 * @param user   - scheduled or not scheduled
	 * @param params - two date / period for getting revenue
	 * @return - (Double) This method returns revenue for entered period
	 */
	public Double getRevenue(User user, String[] params) {
		String date1 = params[0];
		String date2 = params[1];
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		LocalDate date1Local = LocalDate.parse(date1);
		LocalDate date2Local = LocalDate.parse(date2);
		Double retValue = 0.0;
		System.out.println(date1Local.toString());
		System.out.println(date2Local.toString());
		if (date1Local.compareTo(date2Local) >= 0) {
			return null;
		} else {
			List<Checkup> checkups = checkupService.findAll();
			for (Checkup c : checkups) {
				if (c.getClinic().getId() == clinic.getId() && c.isScheduled()) {
					LocalDate temp = c.getDate();
					System.out.println(temp.toString());
					System.out.println(c.getDate());
					if (temp.compareTo(date1Local) >= 0 && temp.compareTo(date2Local) <= 0) {
						retValue += c.getPrice();
					}
				}
			}
		}
		return retValue;
	}

	/**
	 * This method servers for getting report for week
	 * 
	 * @param user - logged clinic administrator
	 * @return - (Integer[]) This method returns list of numbers (numbers of
	 *         operations or appointments of the seven days before current date
	 */
	public Integer[] getReportForWeek(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		Integer[] retValue = new Integer[7];
		for (int i = 0; i < retValue.length; i++) {
			retValue[i] = 0;
		}
		LocalDate date = LocalDate.now();
		date = LocalDate.parse(date.toString());
		List<Checkup> checkups = checkupService.findAll();
		for (Checkup c : checkups) {
			if (c.getClinic().getId() == clinic.getId() && c.isScheduled()) {
				if (c.getDate().equals(date.minusDays(1).toString())) {
					retValue[6] += 1;
				} else if (c.getDate().equals(date.minusDays(2).toString())) {
					retValue[5] += 1;
				} else if (c.getDate().equals(date.minusDays(3).toString())) {
					retValue[4] += 1;
				} else if (c.getDate().equals(date.minusDays(4).toString())) {
					retValue[3] += 1;
				} else if (c.getDate().equals(date.minusDays(5).toString())) {
					retValue[2] += 1;
				} else if (c.getDate().equals(date.minusDays(6).toString())) {
					retValue[1] += 1;
				} else if (c.getDate().equals(date.minusDays(7).toString())) {
					retValue[0] += 1;
				}
			}
		}
		return retValue;
	}

	/**
	 * This method servers for getting report for month
	 * 
	 * @param user - logged clinic administrator
	 * @return - (Integer[]) This method returns list of numbers (numbers of
	 *         operations or appointments of themonth
	 */
	public Integer[] getReportForMonth(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		Clinic clinic = clinicAdministrator.getClinic();
		Integer[] retValue = new Integer[12];
		for (int i = 0; i < retValue.length; i++) {
			retValue[i] = 0;
		}
		List<Checkup> checkups = checkupService.findAll();
		for (Checkup c : checkups) {
			if (c.getClinic().getId() == clinic.getId() && c.isScheduled()) {
				String temp = c.getDate().toString();
				String[] temp1 = temp.split("-");
				String temp2 = temp1[1];
				if (temp2.equals("01")) {
					retValue[0] += 1;
				} else if (temp2.equals("02")) {
					retValue[1] += 1;
				} else if (temp2.equals("03")) {
					retValue[2] += 1;
				} else if (temp2.equals("04")) {
					retValue[3] += 1;
				} else if (temp2.equals("05")) {
					retValue[4] += 1;
				} else if (temp2.equals("06")) {
					retValue[5] += 1;
				} else if (temp2.equals("07")) {
					retValue[6] += 1;
				} else if (temp2.equals("08")) {
					retValue[7] += 1;
				} else if (temp2.equals("09")) {
					retValue[8] += 1;
				} else if (temp2.equals("10")) {
					retValue[9] += 1;
				} else if (temp2.equals("11")) {
					retValue[10] += 1;
				} else if (temp2.equals("12")) {
					retValue[11] += 1;
				}
			}
		}
		return retValue;
	}

	public Clinic save(ClinicDTO c) {
		Clinic clinic = new Clinic();
		clinic.setName(c.getName());
		clinic.setCity(c.getCity());
		clinic.setAddress(c.getAddress());
		clinic.setCountry(c.getCountry());
		clinic.setDescription(c.getDescription());
		return clinicRepository.save(clinic);
	}

	/**
	 * This method servers for updating clinic
	 * 
	 * @param clinicAdministrator - logged clinic administrator
	 * @param clinic              - new data of clinic
	 * @return - (Clinic) This method returns updated clinic
	 */
	public Clinic updateClinic(ClinicAdministrator clinicAdministrator, ClinicDTO clinic) {
		Clinic nameOfClinic = clinicAdministrator.getClinic();
		List<Clinic> temp = findAll();
		String name1 = clinic.getName();
		for (Clinic c : temp) {
			if (c.getName().equals(name1) && c.getId() != nameOfClinic.getId()) {
				return null;
			}
		}
		nameOfClinic.setName(clinic.getName());
		nameOfClinic.setAddress(clinic.getAddress());
		nameOfClinic.setCity(clinic.getCity());
		nameOfClinic.setDescription(clinic.getDescription());
		nameOfClinic = update(nameOfClinic);
		if (nameOfClinic != null)
			return nameOfClinic;
		else
			return null;
	}

	/**
	 * This method servers for editing type of check-up
	 * 
	 * @param clinic - clinic of logged clinic administrator
	 * @param before - older name of type
	 * @param after  - new name of type
	 * @param price  - new price of type
	 * @return - (CheckUpType) This method returns updated check-up type or null if
	 *         there is check-up type with same name
	 */
	public CheckUpType editType(Clinic clinic, String before, String after, String price) {
		CheckUpType retVal = new CheckUpType();
		List<Clinic> clinics = findAll();
		retVal = checkUpTypeService.findOneByName(before);
		for (Clinic klinika : clinics) {
			if (klinika.getAvailableAppointments() != null) {
				for (Checkup c : klinika.getAvailableAppointments()) {
					if (c.getType().equals(before)) {
						return null; // returns null if can't change name of type
					}
				}
			}
		}
		if (checkUpTypeService.update(retVal, after, price) == null) {
			return null;
		} else {
			retVal = checkUpTypeService.update(retVal, after, price);
			return retVal;
		}
	}

	/**
	 * This method servers for getting one type of check-up
	 * 
	 * @param clinicAdministrator - logged clinic administrator
	 * @param name                - name of type that have to be gotten
	 * @return - (ArrayList<CheckUpTypeDTO>) This method returns one type in list
	 */
	public ArrayList<CheckUpTypeDTO> getOneTypeInClinic(ClinicAdministrator clinicAdministrator, String name) {
		Clinic clinic = clinicAdministrator.getClinic();
		ArrayList<CheckUpTypeDTO> temp = new ArrayList<CheckUpTypeDTO>();
		if (clinic != null) {
			Set<CheckUpType> temps = clinic.getCheckUpTypes();
			for (CheckUpType c : temps) {
				if (c.getName().equals(name)) {
					temp.add(new CheckUpTypeDTO(c));
				}
			}
			return temp;
		}
		return null;
	}

	/**
	 * This method servers for filter rooms in clinic
	 * 
	 * @param clinic - clinic of logged clinic administrator
	 * @param number - number of room
	 * @return - (List<RoomDTO>) This method returns list of rooms with entered
	 *         number (one room)
	 */
	public List<RoomDTO> filterRooms(Clinic clinic, int number) {
		Set<Room> temp = clinic.getRooms();
		List<RoomDTO> ret = new ArrayList<RoomDTO>();
		for (Room r : temp) {
			if (r.getNumber() == number) {
				ret.add(new RoomDTO(r));
				return ret;
			}
		}
		return null;
	}

	/**
	 * This method servers for getting all doctors in clinic
	 * 
	 * @param user - logged clinic administrator
	 * @return - (ArrayList<MedicalWorkerDTO>>) This method returns list of doctors
	 *         in clinic
	 */
	public ArrayList<MedicalWorkerDTO> getAllDoctors(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		ArrayList<MedicalWorkerDTO> dtos = new ArrayList<MedicalWorkerDTO>();
		if (clinicAdministrator != null) {
			Clinic clinic = clinicAdministrator.getClinic();
			if (clinic != null) {
				List<MedicalWorker> workers = medicalWorkerService.findAllByClinicId(clinic.getId());
				for (MedicalWorker d : workers) {
					dtos.add(new MedicalWorkerDTO(d));
				}
				return dtos;
			}
		}
		return null;
	}

	/**
	 * This method servers for getting all types of check-ups
	 * 
	 * @param user - logged clinic administrator
	 * @return - (ArrayList<CheckUpTypeDTO>>) This method returns list of check-up
	 *         types
	 */
	public ArrayList<CheckUpTypeDTO> getAllTypes(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		ArrayList<CheckUpTypeDTO> dtos = new ArrayList<CheckUpTypeDTO>();
		if (clinicAdministrator != null) {
			Clinic clinic = clinicAdministrator.getClinic();
			if (clinic != null) {
				Set<CheckUpType> tmp = clinic.getCheckUpTypes();
				for (CheckUpType c : tmp) {
					dtos.add(new CheckUpTypeDTO(c));
				}
			}
			return dtos;
		}
		return null;
	}

	/**
	 * This method servers for getting all rooms in clinic
	 * 
	 * @param user - logged clinic administrator
	 * @return - (List<RoomDTO>>) This method returns list rooms in clinic
	 */
	public List<RoomDTO> getAllRooms(User user) {
		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		if (clinicAdministrator != null) {
			Clinic clinic = clinicAdministrator.getClinic();
			if (clinic != null) {
				List<Room> rooms = roomService.findAllByClinicId(clinic.getId());
				List<RoomDTO> dtos = new ArrayList<RoomDTO>();
				for (Room r : rooms) {
					dtos.add(new RoomDTO(r));
				}
				return dtos;
			}
		}
		return null;
	}

	/**
	 * This method servers for searching rooms in clinic by criteria
	 * 
	 * @param clinic - clinic of logged clinic administrator
	 * @param params - criteria of searching, params[0] is name, params[1] is type
	 *               of room and params[2] is date when it is free
	 * @return - (List<RoomDTO>) This method returns list of rooms with enterd
	 *         criteria
	 */
	public List<RoomDTO> searchRooms(Clinic clinic, String[] params) {
		String name = params[0];
		String type = params[1];
		LocalDate date = LocalDate.parse(params[2]);
		List<RoomDTO> ret = new ArrayList<RoomDTO>();
		Set<Room> temp = clinic.getRooms();
		int counter = 0;
		if (name.equals("undefined") || name.equals("")) {
			for (Room r : temp) {
				if (r.getTypeRoom().equals(type)) {
					Set<Checkup> checkups = r.getBookedCheckups();
					for (Checkup c : checkups) {
						if (c.getDate().equals(date)) {
							counter++;
						}
					}
					if (counter < 8) {
						ret.add(new RoomDTO(r));
					}
				}
			}
		} else {
			counter = 0;
			for (Room r : temp) {
				if (r.getName().equals(name) && r.getTypeRoom().equals(type)) {
					Set<Checkup> checkups = r.getBookedCheckups();
					for (Checkup c : checkups) {
						if (c.getDate().equals(date)) {
							counter++;
						}
					}
					if (counter < 8) {
						ret.add(new RoomDTO(r));
					}
				}
			}
		}
		if (ret.size() == 0) {
			return null;
		} else {
			return ret;
		}
	}

	/**
	 * This method servers for deleting room in clinic by name
	 * 
	 * @param name                - name of room that has to be deleted
	 * @param clinicAdministrator - logged clinic administrator
	 * @return - (String) This method string 'Obrisano' or ''
	 */
	public String deleteRoom(String name, ClinicAdministrator clinicAdministrator) {
		Clinic clinic = findOneById(clinicAdministrator.getClinic().getId());
		Set<Room> sobe = clinic.getRooms();
		for (Room r : sobe) {
			if (r.getName().equals(name)) {
				clinic.getRooms().remove(r);
				clinic = updateClinic(clinicAdministrator, new ClinicDTO(clinic));
				roomRepository.delete(r);
				return "Obrisano";
			}
		}
		return "";
	}

	/**
	 * This method servers for deleting room in clinic by number
	 * 
	 * @param number              - clinic of logged clinic administrator
	 * @param clinicAdministrator - logged clinic administrator
	 * @return - (String) This method returns string 'Obrisano' or ''
	 */
	public String deleteRoomN(int number, ClinicAdministrator clinicAdministrator) {
		Clinic clinic = findOneById(clinicAdministrator.getClinic().getId());
		Set<Room> sobe = clinic.getRooms();
		for (Room r : sobe) {
			if ((r.getNumber() == number) && (r.isFree() == true) && (r.getBookedCheckups().size() == 0)) {
				System.out.println(clinic.getRooms().size());
				clinic.getRooms().remove(r);
				clinic = update(clinic);
				System.out.println(clinic.getRooms().size());
				r.setClinic(null);
				roomRepository.save(r);
				return "Obrisano";
			}
		}
		return "";
	}

	/**
	 * This method servers for adding room in clinic
	 * 
	 * @param room                - room that has to be added
	 * @param clinicAdministrator - logged clinic administrator
	 * @return - (Room) This method returns added room
	 */
	public Room addRoom(RoomDTO room, ClinicAdministrator clinicAdministrator) {
		Room room1 = new Room();
		room1.setName(room.getName());
		room1.setNumber(room.getNumber());
		room1.setTypeRoom(room.getTypeRoom());
		Clinic klinika = new Clinic();
		klinika = findOneById(clinicAdministrator.getClinic().getId());
		List<Room> allRooms = roomService.findAllByClinicId(klinika.getId());
		for (Room r : allRooms) {
			if ((r.getNumber() == room.getNumber()) && r.getClinic() != null) {
				return null;
			}
		}
		room1.setClinic(klinika);
		room1.setFree(true);
		klinika.getRooms().add(room1);
		room1 = roomService.save(room1);
		return room1;
	}

	/**
	 * This method servers for changing room in clinic
	 * 
	 * @param room                - room that has to be changed
	 * @param clinicAdministrator - logged clinic administrator
	 * @return - (Room) This method returns changed room
	 */
	public Room changeRoom(RoomDTO room, ClinicAdministrator clinicAdministrator) {
		Clinic klinika = findOneById(clinicAdministrator.getClinic().getId());
		Room room1 = roomRepository.findOneByClinicIdAndNumber(klinika.getId(), room.getNumber());
		if (!room1.isFree() || room1.getBookedCheckups().size() == 0) {
			return null;
		}
		room1.setName(room.getName());
		room1.setTypeRoom(room.getTypeRoom());
		roomRepository.save(room1);
		return room1;
	}

	public List<Clinic> searchClinics(String[] params) {
		List<Clinic> retClinics = new ArrayList<Clinic>();
		List<Clinic> result = new ArrayList<Clinic>();
		int counter = 0; // assuming there are 7 checkups in one day
		CheckUpType srchType = checkupTypeRepository.findOneByName(params[0]);

		if (params[0].equals("") || params[1].equals("") || srchType == null)
			return null; // nothing to search

		else {
			for (Clinic cl : srchType.getClinics()) {
				retClinics.add(cl); // all clinics of specified type of check-up
			}

			// check if clinic has available doctor, if not remove that clinic from list
			for (Clinic cl : retClinics) {
				for (MedicalWorker mw : cl.getMedicalStuff()) {
					if (mw.getUser().getType().equals("DOKTOR") && mw.getType().equals(params[0])) {
						for (Checkup c : mw.getCheckUps()) {
							if (c.getDate().toString().equals(params[1])) {
								counter++;
							}

						}
						if (counter < 7) {
							result.add(cl);
							break;
						}
					}
				}
			}

			return result;

		}

	}

	public List<Clinic> filterClinics(String parametar, ArrayList<Clinic> clinics) {
		int ranging = -1;
		List<Clinic> filtered = new ArrayList<Clinic>();
		ranging = Integer.parseInt(parametar);

		for (Clinic clinic : clinics) {
			if (clinic.getRating() >= ranging) {
				filtered.add(clinic);
			}
		}

		return filtered;
	}

	public List<MedicalWorkerDTO> doctorsInClinic(String name, String type, String date) {
		Clinic cl = clinicRepository.findOneByName(name);
		List<MedicalWorkerDTO> doctors = new ArrayList<MedicalWorkerDTO>();
		int counter = 0;
		if (cl != null) {
			for (MedicalWorker medicalWorker : cl.getMedicalStuff()) {
				if (medicalWorker.getType().equals(type)) {
					for (Checkup c : medicalWorker.getCheckUps()) {
						if (c.getDate().toString().equals(date)) {
							counter++;
						}
					}
					if (counter < 7) {
						MedicalWorkerDTO mw = new MedicalWorkerDTO(medicalWorker);
						doctors.add(mw);
						break;
					}
				}
			}
			boolean taken = false;
			ArrayList<String> pom = new ArrayList<String>(); // list of times of appointments for specific date
			for (MedicalWorkerDTO mw : doctors) {
				MedicalWorker medicalWorker = medicalWorkerService.findOneById(mw.getId());
				for (int i = medicalWorker.getStartHr(); i < medicalWorker.getEndHr(); i++) {
					for (Checkup ch : medicalWorker.getCheckUps()) {
						if (Integer.parseInt(ch.getTime()) == i) {
							taken = true;
							break;
						}
					}
					if (!taken) {
						pom.add(Integer.toString(i));
					}
				}

				mw.getAvailableCheckups().put(date, pom);

			}
			return doctors;
		}
		return null;

	}

	public Clinic update(Clinic clinic) {
		for (Room r : clinic.getRooms())
			r.setClinic(clinic);
		return clinicRepository.save(clinic);
	}

	public Room addRoom(Clinic clinic, RoomDTO r) {
		Room room = new Room();
		room.setClinic(clinic);
		room.setFree(true);
		room.setName(r.getName());
		room.setTypeRoom(r.getTypeRoom());
		room.setNumber(r.getNumber());
		room.setFirstFreeDate(r.getFirstFreeDate());
		return roomService.save(room);
	}

}
