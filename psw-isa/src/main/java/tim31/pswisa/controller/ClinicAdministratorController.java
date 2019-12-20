package tim31.pswisa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.ClinicAdministratorDTO;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.UserService;

@RestController
public class ClinicAdministratorController {

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private CheckUpService checkupService;

	// This method returns administrator who is using application at the moment
	@GetMapping(value = "/getAdministrator", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdministratorDTO> getAdministrator(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/requestsForRoom", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CheckupDTO>> requestForRoomController(HttpServletRequest request){
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		List<CheckupDTO> returnValue = checkupService.findAllByScheduled(false);
		return new ResponseEntity<>(returnValue, HttpStatus.OK);
	}

	// This method updates administrator who is using application at the moment
	@PostMapping(value = "/updateAdministrator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ClinicAdministratorDTO> updateAdministratorController(@RequestBody ClinicAdministratorDTO ca,
			HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				clinicAdministrator = clinicAdministratorService.updateAdministrator(clinicAdministrator, ca);
				return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}