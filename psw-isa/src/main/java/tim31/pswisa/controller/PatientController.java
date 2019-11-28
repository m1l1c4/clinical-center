package tim31.pswisa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping(value="/getPatientProfile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> getPatient()
	{
		ResponseEntity<Patient> ret ;
		Patient p = patientService.findOneById((long)1) ;
		if (p!=null)
			ret = new ResponseEntity<>(p, HttpStatus.OK);			
		else
			ret =  new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		return ret;
	}
	
	@PutMapping(value="/editPatient", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Patient> editPatient(@RequestBody Patient p)
	{
		Patient patient = patientService.findOneByEmail(p.getUser().getEmail());
		
		if (patient != null)
			{
				patientService.editP(patient, p);
				patientService.save(patient);
				return new ResponseEntity<>(patient, HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

}