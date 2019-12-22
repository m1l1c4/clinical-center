package tim31.pswisa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Checkup;

public interface CheckUpRepository extends JpaRepository<Checkup, Long> {

	List<Checkup> findAll();

	List<Checkup> findAllByClinicId(Long id);

	Checkup findOneById(Long id);

	List<Checkup> findAllByScheduled(boolean ok);

	List<Checkup> findAllByRoomIdAndScheduledAndDate(Long id, boolean scheduled, LocalDate date);

	List<Checkup> findAllByTimeAndDate(String time, LocalDate date);
}
