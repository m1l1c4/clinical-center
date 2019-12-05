package tim31.pswisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Checkup;

public interface CheckUpRepository extends JpaRepository<Checkup, Long> {

}
