package tim31.pswisa.controller;

import java.util.HashSet;
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

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.CheckUpTypeService;
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
	    CheckUpTypeService checkUpTypeService;
	    
	    @Autowired
	    private CheckUpService checkupService;
	    
	    @Autowired
	    private MedicalWorkerService medicalWorkerService;
	    
	    // have to modify just for doctors and to save doctor after adding appointment
	    @PostMapping(value = "/addAppointment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Checkup> addAppointment(@RequestBody Checkup c,  HttpServletRequest request)
	    {
	    	User doctorOne = userService.findOneByEmail(c.getMedicalWorker().getUser().getEmail());
	    	MedicalWorker doctorOne1 = medicalWorkerService.findByUser(doctorOne.getId());
	        Checkup checkup = new Checkup();
	        checkup.setMedicalWorker(doctorOne1);
	        CheckUpType typeC = checkUpTypeService.findOneByName(c.getCheckUpType().getName());
	        checkup.setCheckUpType(typeC);
	        checkup.setPrice(c.getPrice());
	        checkup.setDate(c.getDate());
	        checkup.setTime(c.getTime());
	        checkup.setType(c.getType());
	        checkup.setDuration(1);
	        checkup.setDiscount(0); 
	        checkup.setRoom(c.getRoom());
	        Room room = new Room();
	        Set<Room>rooms = new HashSet<Room>();
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
	                	rooms = clinic.getRooms();
	                	
	        	       Set<Checkup>checkups = new HashSet<Checkup>();
	        	       if(room.getBookedCheckups() != null) {
	        	    	   checkups = room.getBookedCheckups();
	        	    	   for(Checkup pom : checkups) {  // same room and same time of appointment
		                		if(c.getDate().equals(pom.getDate()) && c.getTime().equals(pom.getTime())) {
		                			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		                		}
		                	}
	        	       }
	        	       
	        	       for(Room r : rooms) {
	                		if(r.getNumber() == c.getRoom().getNumber()) {
	                			 checkup.setRoom(roomService.findOneById(r.getId()));
	                			 checkup.getRoom().getBookedCheckups().add(checkup);
	                			 Room pom = roomService.findOneById(r.getId());
	                			 System.out.println(pom.getName());
	                			 checkup.getRoom().setName(pom.getName());
	                			 System.out.println(pom.getId());
	                			 System.out.println(pom.getNumber());
	                		}
	                	}
	                	
	                }
	                
		            room.getBookedCheckups().add(checkup);
		            checkup.setType(c.getType());
		            checkup = checkupService.save(checkup);
		            doctorOne1.getCheckUps().add(checkup);
	                
			        doctorOne1 = medicalWorkerService.update(doctorOne1);
		            
			        
			        return new ResponseEntity<>(checkup, HttpStatus.CREATED);
	            }
		       
	        }
	        return new ResponseEntity<>(checkup, HttpStatus.NOT_FOUND);
	        
	    }

}
