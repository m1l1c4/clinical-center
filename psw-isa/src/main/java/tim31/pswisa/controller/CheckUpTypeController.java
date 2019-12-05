package tim31.pswisa.controller;

import java.util.List;
import java.util.Set;

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

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpTypeService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.RoomService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value = "/checkUpType")
public class CheckUpTypeController {

	
	 @Autowired
	    private ClinicService clinicService;
	   
	    @Autowired
	    private ClinicAdministratorService clinicAdministratorService;
	 
	    @Autowired
	    private UserService userService;
	   
	    
	    @Autowired
	    private CheckUpTypeService checkUpTypeService;
	 
	    @Autowired
	    TokenUtils tokenUtils;
	    
	    // This method delete type from clinic but type won't be deleted from all types in clinical center
	    @PostMapping(value="/deleteType/{name}")
		public ResponseEntity<String> deleteType(@PathVariable String name, HttpServletRequest request){
	    	 String token = tokenUtils.getToken(request);
		     String email = tokenUtils.getUsernameFromToken(token);
		     User user = userService.findOneByEmail(email);
		     if(user!=null) {
		    	 ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
		    	 if(clinicAdministrator != null) {
		                Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
		                Set<CheckUpType>tipovi = clinic.getCheckUpTypes();
		                for(CheckUpType t : tipovi) {
		                	if(t.getName().equals(name)) {
		                		clinic.getCheckUpTypes().remove(t);
		                		clinic = clinicService.save(clinic); // delete type from clinic
		                		CheckUpType temp = checkUpTypeService.findOneByName(name);
		                		temp.getClinics().remove(clinic);
		                		temp = checkUpTypeService.save(temp);
		                		return new ResponseEntity<>("Obrisano", HttpStatus.OK);
		                	}
		                }
		               
		            }
		     }
			return new ResponseEntity<>("Greska", HttpStatus.ALREADY_REPORTED);
		}
	    
	    
	    // This method returs all types in clinic center of administrator who is logged in system
	    @GetMapping(value="/getTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Set<CheckUpType>> getTypes(HttpServletRequest request) {
	        String token = tokenUtils.getToken(request);
	        String email = tokenUtils.getUsernameFromToken(token);
	        User user = userService.findOneByEmail(email);
	        if(user!=null) {
	            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
	            if(clinicAdministrator != null) {
	                Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
	                Set<CheckUpType>lista = clinic.getCheckUpTypes();
	                return new ResponseEntity<>(lista,HttpStatus.OK);
	            }
	            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	    // This method adding new type in all type of clinic, checking if there is a type with same name
	    @PostMapping(value="/addType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<CheckUpType> addType(@RequestBody CheckUpType type, HttpServletRequest request){
	    	
		      String token = tokenUtils.getToken(request);
		      String email = tokenUtils.getUsernameFromToken(token);
	   	      User user = userService.findOneByEmail(email);
	   	      CheckUpType tip = new CheckUpType();
	   	      tip.setName(type.getName());
	   	      List<CheckUpType>allTypes = checkUpTypeService.findAll();
	   	      Clinic klinika = new Clinic();
	   	      
	   	      // save types in clinic
	   	      if(user!=null) {
	   	    	ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
	   	    	klinika = clinicService.findOneById(clinicAdministrator.getClinic().getId());
	   	    	int x = 0;
	   	    	for(CheckUpType t : klinika.getCheckUpTypes()) {
	   	    		if(t.getName().equals(tip.getName())){
	   	    			x = 1;
	   	    		}
	   	    	}
	   	    	if(x == 0) {
	   	    		klinika.getCheckUpTypes().add(tip);
	   	    	}
	   	    
	   	     // save types in all types of clinical center
	   	    	int y = 0;
		   	      for(CheckUpType t : allTypes) {
		   	    		  if(tip.getName().equals(t.getName())) {
		   	    			  y = 1;
		   	    		  }	  
		   	      }
		   	      if(y == 0) {
		   	    	  tip.getClinics().add(klinika);
 	    			  tip = checkUpTypeService.save(tip);
 	    			  klinika.getCheckUpTypes().add(tip);
 	    			 return new  ResponseEntity<>(tip,HttpStatus.OK); 
		   	      }
		   	
	   	      }   
	   	   return new  ResponseEntity<>(tip,HttpStatus.NOT_FOUND); 
	    }
	
}
