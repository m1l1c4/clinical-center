package tim31.pswisa.controller;

import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.RoomService;
import tim31.pswisa.service.UserService;
 

@RestController
public class ClinicController {

	
	@Autowired
	private ClinicService clinicService;
	
	    
    @Autowired
    private MedicalWorkerService medicalWorkerService;
   
    @Autowired
    private ClinicAdministratorService clinicAdministratorService;
 
    @Autowired
    private UserService userService;
   
    @Autowired
    private RoomService roomService;
 
    @Autowired
    TokenUtils tokenUtils;
 
    @PostMapping(value="/updateClinic", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
                    if(c.getName().equals(name1) && c.getId() != nameOfClinic.getId()) {
                        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
                    }
                }
                nameOfClinic.setName(clinic.getName());
                nameOfClinic.setAddress(clinic.getAddress());
                nameOfClinic.setCity(clinic.getCity());
                nameOfClinic.setDescription(clinic.getDescription());
                nameOfClinic.setRooms(clinic.getRooms());
                nameOfClinic = clinicService.save(nameOfClinic);
                if(nameOfClinic != null)
                    return new ResponseEntity<>(nameOfClinic, HttpStatus.OK);
                else
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        clinic.setDescription(c.getDescription());
        clinic.setRooms(c.getRooms());
 
        clinic = clinicService.save(clinic);
        if (clinic == null)
            return new ResponseEntity<>(clinic, HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(clinic, HttpStatus.CREATED);
    }
   
    @GetMapping(value="/getRooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>>getRooms(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = userService.findOneByEmail(email);
        if(user != null) {
            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
            if(clinicAdministrator != null) {
                Clinic clinic = clinicAdministrator.getClinic();
                if(clinic != null) {
                    List<Room>rooms = roomService.findAllByClinicId(clinic.getId());
                    return new ResponseEntity<>(rooms,HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
    @GetMapping(value="/getFreeRooms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Room>>getFreeRooms(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = userService.findOneByEmail(email);
        if(user != null) {
            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
            if(clinicAdministrator != null) {
                Clinic clinic = clinicAdministrator.getClinic();
                if(clinic != null) {
                    List<Room>rooms = roomService.findAllByClinicId(clinic.getId());
                    Set<Room>temp = new HashSet<Room>();
                    for(Room r : rooms) {
                    	temp.add(r);
                    }
                    return new ResponseEntity<>(temp,HttpStatus.OK);  
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
   
    @GetMapping(value="/getDoctors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<MedicalWorker>>getAllMedicalWorkers(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = userService.findOneByEmail(email);
        if(user != null) {
            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
            if(clinicAdministrator != null) {
                Clinic clinic = clinicAdministrator.getClinic();
                if(clinic != null) {
                    Set<MedicalWorker>workers = medicalWorkerService.findAllByClinicId(clinic.getId());
                    return new ResponseEntity<>(workers,HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value="/getAllTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<CheckUpType>>getAllTypes(HttpServletRequest request){
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = userService.findOneByEmail(email);
        if(user != null) {
            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
            if(clinicAdministrator != null) {
                Clinic clinic = clinicAdministrator.getClinic();
                if(clinic != null) {
                   Set<CheckUpType> tmp = clinic.getCheckUpTypes();
                    return new ResponseEntity<>(tmp,HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value="/getClinic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Clinic> getClinic(HttpServletRequest request) {
        String token = tokenUtils.getToken(request);
        String email = tokenUtils.getUsernameFromToken(token);
        User user = userService.findOneByEmail(email);
        if(user!=null) {
            ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
            if(clinicAdministrator != null) {
                Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
                return new ResponseEntity<>(clinic,HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	
	@GetMapping(value="/getClinics", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ClinicDTO>> getAllClinics()
	{
		List<Clinic> clinics = clinicService.findAll();
		List<ClinicDTO> retDto = new ArrayList<ClinicDTO>();
		
		if (clinics == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			for (Clinic clinic : clinics) {
				ClinicDTO cldto = new ClinicDTO(clinic.getId(), clinic.getName() , clinic.getCity() , clinic.getAddress(), clinic.getRating());
				
				for (Checkup ch : clinic.getAvailableAppointments()) {
					cldto.getCheckupTypes().add(ch.getType());
				}
				
				retDto.add(cldto);
			}
			
			return new ResponseEntity<>(retDto,HttpStatus.OK);
		}
		
	}
	
	@PostMapping(value="/searchClinic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> searchClinics(@RequestBody String[] params)
	{
		List<Clinic> ret = clinicService.searchClinics(params);		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	} 
	
	@PostMapping(value="/filterClinic/{p}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> filterClinics(@PathVariable String p , @RequestBody List<Clinic> clinics)
	{
		List<Clinic> ret = new ArrayList<Clinic>();
		ret = clinicService.filterClinics(p, (ArrayList<Clinic>) clinics);
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	} 
	
}


