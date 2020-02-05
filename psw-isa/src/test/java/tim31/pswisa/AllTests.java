package tim31.pswisa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tim31.pswisa.controller.ClinicControllerTest;
import tim31.pswisa.repository.CheckupRepositoryTest;
import tim31.pswisa.repository.CheckupTypeRepositoryTest;
import tim31.pswisa.selenium.SearchAndFilterClinics;
import tim31.pswisa.service.ClinicServiceTest;
import tim31.pswisa.controller.AdminControllerTest;
import tim31.pswisa.controller.CheckupControllerTest;
import tim31.pswisa.repository.DoctorRepositoryTest;
import tim31.pswisa.repository.RoomRepositoryTest;
import tim31.pswisa.service.CheckupServiceTest;
import tim31.pswisa.service.DoctorServiceTest;
import tim31.pswisa.service.RoomServiceTest;
 
 //DODAJES SVE KLASE U KOJIMA TESTIRAS
@RunWith(Suite.class)
@Suite.SuiteClasses({
  CheckupRepositoryTest.class,
  DoctorRepositoryTest.class,
  RoomRepositoryTest.class,
  CheckupServiceTest.class,
  DoctorServiceTest.class,
  RoomServiceTest.class ,
  CheckupControllerTest.class,
  AdminControllerTest.class,
  ClinicServiceTest.class,
  CheckupTypeRepositoryTest.class,
  ClinicControllerTest.class,
  SearchAndFilterClinics.class,

})

}