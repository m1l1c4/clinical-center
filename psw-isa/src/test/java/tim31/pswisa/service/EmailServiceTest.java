package tim31.pswisa.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.UserConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {
/*
	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

	@Test
	public void testSend() {
		String body = "We announce you that you successfully booked medical appointment for"
				+ CheckupConstants.CHECKUP_DATE;
		GreenMailUtil.sendTextEmailTest("pswisa.tim31.2019@gmail.com", "pswisa.tim31.2019@gmail.com", "Booking checkup confirmation", body);
		assertEquals(body, GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}
*/
	
	 @Resource
	    private JavaMailSenderImpl emailSender;
	 
		@Rule
		public final GreenMailRule testSmtp = new GreenMailRule(ServerSetupTest.SMTP);
	 
	   // private GreenMail testSmtp;
	 
	    @Before
	    public void testSmtpInit(){
	        //testSmtp = new GreenMail(ServerSetupTest.SMTP);
	        testSmtp.start();
	 
	        //don't forget to set the test port!
	        emailSender.setPort(587);
	        emailSender.setHost("smtp.gmail.com");
	    }
	 
	    @Test
	    public void testEmail() throws InterruptedException, MessagingException {
	        SimpleMailMessage message = new SimpleMailMessage();
	 
	        message.setFrom("pswisa.tim31.2019@gmail.com");
	        message.setTo("pswisa.tim31.2019@gmail.com");
	        message.setSubject("test subject");
	        message.setText("test message");
	        emailSender.send(message);
	         
	        Message[] messages = testSmtp.getReceivedMessages();
	        assertEquals(1, messages.length);
	        assertEquals("test subject", messages[0].getSubject());
	        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
	        assertEquals("test message", body);
	    }
	 
	    @After
	    public void cleanup(){
	        testSmtp.stop();
	    }
}
