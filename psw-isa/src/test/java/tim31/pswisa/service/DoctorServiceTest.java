package tim31.pswisa.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import tim31.pswisa.constants.DoctorConstants;
import tim31.pswisa.constants.RoomConstants;
import tim31.pswisa.constants.UserConstants;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.repository.MedicalWorkerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class DoctorServiceTest {
	@MockBean
	private MedicalWorkerRepository doctorRepositoryMocked;
	
	@Autowired
	private MedicalWorkerService doctorService;
	
	@Test
	public void findOneByIdTest() {
		MedicalWorker doctorTest = new MedicalWorker();
		doctorTest.setId(DoctorConstants.DOCTOR_ID);				
		Mockito.when(doctorRepositoryMocked.findOneById(doctorTest.getId())).thenReturn(doctorTest);
		MedicalWorker doctor = doctorService.findOneById(DoctorConstants.DOCTOR_ID);
		assertEquals(doctor.getId(), DoctorConstants.DOCTOR_ID);
		assertEquals(doctor.getUser().getName(), UserConstants.DOKTOR_NAME);
		assertEquals(doctor.getUser().getSurname(), UserConstants.DOKTOR_SURNAME);
		assertEquals(doctor.getUser().getEmail(), UserConstants.DOCTOR_EMAIL);
		assertEquals(doctor.getPhone(), DoctorConstants.DOCTOR_PHONE);
		
	}
}
