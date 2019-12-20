package tim31.pswisa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RecipeDTO;
import tim31.pswisa.dto.UserDTO;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Recipe;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.RecipeService;
import tim31.pswisa.service.UserService;

@RestController
public class MedicalWorkerController {

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private RecipeService recipeService;

	// method returns medical worker by email

	// This method returns medical worker for update
	@GetMapping(value = "/getMedicalWorker", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> getMedicalWorker(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			MedicalWorker medicalWorker = medicalWorkerService.findByUser(user.getId());
			if (medicalWorker != null) {
				return new ResponseEntity<>(new MedicalWorkerDTO(medicalWorker), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/canAccessToMedicalRecord", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> canAccessController(HttpServletRequest request, @RequestBody String pom) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			String isOk = medicalWorkerService.canAccess(user, pom);
			return new ResponseEntity<>(isOk, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping(value = "/bookForPatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> bookForPatientController(@RequestBody CheckupDTO c, HttpServletRequest request)
			throws MailException, InterruptedException {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		medicalWorkerService.bookForPatient(user, c);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@PostMapping(value = "/deleteDoctor")
	public ResponseEntity<String> deleteDoctorController(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				String returnVal = medicalWorkerService.deleteDoctor(userDTO.getEmail(), clinicAdministrator);
				if (returnVal.equals("Obrisano")) {
					return new ResponseEntity<>("Obrisano", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Greska", HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>("Greska", HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/findDoctors", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicalWorkerDTO>> findDoctorsController(@RequestBody String[] params,
			HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					if (medicalWorkerService.findDoctors(clinic, params[0], params[1]) == null) {
						return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
					} else {
						return new ResponseEntity<>(medicalWorkerService.findDoctors(clinic, params[0], params[1]),
								HttpStatus.OK);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

	// This method updates medical worker who sends request for that
	@PostMapping(value = "/updateMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> updateMedicalWorkerController(@RequestBody MedicalWorkerDTO mw) {
		User user = userService.findOneByEmail(mw.getUser().getEmail());
		if (user != null) {
			MedicalWorker medWorker = medicalWorkerService.findByUser(user.getId());
			if (medWorker != null) {
				medWorker = medicalWorkerService.updateMedicalWorker(medWorker, mw);
				return new ResponseEntity<>(new MedicalWorkerDTO(medWorker), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping(value = "/addMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> addMedicalWorker(@RequestBody MedicalWorkerDTO mw) {
		MedicalWorker medicalWorker = medicalWorkerService.save(mw);
		if (medicalWorker != null) {
			return new ResponseEntity<>(new MedicalWorkerDTO(medicalWorker), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@GetMapping(value = "/getRecipes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RecipeDTO>> getRecipes() {
		List<Recipe> recipes = recipeService.findAllByVerified(false);
		List<RecipeDTO> ret = new ArrayList<RecipeDTO>();
		for (Recipe recipe : recipes) {
			ret.add(new RecipeDTO(recipe));
		}
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}

	/*
	 * method for searching doctors by given parameters input - String array, sent
	 * parameters from client request output - List<MedicalWorkerDTO> , list of
	 * doctors that meet searching params
	 */
	@PostMapping(value = "/searchDoctors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicalWorkerDTO>> searchDoctors(@RequestBody String[] params) {
		List<MedicalWorkerDTO> result = medicalWorkerService.searchDoctors(params);
		if (result == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/verifyRecipe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RecipeDTO> verifyRecipe(@PathVariable Long id, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		MedicalWorker nurse = medicalWorkerService.findByUser(user.getId());
		Recipe recipe = recipeService.findOneById(id);
		recipe = recipeService.verify(recipe, nurse);
		if (recipe != null) {
			return new ResponseEntity<>(new RecipeDTO(recipe), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	}

	@GetMapping(value = "/getAllDoctors", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicalWorkerDTO>> getAllDoctors(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		System.out.println(user.getName());
		System.out.println(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicAdministrator.getClinic();
				if (clinic != null) {
					if (medicalWorkerService.getDoctors(clinic) != null) {
						return new ResponseEntity<>(medicalWorkerService.getDoctors(clinic), HttpStatus.OK);
					}
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

	@GetMapping(value = "/getAllAvailable/{id}/{date}/{time}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicalWorkerDTO>> getAllAvailableDoctors(@PathVariable Long id,
			@PathVariable String date, @PathVariable String time) {
		List<MedicalWorker> doctors = medicalWorkerService.findAllAvailable(id, date, time);
		List<MedicalWorkerDTO> ret = new ArrayList<>();
		for (MedicalWorker mw : doctors) {
			ret.add(new MedicalWorkerDTO(mw));
		}
		return new ResponseEntity<List<MedicalWorkerDTO>>(ret, HttpStatus.BAD_GATEWAY);
	}

}
