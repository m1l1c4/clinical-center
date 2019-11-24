package tim31.pswisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Patient;
import tim31.pswisa.service.PatientService;
import tim31.pswisa.service.UserService;

@RestController
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/patientsRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Patient>> saveClinic()
	{
		List<Patient> patients = patientService.findAllByActive(userService.findAllByActivated(false));

		return new ResponseEntity<>(patients, HttpStatus.OK);
	}

}