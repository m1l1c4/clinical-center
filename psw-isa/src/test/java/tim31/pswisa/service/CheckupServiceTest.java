package tim31.pswisa.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.constants.DoctorConstants;
import tim31.pswisa.constants.PatientConstants;
import tim31.pswisa.constants.RoomConstants;
import tim31.pswisa.constants.UserConstants;
import tim31.pswisa.dto.CheckupDTO;
import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.Checkup;
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
	private RoomRepository roomRepositoryMocked;
	
	@MockBean
	private MedicalWorkerRepository doctorRepositoryMocked;
	
	@Test
	public void testBookQuickAppFalse() {
		
		Checkup checkupTest = new Checkup();
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest.setDate(CheckupConstants.CHECKUP_DATE);
		checkupTest.setId(CheckupConstants.CHECKUP_ID_FALSE);
		Mockito.when(checkupRepositoryMocked.findOneById(checkupTest.getId())).thenReturn(null);
		boolean ret = checkupService.bookQuickApp(checkupTest.getId(), UserConstants.USER1_EMAIL);
		assertNull(ret);
	}
	
	@Test
	public void testBookQuickApp() {
		Checkup checkupTest = new Checkup();
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest.setDate(CheckupConstants.CHECKUP_DATE);
		checkupTest.setId(CheckupConstants.CHECKUP_ID_FALSE);
		
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
	
	@Test
	public void testUpdate() {
		CheckupDTO checkupTest = new CheckupDTO();	// input
		Checkup tempCheckup = new Checkup();		// testing value
		Checkup retCheckup = new Checkup();
		MedicalWorkerDTO doctorTest = new MedicalWorkerDTO();		
		doctorTest.setId(DoctorConstants.DOCTOR_ID);
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest.setDate(CheckupConstants.CHECKUP_DATE);
		checkupTest.setTime(CheckupConstants.CHECKUP_TIME);
		checkupTest.setId(CheckupConstants.CHECKUP_ID);
		checkupTest.setMedicalWorker(doctorTest);
		Mockito.when(checkupRepositoryMocked.findOneById(checkupTest.getId())).thenReturn(tempCheckup);
		
		RoomDTO testRoom = new RoomDTO();
		Room tempRoom = new Room();
		testRoom.setName(RoomConstants.ROOM_NAME);;
		testRoom.setNumber(RoomConstants.ROOM_NUMBER);
		testRoom.setTypeRoom(RoomConstants.ROOM_TYPE);
		testRoom.setId(RoomConstants.ROOM_ID);
		checkupTest.setRoom(testRoom);
		
        Mockito.when(roomRepositoryMocked.findOneById(checkupTest.getRoom().getId())).thenReturn(tempRoom);
		
        List<Checkup> allInRoomTest = new ArrayList<Checkup>();		
		Mockito.when(checkupRepositoryMocked.findAllByRoomIdAndTimeAndDate(tempRoom.getId(),
				checkupTest.getTime(), checkupTest.getDate())).thenReturn(allInRoomTest);
		
		tempCheckup.setDate(checkupTest.getDate());
		tempCheckup.setTime(checkupTest.getTime());
		tempCheckup.setRoom(tempRoom);
		tempCheckup.setScheduled(true);
		tempCheckup.setDoctors(new HashSet<MedicalWorker>());
		
		MedicalWorker doctorToSet = new MedicalWorker();		
		Mockito.when(doctorRepositoryMocked.findOneById(checkupTest.getMedicalWorker().getId()))
			   .thenReturn(doctorToSet);
		
		tempCheckup.getDoctors().add(doctorToSet);
		
        Mockito.when(checkupRepositoryMocked.save(tempCheckup)).thenReturn(tempCheckup);

		try {
			retCheckup = checkupService.update(checkupTest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(retCheckup);
		
	}
	
	
}
