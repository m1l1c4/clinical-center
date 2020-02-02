package tim31.pswisa.repository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CheckupRepositoryTest {

	@MockBean
	private CheckUpRepository checkupRepository;

	/*@Test
	public void testFindAllByRoomIdAndScheduledAndDate() {
		List<Checkup> checkups = checkupRepository.findAllByRoomIdAndScheduledAndDate(RoomConstants.ID_R_1,
				CheckupConstants.SCHEDULED_1, CheckupConstants.LOCAL_DATE_1);

		assertEquals(CheckupConstants.REZ_1, checkups.size());
		for (Checkup c : checkups) {
			assertEquals(c.getRoom().getId(),RoomConstants.ID_R_1);
		}
	}*/
}
