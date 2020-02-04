package tim31.pswisa.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tim31.pswisa.TestUtil;
import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.RoomConstants;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.UserTokenState;
import tim31.pswisa.security.auth.JwtAuthenticationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {

	private static final String URL_PREFIX = "/checkup/";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private String accessToken;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void login() {
		ResponseEntity<UserTokenState> responseEntity = restTemplate.postForEntity("/login",
				new JwtAuthenticationRequest("admin@gmail.com", "sifra1"), UserTokenState.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
		
		// TREBA I PROMENA LOZINKE
	}

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testupdate() throws Exception {
		Clinic clinic = new Clinic();
		Room room = new Room(RoomConstants.ROOM_ID, RoomConstants.ROOM_NAME, 
						RoomConstants.ROOM_TYPE, RoomConstants.ROOM_IS_FREE, 
						RoomConstants.ROOM_NUMBER, clinic, RoomConstants.ROOM_FRDATE);
		Checkup checkup = new Checkup(CheckupConstants.CHECKUP_DISCOUNT, 
							CheckupConstants.CHECKUP_SCHEDULED, CheckupConstants.CHECKUP_DATE,
							CheckupConstants.CHECKUP_TIME, CheckupConstants.CHECKUP_CHTYPE, CheckupConstants.CHECKUP_DURATION,
							CheckupConstants.CHECKUP_PRICE, room, CheckupConstants.CHECKUP_FINISHED );
		
		String json = TestUtil.json(checkup); 
		mockMvc.perform(post(URL_PREFIX + "update" ).header("Authorization", accessToken)
				.contentType(contentType)
				.content(json))
				.andExpect(status().isOk())				
				.andExpect(jsonPath("$.id").value(22));
				
	}
	
	@Test
	public void testupdateFalse() throws Exception {
		mockMvc.perform(post(URL_PREFIX + "update" ).header("Authorization", accessToken))
				.andExpect(status().isExpectationFailed());
	}
}