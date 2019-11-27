package tim31.pswisa.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.UserService;

@RestController
public class ClinicController {
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	public ClinicAdministratorService clinicAdministratorService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@PostMapping(value="/upadateClinic/{token}", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> upadateClinic(@RequestBody Clinic clinic, @PathVariable String token){
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		List<Clinic>temp = clinicService.findAll();
		String name1 = clinic.getName();
		for(Clinic c : temp) {
			if(c.getName().equals(name1)) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			}
		}
		
		if(user!=null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		    if(clinicAdministrator != null) {
		    	String nameOfClinic = clinicAdministrator.getClinic();
		    	Clinic pom = clinicService.findOneByName(nameOfClinic);
			    pom.setName(clinic.getName());
			    pom.setAddress(clinic.getAddress());
			    pom.setCity(clinic.getCity());
			    pom.setRating(pom.getRating());
			    pom.setMedicalStuff(pom.getMedicalStuff());
			    pom.setAvailableAppointments(pom.getAvailableAppointments());
			    pom.setPatients(pom.getPatients());
			    pom.setRooms(pom.getRooms());
			    pom = clinicService.save(pom);
			    return new ResponseEntity<>(HttpStatus.OK);
		    }
		    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
	
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
	
	@GetMapping(value="/getClinic/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> getClinic(@PathVariable String token) {
		
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if(user!=null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		    if(clinicAdministrator != null) {
		    	String nameOfClinic = clinicAdministrator.getClinic();
		    	Clinic temp = clinicService.findOneByName(nameOfClinic);
			    return new ResponseEntity<>(temp,HttpStatus.OK);
		    }
		    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}