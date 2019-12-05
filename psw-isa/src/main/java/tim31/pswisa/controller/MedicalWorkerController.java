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

import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.service.LoggingService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.UserService;
import tim31.pswisa.service.LoggingService;
import tim31.pswisa.service.MedicalWorkerService;

@RestController
public class MedicalWorkerController {

	@Autowired
	public MedicalWorkerService medicalWorkerService;
	
	@Autowired
	public UserService userService;
	
	// method returns medical worker by email
	
	@GetMapping(value="/getMedicalWorker", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorker> getMedicalWorker() 
	{
		/*User user = userService.findOneByEmail(email);
		if(user!=null) {
			MedicalWorker medicalWorker = medicalWorkerService.findByUser(user.getId());
			if(medicalWorker == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
				return new ResponseEntity<>(medicalWorker,HttpStatus.OK);
			}
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		*/
		MedicalWorker med = medicalWorkerService.findOneById((long) 1);
		
		return new ResponseEntity<>(med,HttpStatus.OK);
	}
	
	// method updates medical worker by email, parameter of this method is MedicalWorker object
	
	@PostMapping(value="/updateMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorker> updateMedicalWorker(@RequestBody MedicalWorker mw) 
	{
		/*User user = userService.findOneByEmail(email);
		if(user!=null) {
			MedicalWorker medWorker = medicalWorkerService.findByUser(user.getId());
			medWorker.getUser().setName(mw.getUser().getName());
			medWorker.getUser().setSurname(mw.getUser().getSurname());
			medWorker.setPhone(mw.getPhone());
			medWorker.getUser().setPassword(mw.getUser().getPassword());
			medWorker.getUser().setEmail(medWorker.getUser().getEmail());
			medWorker.setClinic(medWorker.getClinic());
			medWorker.setEndHr(medWorker.getEndHr());
			medWorker.setId(medWorker.getId());
			medWorker.setRating(medWorker.getRating());
			medWorker.setStartHr(medWorker.getStartHr());
			medWorker.setType(medWorker.getType());
			medWorker = medicalWorkerService.update(medWorker);
			if(medWorker == null) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}else
				return new ResponseEntity<>(medWorker, HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); 
		*/
		
		MedicalWorker med = medicalWorkerService.findOneById((long) 1);
		med.getUser().setName(mw.getUser().getName());
		med.getUser().setSurname(mw.getUser().getSurname());
		med.getUser().setPassword(mw.getUser().getPassword());
		med = medicalWorkerService.update(med);
		return new ResponseEntity<>(med, HttpStatus.OK);
	}
	
}
