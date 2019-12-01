package tim31.pswisa.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.RoomService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value = "/checkup")
public class CheckupController {
	
	  @Autowired
	    private ClinicService clinicService;
	   
	    @Autowired
	    private ClinicAdministratorService clinicAdministratorService;
	 
	    @Autowired
	    private UserService userService;
	   
	    @Autowired
	    private RoomService roomService;
	 
	    @Autowired
	    TokenUtils tokenUtils;
	    
	    @Autowired
	    private CheckUpService checkupService;
	    
	    @Autowired
	    private MedicalWorkerService medicalWorkerService;
	    
	    // have to modify for doctors who are not available but first I have to find doctor from frontend so I have to send email of doctor to identify
	    @PostMapping(value = "/addAppointment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Checkup> addAppointment(@RequestBody Checkup c, HttpServletRequest request)
	    {
	    	
	    	User doctorOne = userService.findOneByEmail(c.getMedicalworker().getUser().getEmail());
	    	MedicalWorker doctorOne1 = medicalWorkerService.findByUser(doctorOne.getId());
	        Checkup checkup = new Checkup();
	        checkup.setMedicalworker(doctorOne1);
	        checkup.setCheckUpType(c.getCheckUpType());
	        checkup.setPrice(c.getPrice());
	        checkup.setDate(c.getDate());
	        checkup.setTime(c.getTime());
	        checkup.setDuration(1);
	        checkup.setDiscount(0);
	        checkup.setRoom(c.getRoom());
	        String token = tokenUtils.getToken(request);
	        Clinic clinic = new Clinic();
	        String email = tokenUtils.getUsernameFromToken(token);
	        User user = userService.findOneByEmail(email);
	        if(user != null) {
	            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
	            if(clinicAdministrator != null) {
	                clinic = clinicAdministrator.getClinic();
	                if(clinic != null) {
	                	checkup.setClinic(clinic);
	                	Set<Checkup>pregledi = clinic.getAvailableAppointments();
	                	for(Checkup pom : pregledi) {
	                		if(c.getDate().equals(pom.getDate()) && c.getTime().equals(pom.getTime())) {
	                			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
	                		}
	                	}
	                }
	            }
	        }

	        checkup = checkupService.save(checkup);
	        
	        return new ResponseEntity<>(checkup, HttpStatus.CREATED);
	    }

}
