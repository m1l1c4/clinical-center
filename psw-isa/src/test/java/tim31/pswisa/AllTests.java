package tim31.pswisa;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.controller.AdminControllerTest;
import tim31.pswisa.controller.AdminControllerUnitTest;
import tim31.pswisa.controller.CheckupControllerTest;
import tim31.pswisa.controller.CheckupControllerUnitTest;
import tim31.pswisa.controller.CheckupRequestControllerTest;
import tim31.pswisa.controller.CheckupRequestControllerUnitTest;
import tim31.pswisa.controller.ClinicControllerTest;
import tim31.pswisa.controller.ClinicControllerUnit;
import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.CheckupTypeRepositoryTest;
import tim31.pswisa.repository.ClAdminRepositoryTest;
import tim31.pswisa.repository.ClinicRepositoryTest;
import tim31.pswisa.repository.DoctorRepositoryTest;
import tim31.pswisa.repository.PatientRepositoryTest;
import tim31.pswisa.repository.RoomRepositoryTest;
import tim31.pswisa.repository.UserRepositoryTest;
import tim31.pswisa.service.CheckUpTypeServiceTest;
import tim31.pswisa.service.CheckupServiceIntegracioniTest;
import tim31.pswisa.service.CheckupServiceUnitTest;
import tim31.pswisa.service.ClinicAdminServiceTest;
import tim31.pswisa.service.ClinicServiceTest;
import tim31.pswisa.service.ClinicServiceTestIntegration;
import tim31.pswisa.service.DoctorServiceTest;
import tim31.pswisa.service.EmailServiceTest;
import tim31.pswisa.service.PatientServiceTest;
import tim31.pswisa.service.RoomServiceTest;
import tim31.pswisa.service.UserServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	AdminControllerTest.class,
	AdminControllerUnitTest.class,
	CheckupControllerTest.class,
	CheckupControllerUnitTest.class,
	CheckupRequestControllerTest.class,
	CheckupRequestControllerUnitTest.class,
	ClinicControllerTest.class,
	ClinicControllerUnit.class,
	CheckupRepositoryTest.class,
	CheckupTypeRepositoryTest.class,
	ClinicRepositoryTest.class,
	ClAdminRepositoryTest.class,
	DoctorRepositoryTest.class,
	PatientRepositoryTest.class,
	RoomRepositoryTest.class,
	UserRepositoryTest.class,
	CheckupServiceIntegracioniTest.class,
	CheckupServiceUnitTest.class,
	CheckUpTypeServiceTest.class,
	ClinicAdminServiceTest.class,
	ClinicServiceTest.class,
	ClinicServiceTestIntegration.class,
	DoctorServiceTest.class,
	EmailServiceTest.class,
	PatientServiceTest.class,
	RoomServiceTest.class,
	UserServiceTest.class
})
public class AllTests {
	
}
