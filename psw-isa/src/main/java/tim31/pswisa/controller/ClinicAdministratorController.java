package tim31.pswisa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.AbsenceDTO;
import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.ClinicAdministratorDTO;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.EmailService;
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

	/**
	 * This method servers for getting clinic administrator to access to his profile
	 * 
	 * @param request -
	 * @return - (ClinicAdministratorDTO) This method returns clinic administrator
	 * 
	 */
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

	/**
	 * This method servers for getting all requests for vacation from medical worker
	 * 
	 * @param request -
	 * @return - (List<AbsenceDTO>) This method returns all requests for vacation
	 *         from medical worker to clinic administrator who is logged
	 * 
	 */
	@GetMapping(value = "/getRequestForVacation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AbsenceDTO>> getRequestForVacationController(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			List<AbsenceDTO> returnValue = clinicAdministratorService.getRequestForVacation(user);
			if (returnValue == null) {
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			} else {
				return new ResponseEntity<>(returnValue, HttpStatus.ALREADY_REPORTED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * This method servers for implement acceptance or rejection for request for
	 * vacation and sending email to medical worker about acceptance or rejection
	 * 
	 * @param request -
	 * @param a       - absence that have to be accepted or rejected
	 * @param reason  - reason for rejection or 'ok' if it is acceptance
	 * @return - (String) This method returns 'ok' or 'Greska' like information
	 *         about success of this method
	 * 
	 */
	@PostMapping(value = "/requestVacation/{reason}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> requestVacationController(HttpServletRequest request, @RequestBody AbsenceDTO a,
			@PathVariable String reason) throws MailException, InterruptedException {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			clinicAdministratorService.sendEmailToMw(user, a, reason);
			return new ResponseEntity<>("Ok", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Greska", HttpStatus.BAD_GATEWAY);
		}

	}

	/**
	 * This method servers for getting all request for booking room for operation or
	 * appointment sent by doctor to administrator
	 * 
	 * @param request -
	 * @return - (List<CheckupDTO>) This method returns list of request of check-up
	 *         that have to be booked by administrator
	 * 
	 */
	@GetMapping(value = "/requestsForRoom", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CheckupDTO>> requestForRoomController(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		List<CheckupDTO> returnValue = checkupService.findAllByScheduled(false, user);
		return new ResponseEntity<>(returnValue, HttpStatus.OK);
	}

	/**
	 * This method servers for updating clinic administrator
	 * 
	 * @param ca      - new information about clinic administrator
	 * @param request -
	 * @return - (ClinicAdministratorDTO) This method returns updated clinic
	 *         administrator
	 * 
	 */
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

	
	@Scheduled(cron = "${scheduleRoom.cron}")
	public void scheuldeRoom () {
		clinicAdministratorService.scheuldeRoomsEndDay();
		System.out.println("POZVANA FUNKCIJA" + "        " + System.currentTimeMillis());
	}
	
	
	
	
	
	
	
	
	
	
	
}