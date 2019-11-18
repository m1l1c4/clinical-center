package tim31.pswisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.ClinicAdministrator;



public interface ClinicAdministratorRepository extends JpaRepository<ClinicAdministrator, Long> {

	ClinicAdministrator findByUser(Long id);
	
}

