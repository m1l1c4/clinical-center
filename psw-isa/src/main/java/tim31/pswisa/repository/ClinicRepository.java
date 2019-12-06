package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.MedicalWorker;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
	
	List<Clinic> findAll();
	Clinic findOneByName(String clinic);
	Clinic findOneById(Long id);	
	Room findRoomById(Long id);

}
	