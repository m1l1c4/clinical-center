package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import tim31.pswisa.model.User;
import tim31.pswisa.service.LoggingService;


@RestController
public class LoggingController {
	
	@Autowired
	public LoggingService service;
	
	@PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> registerUser(@RequestBody User user) throws Exception
	{
		User u = service.registerUser(user);
		
		if (u == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		// postalji mejl adminu
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
}
