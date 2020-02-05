package tim31.pswisa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import tim31.pswisa.constants.CheckupTypeConstants;
import tim31.pswisa.constants.ClinicConstants;
import tim31.pswisa.constants.DoctorConstants;
import tim31.pswisa.constants.UserConstants;
import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.repository.CheckUpTypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClinicServiceTest {

	@Autowired
	private ClinicService clinicService;

	@MockBean
	private CheckUpTypeRepository checkupTypeRepository;

	@Test
	public void testFilterClinicsOk() {
		Clinic clinic1 = new Clinic(ClinicConstants.ID_C_1, ClinicConstants.NAZIV_1, ClinicConstants.GRAD_1,
				ClinicConstants.DRZAVA_1, ClinicConstants.ADRESA_1, ClinicConstants.RAITING_1, ClinicConstants.OPIS_1);

		Clinic clinic2 = new Clinic(ClinicConstants.ID_C_2, ClinicConstants.NAZIV_2, ClinicConstants.GRAD_2,
				ClinicConstants.DRZAVA_2, ClinicConstants.ADRESA_2, ClinicConstants.RAITING_2, ClinicConstants.OPIS_2);

		String raiting = ClinicConstants.RAITING_OK_1;
		String raiting2 = ClinicConstants.RAITING_OK_2;
		String raiting3 = ClinicConstants.RAITING_OK_3;
		String raiting4 = ClinicConstants.RAITING_OK_4;

		ArrayList<Clinic> clinics = new ArrayList<Clinic>();
		clinics.add(clinic1);
		clinics.add(clinic2);
		assertEquals(1, clinicService.filterClinics(raiting, clinics).size());
		assertEquals(2, clinicService.filterClinics(raiting2, clinics).size());
		assertEquals(1, clinicService.filterClinics(raiting3, clinics).size());
		assertEquals(0, clinicService.filterClinics(raiting4, clinics).size());

	}

	@Test
	public void testSearchClinicsFalseType() {
		CheckUpType srchType = new CheckUpType();
		srchType.setClinics(new HashSet<Clinic>());
		srchType.setName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		srchType.setId(CheckupTypeConstants.CHECK_UP_TYPE_ID);
		srchType.setTypePrice(100);
		String[] params = { CheckupTypeConstants.CHECK_UP_TYPE_NAME_FALSE, DoctorConstants.DATE_OK };
		Mockito.when(checkupTypeRepository.findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME_FALSE))
				.thenReturn(srchType);
		List<ClinicDTO> rezultat = clinicService.searchClinics(params);
		// verify(checkupTypeRepository,
		// times(1)).findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		assertNull(rezultat);
	}

	@Test
	public void testSearchClinicsFalseNull() {
		CheckUpType srchType = new CheckUpType();
		srchType.setClinics(new HashSet<Clinic>());
		srchType.setName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		srchType.setId(CheckupTypeConstants.CHECK_UP_TYPE_ID);
		srchType.setTypePrice(100);
		String[] params = { "NE POSTOJI", DoctorConstants.DATE_OK };
		Mockito.when(checkupTypeRepository.findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME)).thenReturn(null);
		List<ClinicDTO> rezultat = clinicService.searchClinics(params);
		// verify(checkupTypeRepository,
		// times(1)).findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		assertNull(rezultat);
	}

	@Test
	public void testSearchClinicsFalseDate() {
		CheckUpType srchType = new CheckUpType();
		srchType.setClinics(new HashSet<Clinic>());
		srchType.setName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		srchType.setId(CheckupTypeConstants.CHECK_UP_TYPE_ID);
		srchType.setTypePrice(100);
		String[] params = { CheckupTypeConstants.CHECK_UP_TYPE_NAME, CheckupTypeConstants.CHECK_UP_TYPE_NAME_FALSE };
		Mockito.when(checkupTypeRepository.findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME)).thenReturn(srchType);
		List<ClinicDTO> rezultat = clinicService.searchClinics(params);
		assertNull(rezultat);
	}

	@Test
	public void testSearchClinics() {
		Clinic clinic1 = new Clinic(ClinicConstants.ID_C_1, ClinicConstants.NAZIV_1, ClinicConstants.GRAD_1,
				ClinicConstants.DRZAVA_1, ClinicConstants.ADRESA_1, ClinicConstants.RAITING_1, ClinicConstants.OPIS_1);

		Clinic clinic2 = new Clinic(ClinicConstants.ID_C_2, ClinicConstants.NAZIV_2, ClinicConstants.GRAD_1,
				ClinicConstants.DRZAVA_1, ClinicConstants.ADRESA_1, ClinicConstants.RAITING_1, ClinicConstants.OPIS_1);

		List<ClinicDTO> clinics = new ArrayList<ClinicDTO>();
		ClinicDTO clinic11 = new ClinicDTO(clinic1);
		ClinicDTO clinic12 = new ClinicDTO(clinic2);
		clinics.add(clinic11);
		clinics.add(clinic12);
		clinic1.setMedicalStuff(new HashSet<MedicalWorker>());
		clinic2.setMedicalStuff(new HashSet<MedicalWorker>());

		User user1 = new User();
		user1.setName(UserConstants.IME_1);
		user1.setSurname(UserConstants.PREZIME_1);
		user1.setType(UserConstants.TIP);

		User user2 = new User();
		user2.setName(UserConstants.IME_2);
		user2.setSurname(UserConstants.PREZIME_2);
		user2.setType(UserConstants.TIP);

		MedicalWorker mw1 = new MedicalWorker(DoctorConstants.DOCTOR_ID_1, user1, clinic1, DoctorConstants.TIP_D_1);
		clinic1.getMedicalStuff().add(mw1);

		MedicalWorker mw2 = new MedicalWorker(DoctorConstants.DOCTOR_ID_2, user2, clinic2, DoctorConstants.TIP_D_1);
		clinic2.getMedicalStuff().add(mw2);

		CheckUpType srchType = new CheckUpType();
		srchType.setClinics(new HashSet<Clinic>());
		srchType.getClinics().add(clinic1);
		srchType.getClinics().add(clinic2);
		srchType.setName(CheckupTypeConstants.CHECK_UP_TYPE_NAME);
		srchType.setId(CheckupTypeConstants.CHECK_UP_TYPE_ID);
		srchType.setTypePrice(100);

		Mockito.when(checkupTypeRepository.findOneByName(CheckupTypeConstants.CHECK_UP_TYPE_NAME)).thenReturn(srchType);

		String[] params1 = { CheckupTypeConstants.CHECK_UP_TYPE_NAME, CheckupConstants.LOCAL_DATE_1.toString() };
		List<ClinicDTO> rezultat = clinicService.searchClinics(params1);

		assertEquals(clinics.size(), rezultat.size());

	}

	/*
	 * Clinic clinic = new Clinic(ClinicConstants.ID_C_1, ClinicConstants.NAZIV_1,
	 * ClinicConstants.GRAD_1, ClinicConstants.DRZAVA_1, ClinicConstants.ADRESA_1,
	 * ClinicConstants.RAITING_1, ClinicConstants.OPIS_1);
	 * 
	 * Room room = new Room(RoomConstants.ID_R_1, RoomConstants.NAME_1,
	 * RoomConstants.TIP_1, RoomConstants.FREE_1, RoomConstants.NUMBER_1, clinic,
	 * RoomConstants.FIRST_DATE_1);
	 * 
	 * Checkup cek1 = new Checkup(CheckupConstants.DISCOUNT_1,
	 * CheckupConstants.SCHEDULED_1, CheckupConstants.LOCAL_DATE_1,
	 * CheckupConstants.TIME_1, CheckupConstants.TIP_1, CheckupConstants.DURATION_1,
	 * CheckupConstants.PRICE_1, room, CheckupConstants.FINISHED_1);
	 * 
	 * Checkup cek2 = new Checkup(CheckupConstants.DISCOUNT_2,
	 * CheckupConstants.SCHEDULED_2, CheckupConstants.LOCAL_DATE_2,
	 * CheckupConstants.TIME_2, CheckupConstants.TIP_2, CheckupConstants.DURATION_1,
	 * CheckupConstants.PRICE_2, room, CheckupConstants.FINISHED_2);
	 * 
	 * Checkup cek3 = new Checkup(CheckupConstants.DISCOUNT_3,
	 * CheckupConstants.SCHEDULED_3, CheckupConstants.LOCAL_DATE_3,
	 * CheckupConstants.TIME_3, CheckupConstants.TIP_3, CheckupConstants.DURATION_1,
	 * CheckupConstants.PRICE_3, room, CheckupConstants.FINISHED_3);
	 * 
	 * 
	 * 
	 * }
	 */

}
