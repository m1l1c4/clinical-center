package tim31.pswisa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Checkup;

public interface CheckUpRepository extends JpaRepository<Checkup, Long> {

	/**
	 * This method servers for getting all check-ups from database
	 * 
	 * @return - (List<Checkup>) This method returns list of check-ups from database
	 */
	List<Checkup> findAll();

	/**
	 * This method servers for getting all check-ups in one clinic
	 * 
	 * @param id - clinic id
	 * @return - (List<Checkup>) This method returns list of check-ups in one clinic
	 */
	List<Checkup> findAllByClinicId(Long id);

	/**
	 * This method servers for getting one check-up by id
	 * 
	 * @param id - check-up id that has to be returned
	 * @return - (Checkup) This method returns one check-up
	 */

	Checkup findOneById(Long id);

	/**
	 * This method servers for getting all check-ups by room id and time and date of
	 * operation or appointment
	 * 
	 * @param id   - id of room
	 * @param time - time of appointment or operation
	 * @param date - date of appointment or operation
	 * @return - (List<Checkup>) This method returns list of check-ups by id, time
	 *         and date
	 */
	List<Checkup> findAllByRoomIdAndTimeAndDate(Long id, String time, LocalDate date);

	/**
	 * This method servers for getting all check-ups by scheduled
	 * 
	 * @param ok - is/not scheduled
	 * @return - (List<Checkup>) This method returns list of check-ups by param ok
	 */
	List<Checkup> findAllByScheduled(boolean ok);

	List<Checkup> findAllByRoomIdAndScheduledAndDate(Long id, boolean scheduled, LocalDate date);

	List<Checkup> findAllByTimeAndDate(String time, LocalDate date);
}
