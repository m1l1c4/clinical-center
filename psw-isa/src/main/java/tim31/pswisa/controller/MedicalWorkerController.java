package tim31.pswisa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.MedicalWorkerService;
import tim31.pswisa.service.UserService;

@RestController
public class MedicalWorkerController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils tokenUtils;
	// method returns medical worker by email

	@GetMapping(value = "/getMedicalWorker", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorker> getMedicalWorker(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			MedicalWorker medicalWorker = medicalWorkerService.findByUser(user.getId());
			if (medicalWorker != null) {
				return new ResponseEntity<>(medicalWorker, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// method updates medical worker by email, parameter of this method is
	// MedicalWorker object

	@PostMapping(value = "/updateMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorker> updateMedicalWorker(@RequestBody MedicalWorker mw) {
		User user = userService.findOneByEmail(mw.getUser().getEmail());
		if (user != null) {
			MedicalWorker medWorker = medicalWorkerService.findByUser(user.getId());
			medWorker.getUser().setName(mw.getUser().getName());
			medWorker.getUser().setSurname(mw.getUser().getSurname());
			medWorker.setPhone(mw.getPhone());
			medWorker.getUser().setPassword(mw.getUser().getPassword());
			medWorker = medicalWorkerService.update(medWorker);
			if (medWorker != null) {
				return new ResponseEntity<>(medWorker, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@PostMapping(value = "/addMedicalWorker", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MedicalWorker> addMedicalWorker(@RequestBody MedicalWorker mw) {
		MedicalWorker medWorker = new MedicalWorker();
		mw.getUser().setPassword(passwordEncoder.encode("sifra123"));
		mw.getUser().setFirstLogin(false);
		mw.getUser().setEnabled(true);
		mw.getUser().setActivated(true);
		medWorker = medicalWorkerService.save(mw);
		if (medWorker != null) {
			return new ResponseEntity<>(medWorker, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

	}

}
