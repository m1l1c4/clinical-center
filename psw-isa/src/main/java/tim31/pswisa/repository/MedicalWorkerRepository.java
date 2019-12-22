package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
	MedicalWorker findOneById(Long id);

	/**
	 * This method servers for getting all medical workers from one clinic
	 * 
	 * @param id - clinic id
	 * @return - (List<MedicalWorker>) This method returns all medical workers in
	 *         clinic
	 */
  
	List<MedicalWorker> findAllByClinicId(Long id);

}
