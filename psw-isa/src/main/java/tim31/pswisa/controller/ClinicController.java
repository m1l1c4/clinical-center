package tim31.pswisa.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
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
	
	@GetMapping(value="/searchClinic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Clinic>> searchClinics(@RequestBody String[] params)
	{
		List<Clinic> ret = clinicService.searchClinics(params);
		if (ret == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(ret, HttpStatus.OK);
	} 
	
}
