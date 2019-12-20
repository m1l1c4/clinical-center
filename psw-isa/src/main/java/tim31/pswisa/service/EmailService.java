package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicalWorkerService medicalWorkerService;

	@Async
	public void sendAccountConfirmationEmail(String email, String text) throws MailException, InterruptedException {
		System.out.println("Sending email...");
		User u = userService.findOneByEmail(email);
		String path = "http://localhost:3000/activateAccount/" + u.getId();
		// String path = "<html><a href='" + varifyUrl + "'>" + varifyUrl + "</a></html>
		// ";

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("pswisa.tim31.2019@gmail.com");
		msg.setFrom(env.getProperty("spring.mail.username"));
		msg.setSubject("Account confirmation");
		if (text.equals("approved"))
			msg.setText("Please confirm your Clinical center account by clicking on link below. \n\n" + path
					+ "\n\nAdministration team");
		else
			msg.setText(
					"Your request for registration to Clinical center can't be approved. The reason for rejection is:\n\n"
							+ text + "\n\nAdministration team");
		javaMailSender.send(msg);

		System.out.println("Email sent.");
	}

	@Async
	public void sendNotificationToAmin(Clinic clinic, MedicalWorker medWorker, Patient patient)
			throws MailException, InterruptedException {
		// Set<ClinicAdministrator> clinicAdministrators = clinic.getClAdmins();
		SimpleMailMessage msg = new SimpleMailMessage();
		// for(ClinicAdministrator ca : clinicAdministrators) {
		System.out.println("Sending email...");
		msg.setTo("pswisa.tim31.2019@gmail.com");
		msg.setFrom(env.getProperty("spring.mail.username"));
		msg.setSubject("New request for operation or appointment");
		msg.setText("There is new request for operation or appointment by doctor " + medWorker.getUser().getName() + " "
				+ medWorker.getUser().getSurname() + " for patient " + patient.getUser().getName() + " "
				+ patient.getUser().getSurname());
		javaMailSender.send(msg);
		System.out.println("Email sent.");
		// }

	}

	@Async
	public void sendChangeDate(Patient patient, CheckupDTO c) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("pswisa.tim31.2019@gmail.com");
		msg.setFrom(env.getProperty("spring.mail.username"));
		msg.setSubject("Account confirmation");
		msg.setText("Your operation was resheduled for " + c.getDate().toString() + " " + c.getTime() + " in the room ."
				+ c.getRoom().getName() + " number: " + c.getRoom().getNumber());
		System.out.println("Email sent.");
	}
	
	@Async
	public void notifyDoctor(Long id, Checkup c) {
		MedicalWorker medicalWorker = medicalWorkerService.findOneById(id);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("pswisa.tim31.2019@gmail.com");
		msg.setFrom(env.getProperty("spring.mail.username"));
		msg.setSubject("Account confirmation");
		msg.setText("You must attend operatin on " + c.getDate().toString() + " " + c.getTime() + " in the room ."
				+ c.getRoom().getName() + " number: " + c.getRoom().getNumber());
		System.out.println("Email sent.");
	}

}
