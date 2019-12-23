package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Checkup;

public interface CheckUpRepository extends JpaRepository<Checkup, Long> {

	List<Checkup> findAll();

	List<Checkup> findAllByClinicId(Long id);
	
	Checkup findOneById(Long id);
}
