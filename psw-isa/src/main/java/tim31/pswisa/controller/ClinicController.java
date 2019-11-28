package tim31.pswisa.controller;
import java.util.List;

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

import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value="/clinic")
public class ClinicController {
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ClinicAdministratorService clinicAdministratorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@PostMapping(value="/upadateClinic", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Clinic> upadateClinic(@RequestBody Clinic clinic, HttpServletRequest request){
		
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		List<Clinic>temp = clinicService.findAll();
		String name1 = clinic.getName();
		
		if(user!=null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		    if(clinicAdministrator != null) {
		    	Clinic nameOfClinic = clinicAdministrator.getClinic();
		    	for(Clinic c : temp) {
					if(c.getName().equals(name1) && c.getId()!=nameOfClinic.getId()) {
						return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
					}
				}
				
		    	nameOfClinic.setName(clinic.getName());
		    	nameOfClinic.setAddress(clinic.getAddress());
		    	nameOfClinic.setCity(clinic.getCity());
			    nameOfClinic.setRating(nameOfClinic.getRating());
			    nameOfClinic.setMedicalStuff(nameOfClinic.getMedicalStuff());
			    nameOfClinic.setAvailableAppointments(nameOfClinic.getAvailableAppointments());
			    nameOfClinic.setPatients(nameOfClinic.getPatients());
			    nameOfClinic.setRooms(nameOfClinic.getRooms());
			    nameOfClinic = clinicService.save(nameOfClinic);
			    return new ResponseEntity<>(HttpStatus.OK);
		    }
		    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
		    	Clinic clinic = clinicAdministrator.getClinic();
			    return new ResponseEntity<>(clinic,HttpStatus.OK);
		    }
		    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}