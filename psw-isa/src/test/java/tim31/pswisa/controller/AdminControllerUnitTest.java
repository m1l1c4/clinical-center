package tim31.pswisa.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import org.mockito.Mockito.times;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.DoctorConstants;
import tim31.pswisa.constants.RoomConstants;
import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.UserTokenState;
import tim31.pswisa.security.auth.JwtAuthenticationRequest;
import tim31.pswisa.service.CheckUpService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerUnitTest {
	private static final String URL_PREFIX = "/checkup/";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private CheckUpService checkupService;
	
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
		
	}

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testupdate() throws Exception {		
		MedicalWorkerDTO doctorTest = new MedicalWorkerDTO();		
		doctorTest.setId(DoctorConstants.DOCTOR_ID);
		RoomDTO room = new RoomDTO();
		room.setId(RoomConstants.ROOM_ID);
		Checkup checkup = new Checkup();
		checkup.setId(CheckupConstants.CHECKUP_ID);			
		CheckupDTO inputCheckup = new CheckupDTO();
		inputCheckup.setId(checkup.getId());
		inputCheckup.setRoom(room);
		inputCheckup.setTime(CheckupConstants.CHECKUP_TIME);
		inputCheckup.setDate(CheckupConstants.CHECKUP_DATE);
		inputCheckup.setMedicalWorker(doctorTest);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String body = mapper.valueToTree(inputCheckup).toString();		
		Checkup retCheckup = new Checkup();
		
		Mockito.when(checkupService.update(inputCheckup)).thenReturn(retCheckup);
		
		mockMvc.perform(post(URL_PREFIX + "update" ).header("Authorization", accessToken)
				.contentType(contentType)
				.content(body))
				.andExpect(status().isOk());
		
		verify(checkupService, times(1)).update(inputCheckup);
				
	}
}
