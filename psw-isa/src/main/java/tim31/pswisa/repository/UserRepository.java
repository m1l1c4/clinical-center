package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findAll();
}