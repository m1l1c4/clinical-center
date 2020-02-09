package tim31.pswisa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.CheckupTypeRepositoryTest;
import tim31.pswisa.repository.ClAdminRepositoryTest;
import tim31.pswisa.repository.ClinicRepositoryTest;
import tim31.pswisa.repository.DoctorRepositoryTest;
import tim31.pswisa.repository.PatientRepositoryTest;
import tim31.pswisa.repository.RoomRepositoryTest;
import tim31.pswisa.repository.UserRepositoryTest;
import tim31.pswisa.service.CheckupServiceUnitTest;
import tim31.pswisa.service.ClinicServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CheckupRepositoryTest.class,
	CheckupTypeRepositoryTest.class,
	ClAdminRepositoryTest.class,
	ClinicRepositoryTest.class,
	DoctorRepositoryTest.class,
	PatientRepositoryTest.class,
	RoomRepositoryTest.class,
	UserRepositoryTest.class,
	CheckupServiceUnitTest.class,
	ClinicServiceTest.class
})
public class UnitTests {

}
