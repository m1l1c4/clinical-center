package tim31.pswisa.service;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.UserConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {

	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

	@Test
	public void testSend() {
		String body = "We announce you that you successfully booked medical appointment for"
				+ CheckupConstants.CHECKUP_DATE;
		GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com", "Booking checkup confirmation", body);
		assertEquals(body, GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
	}

}
