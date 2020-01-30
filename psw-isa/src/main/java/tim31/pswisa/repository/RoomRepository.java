package tim31.pswisa.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import tim31.pswisa.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	/**
	 * This method servers for getting all rooms in clinic
	 * 
	 * @return - (List<Room>) This method returns all rooms in database
	 */
	List<Room> findAll();

	/**
	 * This method servers for getting all rooms in clinic
	 * 
	 * @param id - id of clinic
	 * @return - (List<Room>) This method returns all rooms in clinic
	 */
	List<Room> findAllByClinicId(Long id);

	/**
	 * This method servers for getting one room by room id, transaction (pesimistic
	 * D)
	 * 
	 * @param id - room id
	 * @return - (Room) This method returns one room
	 */
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select r from Room r where r.id = :id")
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "0") })
	Room findOneById(Long id);

	/**
	 * This method servers for getting one room by clinic and number
	 * 
	 * @param clinicId - id of clinic where room is
	 * @return - (Room) This method returns searched room
	 */
	Room findOneByClinicIdAndNumber(Long clinicId, int number);

	/**
	 * This method servers for getting one room by number
	 * 
	 * @param number - number of room that has to be returned
	 * @return - (Room) This method returns searched room
	 */

	List<Room> findAllByClinicIdAndTipRoom(Long clinicId, String type);

	/**
	 * This method servers for getting one room by type of room
	 * 
	 * @param type - type of room that has to be returned
	 * @return - (List<Room>) This method returns searched rooms
	 */

	Room findOneByNumber(int number);
}
