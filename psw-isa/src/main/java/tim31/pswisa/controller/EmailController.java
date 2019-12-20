package tim31.pswisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.Patient;
import tim31.pswisa.service.EmailService;
import tim31.pswisa.service.MedicalRecordService;
import tim31.pswisa.service.PatientService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicalRecordService medicalRecordService;

	@PostMapping(value = "/sendConfirm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendConfirmationEmail(@RequestBody String[] data) {
		String email = data[1];
		String text = data[0];

		try {
			emailService.sendAccountConfirmationEmail(email, text);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>("Email sent", HttpStatus.OK);
	}

	@PostMapping(value = "/activateEmail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> activateAccount(@PathVariable String id) {
		long p = Integer.parseInt(id);
		Patient px = patientService.findOneByUserId(p);
		px.getUser().setActivated(true);
		px = patientService.save(px);
		medicalRecordService.add(px);
		return new ResponseEntity<>(px, HttpStatus.OK);
	}
	
	@PostMapping(value = "/changeDate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> activateAccount(@PathVariable Long id, @RequestBody CheckupDTO checkup) {
		Patient patient = patientService.findOneById(id);
		emailService.sendChangeDate(patient, checkup);
		return new ResponseEntity<>("Email sent", HttpStatus.OK);
	}
}
