package tim31.pswisa.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import tim31.pswisa.model.MedicalWorker;

public interface MedicalWorkerRepository extends JpaRepository<MedicalWorker, Long> {

	/**
	 * This method servers for getting one medical worker by user id
	 * 
	 * @param id - user id
	 * @return - (MedicalWorker) This method returns searched medical worker
	 */
	MedicalWorker findOneByUserId(Long id);

	/**
	 * This method servers for getting one medical worker by id
	 * 
	 * @param id - id of medical worker
	 * @return - (MedicalWorker) This method returns searched medical worker
	 */
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select mw from MedicalWorker mw where mw.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	MedicalWorker findOneById(@Param("id")Long id);

	Set<MedicalWorker> findAllByTipAndClinicId(String type, Long id);
	/**
	 * This method servers for getting all medical workers from one clinic
	 * 
	 * @param id - clinic id
	 * @return - (List<MedicalWorker>) This method returns all medical workers in
	 *         clinic
	 */
  
	List<MedicalWorker> findAllByClinicId(Long id);
	
	@Query("select s from MedicalWorker s where s.user.type = ?1 and s.clinic.id = ?2")
	List<MedicalWorker> findAllDoctors(String type, Long id);

}
