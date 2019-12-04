package tim31.pswisa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;
import tim31.pswisa.model.UserTokenState;
import tim31.pswisa.repository.CCAdminRepository;
import tim31.pswisa.repository.ClinicAdministratorRepository;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.security.auth.JwtAuthenticationRequest;
import tim31.pswisa.service.LoggingService;
import tim31.pswisa.service.UserService;

@RestController
public class LoggingController {

	@Autowired
	private LoggingService service;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;

	@Autowired
	private CCAdminRepository ccAdminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePassword(@RequestBody String[] data, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			user.setPassword(passwordEncoder.encode(data[0]));
			user.setFirstLogin(true);
			user = userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> registerUser(@RequestBody Patient p) throws Exception {
		Patient u = service.registerUser(p);

		if (u == null) {
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Patient>(u, HttpStatus.OK);
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserTokenState> loginUser(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		User log = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		log = service.loginUser(log);

		if (log != null) {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));

			// Ubaci username + password u kontext
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Kreiraj token
			User user = (User) authentication.getPrincipal();
			String jwt = tokenUtils.generateToken(user.getUsername());
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
		} else {
			return new ResponseEntity<UserTokenState>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(HttpServletRequest request) {
		String jwt_token = tokenUtils.getToken(request);
		// String token = tok.asText();
		String email = tokenUtils.getUsernameFromToken(jwt_token); // email of logged user
		User user = userService.findOneByEmail(email);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/addAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addMedicalWorker(@RequestBody User u) {
		if (u.getType().equals("ADMINISTRATOR")) {
			ClinicAdministrator worker = new ClinicAdministrator();
			worker.setUser(u);
			worker.getUser().setPassword(passwordEncoder.encode("admin"));
			worker.getUser().setFirstLogin(false);
			worker.getUser().setEnabled(true);
			worker.getUser().setActivated(true);
			worker = clinicAdministratorRepository.save(worker);
			if (worker != null) {
				return new ResponseEntity<>(worker.getUser(), HttpStatus.CREATED);
			}
		} else {
			ClinicalCenterAdministrator worker = new ClinicalCenterAdministrator();
			worker.setUser(u);
			worker.getUser().setPassword(passwordEncoder.encode("admin"));
			worker.getUser().setFirstLogin(false);
			worker.getUser().setEnabled(true);
			worker.getUser().setActivated(true);
			worker = ccAdminRepository.save(worker);
			if (worker != null) {
				return new ResponseEntity<>(worker.getUser(), HttpStatus.CREATED);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

	}

}
