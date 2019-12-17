package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Autowired
	private UserService userService;

	@Async
	public void sendAccountConfirmationEmail(String email, String text) throws MailException, InterruptedException {
		System.out.println("Sending email...");
		User u = userService.findOneByEmail(email);
		String path = "http://localhost:3000/activateAccount/" + u.getId();
		
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

}
