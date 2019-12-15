package tim31.pswisa.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckUpTypeDTO;
import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpTypeService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.RoomService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value = "/clinic")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private CheckUpTypeService checkUpTypeService;

	// This method updates clinic by administrator who is using application at the
	// moment, gets administrator and his clinic
	@PostMapping(value = "/updateClinic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicDTO> upadateClinicController(@RequestBody ClinicDTO clinic,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);

		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic temp = clinicService.updateClinic(clinicAdministrator, clinic);
				if (temp != null) {
					return new ResponseEntity<>(new ClinicDTO(temp), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getClinics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {
		List<Clinic> clinics = clinicService.findAll();
		List<ClinicDTO> retDto = new ArrayList<ClinicDTO>();

		if (clinics == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			for (Clinic clinic : clinics) {
				ClinicDTO cldto = new ClinicDTO(clinic);

				retDto.add(cldto);
			}

			return new ResponseEntity<>(retDto, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/changeNameOfType", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CheckUpTypeDTO> changeTypeNameController(@RequestBody String[] params,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);

		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					CheckUpType temp = new CheckUpType();
					temp = clinicService.editType(clinic, params[0], params[1], params[2]);
					if (temp == null) {
						return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
					} else {
						return new ResponseEntity<>(new CheckUpTypeDTO(temp), HttpStatus.OK);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

	@PostMapping(value = "/searchOneType/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<CheckUpTypeDTO>> getOneTypeController(@PathVariable String name,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);

		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					Set<CheckUpType> temps = clinic.getCheckUpTypes();
					for (CheckUpType c : temps) {
						if (c.getName().equals(name)) {
							ArrayList<CheckUpTypeDTO> temp = new ArrayList<CheckUpTypeDTO>();
							temp.add(new CheckUpTypeDTO(c));
							return new ResponseEntity<>(temp, HttpStatus.OK);
						}
					}
					return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	
	@PostMapping(value = "/searchRooms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> searchRoomsController(@RequestBody String[] params, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if(user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if(clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if(clinic != null) {
					System.out.println(params[0]);
					System.out.println(params[1]);
					List<RoomDTO> ret = clinicService.searchRooms(clinic,params);
					return new ResponseEntity<>(ret,HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>( HttpStatus.BAD_GATEWAY);
	}

	@PostMapping(value = "/filterRooms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomDTO> filterRoomsController(@RequestBody int number, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if(user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if(clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if(clinic != null) {
					RoomDTO ret = clinicService.filterRooms(clinic,number);
					return new ResponseEntity<>(ret,HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>( HttpStatus.BAD_GATEWAY);
	}
	
	@PostMapping(value = "/searchClinic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> searchClinics(@RequestBody String[] params) {
		List<Clinic> ret = clinicService.searchClinics(params);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@PostMapping(value = "/filterClinic/{p}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> filterClinics(@PathVariable String p, @RequestBody List<Clinic> clinics) {
		List<Clinic> ret = new ArrayList<Clinic>();
		ret = clinicService.filterClinics(p, (ArrayList<Clinic>) clinics);

		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	/*
	 * find all doctors in one clinic by clinic id input - string, clinic id return
	 * value - List<MedicalWorker> , list of all doctors in clinic
	 */
	@PostMapping(value = "/clinicDoctors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicalWorkerDTO>> getDoctorsByClinicId(@RequestBody String[] params) {
		List<MedicalWorkerDTO> ret = clinicService.doctorsInClinic(params[0], params[1], params[2]);
		if (ret == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(ret, HttpStatus.OK);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO c) {
		Clinic clinic = clinicService.save(c);
		if (clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.CREATED);
	}

	// This method returns all rooms in clinic and its administrator is logged user
	// at the moment
	@GetMapping(value = "/getRooms", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> getRooms(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					List<Room> rooms = roomService.findAllByClinicId(clinic.getId());
					List<RoomDTO> dtos = new ArrayList<RoomDTO>();
					for (Room r : rooms) {
						dtos.add(new RoomDTO(r));
					}
					return new ResponseEntity<>(dtos, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// This method is not used at the moment
	@GetMapping(value = "/getFreeRooms", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Room>> getFreeRooms(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					List<Room> rooms = roomService.findAllByClinicId(clinic.getId());
					Set<Room> temp = new HashSet<Room>();
					for (Room r : rooms) {
						temp.add(r);
					}
					return new ResponseEntity<>(temp, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// This method returns doctors of clinic for create new medical appointment by
	// clinic administrator
	@GetMapping(value = "/getDoctors", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<MedicalWorkerDTO>> getAllMedicalWorkers(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					Set<MedicalWorker> workers = medicalWorkerService.findAllByClinicId(clinic.getId());
					ArrayList<MedicalWorkerDTO> dtos = new ArrayList<MedicalWorkerDTO>();
					for (MedicalWorker d : workers) {
						dtos.add(new MedicalWorkerDTO(d));
					}
					return new ResponseEntity<>(dtos, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/getAllTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<CheckUpTypeDTO>> getAllTypes(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					Set<CheckUpType> tmp = clinic.getCheckUpTypes();
					ArrayList<CheckUpTypeDTO> dtos = new ArrayList<CheckUpTypeDTO>();
					for (CheckUpType c : tmp) {
						dtos.add(new CheckUpTypeDTO(c));
					}
					return new ResponseEntity<>(dtos, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// This method returns clinic of administrator who is logged at the moment
	@GetMapping(value = "/getClinic", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicDTO> getClinic(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
				return new ResponseEntity<>(new ClinicDTO(clinic), HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// This method deletes room by name in clinic, used by administrator of clinic
	@PostMapping(value = "/deleteRoom/{number}")
	public ResponseEntity<String> deleteTypeController(@PathVariable int number, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				String returnVal = clinicService.deleteRoomN(number, clinicAdministrator);
				if (returnVal.equals("Obrisano")) {
					return new ResponseEntity<>("Obrisano", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Greska", HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>("Greska", HttpStatus.BAD_REQUEST);
	}

	// This method adds new room in clinic, already save some data such as type and
	// isFree
	@PostMapping(value = "/addRoom", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomDTO> addRoomController(@RequestBody RoomDTO room, HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);

		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Room room1 = clinicService.addRoom(room, clinicAdministrator);
				if (room1 == null) {
					return new ResponseEntity<>(room, HttpStatus.ALREADY_REPORTED);
				} else {
					return new ResponseEntity<>(new RoomDTO(room1), HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(room, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(room, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/addRooms/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> addRooms(@RequestBody List<RoomDTO> rooms, @PathVariable Long id) {
		Clinic clinic = clinicService.findOneById(id);
		for (RoomDTO room : rooms) {
			Room r = clinicService.addRoom(clinic, room);
			if (r == null)
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@GetMapping(value = "/getClinicsByType/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicDTO>> getClinicByCheckupType(@PathVariable String name) {
		Set<Clinic> clinics = checkUpTypeService.findClinics(name);
		List<ClinicDTO> ret = new ArrayList<>();
		for (Clinic clinic : clinics) {
			ret.add(new ClinicDTO(clinic));
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	@PostMapping(value = "/searchRooms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RoomDTO>> searchRoomsController(@RequestBody String[] params,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					System.out.println(params[0]);
					System.out.println(params[1]);
					List<RoomDTO> ret = clinicService.searchRooms(clinic, params);
					return new ResponseEntity<>(ret, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

	@PostMapping(value = "/filterRooms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RoomDTO> filterRoomsController(@RequestBody int number, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					RoomDTO ret = clinicService.filterRooms(clinic, number);
					return new ResponseEntity<>(ret, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

}