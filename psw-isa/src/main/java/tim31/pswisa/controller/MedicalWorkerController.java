package tim31.pswisa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.UserService;

@RestController
public class MedicalWorkerController {

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils tokenUtils;
	// method returns medical worker by email

	// This method returns medical worker for update
	@GetMapping(value = "/getMedicalWorker", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> getMedicalWorker(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			MedicalWorker medicalWorker = medicalWorkerService.findByUser(user.getId());
			if (medicalWorker != null) {
				return new ResponseEntity<>(new MedicalWorkerDTO(medicalWorker), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// This method updates medical worker who sends request for that
	@PostMapping(value = "/updateMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> updateMedicalWorkerController(@RequestBody MedicalWorkerDTO mw) {
		User user = userService.findOneByEmail(mw.getUser().getEmail());
		if (user != null) {
			MedicalWorker medWorker = medicalWorkerService.findByUser(user.getId());
			if (medWorker != null) {
				medWorker = medicalWorkerService.updateMedicalWorker(medWorker, mw);
				return new ResponseEntity<>(new MedicalWorkerDTO(medWorker), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping(value = "/addMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorkerDTO> addMedicalWorker(@RequestBody MedicalWorkerDTO mw) {
		MedicalWorker medicalWorker = medicalWorkerService.save(mw);
		if (medicalWorker != null) {
			return new ResponseEntity<>(new MedicalWorkerDTO(medicalWorker), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

	}

}
