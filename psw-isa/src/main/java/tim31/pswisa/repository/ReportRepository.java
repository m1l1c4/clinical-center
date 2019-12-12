package tim31.pswisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	Report findOneById(Long id);
}
