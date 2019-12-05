package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.CheckUpType;

public interface CheckUpTypeRepository  extends JpaRepository<CheckUpType, Long>{

	List<CheckUpType> findAll();
	
	CheckUpType findOneByName(String name);
}
