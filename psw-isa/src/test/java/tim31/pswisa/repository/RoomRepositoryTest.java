package tim31.pswisa.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tim31.pswisa.constants.CheckupConstants;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Room;

public class RoomRepositoryTest {
	@Autowired
	RoomRepository roomRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void testFindOneById() {
		Room testRoom = new Room();		
		testRoom = entityManager.persistAndFlush(testRoom);
		Room room = roomRepository.findOneById(testRoom.getId());
		assertEquals(testRoom.getId(), room.getId());
	}
	
	
}
