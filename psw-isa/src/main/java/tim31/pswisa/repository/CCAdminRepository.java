package tim31.pswisa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tim31.pswisa.model.ClinicalCenterAdministrator;

public interface CCAdminRepository extends JpaRepository<ClinicalCenterAdministrator, Long> {

	List<ClinicalCenterAdministrator> findAll();
}
