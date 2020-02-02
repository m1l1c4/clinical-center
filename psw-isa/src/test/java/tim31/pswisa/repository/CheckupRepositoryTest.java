package tim31.pswisa.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.model.Checkup;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CheckupRepositoryTest {

	@Autowired
	CheckUpRepository checkupRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void testFindOneById() {
		Checkup checkupTest = new Checkup();
		checkupTest.setScheduled(CheckupConstants.CHECKUP_SCHEDULED);
		checkupTest = entityManager.persistAndFlush(checkupTest);
		Checkup checkup = checkupRepository.findOneById(checkupTest.getId());
		assertEquals(checkupTest.getId(), checkup.getId());
	}

	@Test
	public void testSave() {
		Checkup checkupTest = new Checkup();
		checkupTest = entityManager.persistAndFlush(checkupTest);
		Checkup checkup = checkupRepository.save(checkupTest);
		assertEquals(checkupTest.getId(), checkup.getId());
	}

}
