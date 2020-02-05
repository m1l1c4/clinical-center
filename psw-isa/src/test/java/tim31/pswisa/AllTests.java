package tim31.pswisa;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.controller.ClinicControllerTest;
import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.CheckupTypeRepositoryTest;
import tim31.pswisa.selenium.SearchAndFilterClinics;
import tim31.pswisa.service.ClinicServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
  ClinicServiceTest.class,
  CheckupTypeRepositoryTest.class,
  ClinicControllerTest.class,
  SearchAndFilterClinics.class,
  
})
public class AllTests {
	
}