package tim31.pswisa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.CheckUpTypeService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.UserService;
import tim31.pswisa.model.Report;
import tim31.pswisa.service.ReportService;

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

	@PostMapping(value = "/addReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Report> saveClinic(@RequestBody Report r) {
		Report report = reportService.add(r);

		if (report == null)
			return new ResponseEntity<>(report, HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(report, HttpStatus.CREATED);
	}

	// have to modify just for doctors
	// This method adding new appointment created by clinic administrator. Patients
	// can booked this with one click
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
					return new ResponseEntity<>(c, HttpStatus.ALREADY_REPORTED);
				} else {
					return new ResponseEntity<>(new CheckupDTO(check), HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(c, HttpStatus.NOT_FOUND);
	}

}
