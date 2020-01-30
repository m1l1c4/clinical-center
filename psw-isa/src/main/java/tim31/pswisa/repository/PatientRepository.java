package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Patient;

@Service
public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByUserId(Long id);

	Patient findOneById(Long id);

	List<Patient> findAll();

	List<Patient> findAllByProcessed(boolean processed);
}
