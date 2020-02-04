package tim31.pswisa.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.ClinicConstants;
import tim31.pswisa.constants.PatientConstants;
import tim31.pswisa.constants.RoomConstants;
import tim31.pswisa.constants.UserConstants;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.CheckUpRepository;
import tim31.pswisa.repository.MedicalWorkerRepository;
import tim31.pswisa.repository.PatientRepository;
import tim31.pswisa.repository.RoomRepository;
import tim31.pswisa.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CheckupServiceTest {

	@Autowired
	private CheckUpService checkupService;

	@MockBean
	private CheckUpRepository checkupRepositoryMocked;

	@MockBean
	private UserRepository userRepositoryMocked;

	@MockBean
	private PatientRepository patientRepositoryMocked;

	@MockBean
	private MedicalWorkerRepository doctorRepositoryMocked;

	@MockBean
	private RoomRepository roomRepositoryMocked;

	@Test
	public void testBookQuickAppFalse() {

		Checkup checkupTest = new Checkup();
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest.setDate(CheckupConstants.CHECKUP_DATE);
		checkupTest.setId(CheckupConstants.CHECKUP_ID_FALSE);
		Mockito.when(checkupRepositoryMocked.findOneById(checkupTest.getId())).thenReturn(null);
		boolean ret = checkupService.bookQuickApp(checkupTest.getId(), UserConstants.USER1_EMAIL);
		assertFalse(ret);
	}

	@Test
	public void testBookQuickApp() {
		Checkup checkupTest = new Checkup();
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest.setDate(CheckupConstants.CHECKUP_DATE);
		checkupTest.setId(CheckupConstants.CHECKUP_ID);

		User userTest = new User(UserConstants.USER1_EMAIL, UserConstants.USER1_PASS, UserConstants.USER1_NAME,
				UserConstants.USER1_SURNAME);
		MedicalWorker doctorTest = new MedicalWorker();
		doctorTest.setUser(userTest);
		checkupTest.setDoctors(new HashSet<>());
		checkupTest.getDoctors().add(doctorTest);
		Clinic clinicTest = new Clinic();
		clinicTest.setId(ClinicConstants.CLINIC_ID);
		clinicTest.setName(ClinicConstants.CLINIC_NAME);
		clinicTest.setCity(ClinicConstants.CLINIC_CITY);
		clinicTest.setAddress(ClinicConstants.CLINIC_ADDRESS);
		Room roomTest = new Room(RoomConstants.ROOM_ID, RoomConstants.ROOM_NAME, RoomConstants.ROOM_TYPE,
				RoomConstants.ROOM_IS_FREE, RoomConstants.ROOM_NUMBER, clinicTest, RoomConstants.ROOM_FRDATE);
		checkupTest.setRoom(roomTest);
		checkupTest.setClinic(clinicTest);
		Mockito.when(checkupRepositoryMocked.findOneById(checkupTest.getId())).thenReturn(checkupTest);

		User testUser = new User();
		testUser.setEmail(UserConstants.USER1_EMAIL);
		testUser.setName(UserConstants.USER1_NAME);
		testUser.setSurname(UserConstants.USER1_SURNAME);
		testUser.setPassword(UserConstants.USER1_PASS);
		testUser.setId(3L);

		Mockito.when(userRepositoryMocked.findOneByEmail(testUser.getEmail())).thenReturn(testUser);

		Patient testPatient = new Patient();
		testPatient.setUser(testUser);
		testPatient.setJbo(PatientConstants.PATIENT1_JBO);
		testPatient.setPhoneNumber(PatientConstants.PATIENT1_PHONE);
		testPatient.setCity(PatientConstants.PATIENT1_CITY);
		testPatient.setState(PatientConstants.PATIENT1_STATE);
		testPatient.setAddress(PatientConstants.PATIENT1_ADDRESS);
		testPatient.setProcessed(PatientConstants.PATIENT1_PROCESSED);
		testPatient.setId(1L);

		Mockito.when(patientRepositoryMocked.findByUserId(testPatient.getUser().getId())).thenReturn(testPatient);

		checkupTest.setPatient(testPatient);
		Mockito.when(checkupRepositoryMocked.save(checkupTest)).thenReturn(checkupTest);

		boolean ret = checkupService.bookQuickApp(checkupTest.getId(), UserConstants.USER1_EMAIL);
		assertTrue(ret);
	}
}
