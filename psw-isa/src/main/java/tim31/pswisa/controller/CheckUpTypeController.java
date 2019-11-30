package tim31.pswisa.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	    
	    @PostMapping(value="/addTypes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public ResponseEntity<Set<CheckUpType>> save(@RequestBody Set<CheckUpType> lista, HttpServletRequest request){
	    	
		      String token = tokenUtils.getToken(request);
		      String email = tokenUtils.getUsernameFromToken(token);
	   	      User user = userService.findOneByEmail(email);
	   	      Set<CheckUpType>temp = lista;
	   	      List<CheckUpType>allTypes = checkUpTypeService.findAll();
	   	      Clinic klinika = new Clinic();
	   	      
	   	      // save types in clinic
	   	      if(user!=null) {
	   	    	ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
	   	    	klinika = clinicService.findOneById(clinicAdministrator.getClinic().getId());
	   	    	for(CheckUpType t : klinika.getCheckUpTypes()) {
	   	    		for(CheckUpType c : temp) {
	   	    			if(!c.getName().equals(t.getName())) {
	   	    				CheckUpType cek = new CheckUpType();
					   	    klinika.getCheckUpTypes().add(cek);
					        klinika = clinicService.save(klinika);
	   	    			}
	   	    			else {
	   	    				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if there are types with same name in clinic
	   	    			}
			    	}
	   	    	}
	   	    	
	   	     // save types in all types of clinical center
		   	      for(CheckUpType t : allTypes) {
		   	    	  for(CheckUpType g : lista) {
		   	    		  if(!g.getName().equals(t.getName())) {
		   	    			  CheckUpType cut = new CheckUpType();
		   	    			  cut.setName(g.getName());
		   	    			  cut.getClinics().add(klinika);
		   	    			  checkUpTypeService.save(g);
		   	    		  }
		   	    		  else {
		   	    			 return new ResponseEntity<>(HttpStatus.NOT_FOUND); // if there are types with same name in clinical center
		   	    		  }
		   	    	  }
		   	      }
		   	   return new  ResponseEntity<>(lista,HttpStatus.OK); 
	   	      }    
	   	   return new  ResponseEntity<>(HttpStatus.NOT_FOUND); // if there no administrator
	    }
	
}
