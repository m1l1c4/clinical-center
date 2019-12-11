package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Codebook;

public interface CodebookRepository extends JpaRepository<Codebook, Long> {

	List<Codebook> findAll();

	Codebook findOneByCode(String code);
}
