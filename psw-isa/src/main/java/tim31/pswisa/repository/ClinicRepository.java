package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
	
	List<Clinic> findAll();
}
