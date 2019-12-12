package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	Recipe findOneByCode(String code);

	List<Recipe> findAllByVerified(Boolean verified);

	Recipe findOneById(Long id);
}
