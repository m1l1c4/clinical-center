package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
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
	private RoomRepository roomRepository;

	@Autowired
	private MedicalWorkerService medicalWorkerService;
	
	@Autowired
	private RoomRepository roomRepository;

	public Room findRoomById(Long id) {
		return clinicRepository.findRoomById(id);
	}

	public List<Clinic> findAll() {
		return clinicRepository.findAll();
	}

	public Clinic findOneById(Long id) {
		return clinicRepository.findOneById(id);
	}

	public Clinic findOneByName(String clinic) {
		return clinicRepository.findOneByName(clinic);
	}

	public Clinic save(ClinicDTO c) {
		Clinic clinic = new Clinic();
		clinic.setName(c.getName());
		clinic.setCity(c.getCity());
		clinic.setAddress(c.getAddress());
		clinic.setDescription(c.getDescription());
		return clinicRepository.save(clinic);
	}

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

	// can't edit the name of type if there is just one appointment with that type
	// in just one clinic of clinical center
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

	public RoomDTO filterRooms(Clinic clinic, int number) {
		Set<Room>temp = clinic.getRooms();
		RoomDTO ret = new RoomDTO();
		for(Room r : temp) {
			if(r.getNumber() == number) {
				ret = new RoomDTO(r);
				return ret;
			}
		}
		return null;
	}
	
	public List<RoomDTO>searchRooms(Clinic clinic, String [] params){
		String name = params[0];
		String type = params[1];
		List<RoomDTO>ret = new ArrayList<RoomDTO>();
		System.out.println(params[0]);
		System.out.println(params[1]);
		Set<Room>temp = clinic.getRooms();
		if(name.equals("undefined") || name.equals("")) {
			for(Room r:temp) {
				if(r.getTypeRoom().equals(type)) {
					System.out.println("udje li");
					ret.add(new RoomDTO(r));
				}
			}
		}else {
			for(Room r : temp) {
				if(r.getName().equals(name) && r.getTypeRoom().equals(type)) {
					ret.add(new RoomDTO(r));
				}
			}
		}
		if(ret.size() == 0) {
			return null;
		}
		else {
			return ret;
		}
	}
	
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

	public String deleteRoomN(int number, ClinicAdministrator clinicAdministrator) {
		Clinic clinic = findOneById(clinicAdministrator.getClinic().getId());
		Set<Room> sobe = clinic.getRooms();
		for (Room r : sobe) {
			if ((r.getNumber() == number) && (r.isFree() == true)) {
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

	public Room addRoom(RoomDTO room, ClinicAdministrator clinicAdministrator) {
		Room room1 = new Room();
		room1.setName(room.getName());
		room1.setNumber(room.getNumber());
		room1.setTypeRoom(room.getTypeRoom());
		Clinic klinika = new Clinic();
		klinika = findOneById(clinicAdministrator.getClinic().getId());
		List<Room> allRooms = roomService.findAllByClinicId(klinika.getId());
		for (Room r : allRooms) {
			if ((r.getNumber() == room.getNumber())) {
				return null;
			}
		}
		room1.setClinic(klinika);
		room1.setFree(true);
		klinika.getRooms().add(room1);
		room1 = roomService.save(room1);
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
		List<String> temp = new ArrayList<String>(); // list of times of appointments for specific date
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
			ArrayList<String> pom = new ArrayList<String>();
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
		return roomService.save(room);
	}
	
	public RoomDTO filterRooms(Clinic clinic, int number) {
		Set<Room>temp = clinic.getRooms();
		RoomDTO ret = new RoomDTO();
		for(Room r : temp) {
			if(r.getNumber() == number) {
				ret = new RoomDTO(r);
				return ret;
			}
		}
		return null;
	}

	public List<RoomDTO>searchRooms(Clinic clinic, String [] params){
		String name = params[0];
		String type = params[1];
		List<RoomDTO>ret = new ArrayList<RoomDTO>();
		System.out.println(params[0]);
		System.out.println(params[1]);
		Set<Room>temp = clinic.getRooms();
		if(name.equals("undefined") || name.equals("")) {
			for(Room r:temp) {
				if(r.getTypeRoom().equals(type)) {
					System.out.println("udje li");
					ret.add(new RoomDTO(r));
				}
			}
		}else {
			for(Room r : temp) {
				if(r.getName().equals(name) && r.getTypeRoom().equals(type)) {
					ret.add(new RoomDTO(r));
				}
			}
		}
		if(ret.size() == 0) {
			return null;
		}
		else {
			return ret;
		}
	}
}
