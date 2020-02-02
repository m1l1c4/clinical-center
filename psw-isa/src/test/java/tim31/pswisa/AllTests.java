package tim31.pswisa;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.controller.CheckupControllerTest;
import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.PatientRepositoryTest;
import tim31.pswisa.repository.UserRepositoryTest;
import tim31.pswisa.service.EmailServiceTest;
import tim31.pswisa.service.PatientServiceTest;
import tim31.pswisa.service.UserServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  CheckupRepositoryTest.class,
  UserRepositoryTest.class,
  PatientRepositoryTest.class,
  UserServiceTest.class,
  PatientServiceTest.class,
  EmailServiceTest.class,
  CheckupControllerTest.class
})
public class AllTests {
	
}

