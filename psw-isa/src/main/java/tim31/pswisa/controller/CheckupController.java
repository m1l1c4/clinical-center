package tim31.pswisa.controller;

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

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.ReportDTO;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.Codebook;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Recipe;
import tim31.pswisa.model.Report;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.CheckUpTypeService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.CodebookService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.ReportService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value = "/checkup")
public class CheckupController {

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private UserService userService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	CheckUpTypeService checkUpTypeService;

	@Autowired
	private CheckUpService checkupService;

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private CodebookService codebookService;

	@PostMapping(value = "/addReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReportDTO> saveReport(@RequestBody ReportDTO r) {
		Report report = reportService.add(r);
		if (report == null) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(new ReportDTO(report), HttpStatus.CREATED);
	}

	/**
	 * This method servers for adding new appointment for booking with one click
	 * 
	 * @param c       - check-up that have to be added
	 * @param request -
	 * @return - This method returns added appointment if doctor are not busy
	 */
	@PostMapping(value = "/addAppointment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CheckupDTO> addAppointmentController(@RequestBody CheckupDTO c, HttpServletRequest request) {
		User doctorOne = userService.findOneByEmail(c.getMedicalWorker().getUser().getEmail());
		MedicalWorker doctorOne1 = medicalWorkerService.findByUser(doctorOne.getId());
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Checkup check = checkupService.addAppointment(c, doctorOne1, clinicAdministrator);
				if (check == null) {
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				} else {
					// return new ResponseEntity<>(new CheckupDTO(check), HttpStatus.OK);
					return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<CheckupDTO> getCheckup(@PathVariable Long id) {
		Checkup checkup = checkupService.findOneById(id);
		if (checkup != null) {
			return new ResponseEntity<>(new CheckupDTO(checkup), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/addRecipes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addRecipes(@RequestBody String[] recipes, @PathVariable Long id) {
		Report report = reportService.findOneById(id);
		for (String code : recipes) {
			Codebook codebook = codebookService.findOneByCode(code);
			Recipe r = reportService.addRecipe(report, codebook);
			if (r == null)
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		return new ResponseEntity<>("Uspjesno dodato", HttpStatus.OK);
	}

}
