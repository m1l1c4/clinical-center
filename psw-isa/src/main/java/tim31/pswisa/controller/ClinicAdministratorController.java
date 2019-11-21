package tim31.pswisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.ssl.SSLConfigurableServerSocket;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.UserService;

@RestController
public class ClinicAdministratorController {

	@Autowired
	public ClinicAdministratorService clinicAdministratorService;
	
	@Autowired
	public UserService userService;
	
	@GetMapping(value="/getAdministrator/{email}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdministrator> getAdministrator(@PathVariable String email) {
			
		User user = userService.findOneByEmail(email);
		if(user!=null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		    if(clinicAdministrator == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		else {
				return new ResponseEntity<>(clinicAdministrator,HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value="/updateAdministrator/{email}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdministrator> updateAdministrator(@RequestBody ClinicAdministrator ca, @PathVariable String email) 
	{
		User user = userService.findOneByEmail(email);
		if(user!=null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			clinicAdministrator.getUser().setName(ca.getUser().getName());
			clinicAdministrator.getUser().setSurname(ca.getUser().getSurname());
			clinicAdministrator.getUser().setPassword(ca.getUser().getPassword());
			clinicAdministrator.getUser().setActivated(user.getActivated());
			clinicAdministrator.getUser().setType(user.getType());
			clinicAdministrator = clinicAdministratorService.update(ca);
			if(clinicAdministrator == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}else
				return new ResponseEntity<>(ca,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
}

