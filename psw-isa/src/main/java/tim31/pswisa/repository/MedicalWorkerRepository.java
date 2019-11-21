package tim31.pswisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.MedicalWorker;

public interface MedicalWorkerRepository extends JpaRepository<MedicalWorker, Long> {


	MedicalWorker findOneByUserId(Long id);
	//MedicalWorker findOneById(Long id);

	
}
