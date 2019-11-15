package tim31.pswisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.Patient;
import tim31.pswisa.service.ClinicService;

@RestController
public class ClinicController {
	
	@Autowired
	private ClinicService clinicService;
	
	@PostMapping(value="/clinic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> saveClinic(@RequestBody Clinic c)
	{
		Clinic clinic = new Clinic();
		clinic.setName(c.getName());
		clinic.setCity(c.getCity());
		clinic.setAddress(c.getAddress());
		clinic.setRooms(c.getRooms());
 
		clinic = clinicService.save(clinic);
		if (clinic == null)
			return new ResponseEntity<>(clinic, HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(clinic, HttpStatus.CREATED);
	}
}
