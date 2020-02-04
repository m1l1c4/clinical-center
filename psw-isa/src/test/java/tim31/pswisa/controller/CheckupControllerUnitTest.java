package tim31.pswisa.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.UserConstants;
import tim31.pswisa.model.UserTokenState;
import tim31.pswisa.security.auth.JwtAuthenticationRequest;
import tim31.pswisa.service.CheckUpService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CheckupControllerUnitTest {

	private static final String URL_PREFIX = "/checkup/";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private String accessToken;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private CheckUpService checkupServiceMocked;

	@Before
	public void login() {
		ResponseEntity<UserTokenState> responseEntity = restTemplate.postForEntity("/login",
				new JwtAuthenticationRequest("pacijent@gmail.com", "sifra1"), UserTokenState.class);
		accessToken = "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@PostConstruct
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testbookQuickApp() throws Exception {
		Mockito.when(checkupServiceMocked.bookQuickApp(CheckupConstants.CHECKUP_ID, UserConstants.USER2_EMAIL))
				.thenReturn(true);
		mockMvc.perform(post(URL_PREFIX + "bookQuickApp/" + CheckupConstants.CHECKUP_ID)
				.header("Authorization", accessToken).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Uspesno zakazivanje pregleda"));
		verify(checkupServiceMocked, times(1)).bookQuickApp(CheckupConstants.CHECKUP_ID, UserConstants.USER2_EMAIL);
	}

	@Test
	public void testbookQuickAppFalse() throws Exception {
		Mockito.when(checkupServiceMocked.bookQuickApp(CheckupConstants.CHECKUP_ID_FALSE, UserConstants.USER2_EMAIL))
				.thenReturn(false);
		mockMvc.perform(post(URL_PREFIX + "bookQuickApp/" + CheckupConstants.CHECKUP_ID_FALSE).header("Authorization",
				accessToken)).andExpect(status().isExpectationFailed());
		verify(checkupServiceMocked, times(1)).bookQuickApp(CheckupConstants.CHECKUP_ID_FALSE, UserConstants.USER2_EMAIL);
	}
}
