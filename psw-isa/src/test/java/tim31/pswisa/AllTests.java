package tim31.pswisa;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.controller.AdminControllerTest;
import tim31.pswisa.controller.CheckupControllerTest;
import tim31.pswisa.controller.CheckupControllerUnitTest;
import tim31.pswisa.controller.ClinicControllerTest;
import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.CheckupTypeRepositoryTest;
import tim31.pswisa.repository.DoctorRepositoryTest;
import tim31.pswisa.repository.PatientRepositoryTest;
import tim31.pswisa.repository.RoomRepositoryTest;
import tim31.pswisa.repository.UserRepositoryTest;
import tim31.pswisa.selenium.SearchAndFilterClinics;
import tim31.pswisa.service.CheckupServiceTest;
import tim31.pswisa.service.ClinicServiceTest;
import tim31.pswisa.service.DoctorServiceTest;
import tim31.pswisa.service.EmailServiceTest;
import tim31.pswisa.service.PatientServiceTest;
import tim31.pswisa.service.RoomServiceTest;
import tim31.pswisa.service.UserServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  CheckupRepositoryTest.class,
  UserRepositoryTest.class,
  PatientRepositoryTest.class,
  UserServiceTest.class,
  PatientServiceTest.class,
  EmailServiceTest.class,
  CheckupControllerTest.class,
  CheckupServiceTest.class,
  CheckupControllerUnitTest.class,
  DoctorRepositoryTest.class,
  RoomRepositoryTest.class,
  DoctorServiceTest.class,
  RoomServiceTest.class ,
  AdminControllerTest.class,
  ClinicServiceTest.class,
  CheckupTypeRepositoryTest.class,
  ClinicControllerTest.class,
  SearchAndFilterClinics.class,
})
public class AllTests {
	
}
