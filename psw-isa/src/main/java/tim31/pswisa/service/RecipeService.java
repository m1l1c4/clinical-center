package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Recipe;
import tim31.pswisa.repository.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public Recipe save(Recipe r) {
		return recipeRepository.save(r);
	}

	public Recipe findOneByCode(String code) {
		return recipeRepository.findOneByCode(code);
	}

	public List<Recipe> findAllByVerified(Boolean verified) {
		return recipeRepository.findAllByVerified(verified);
	}

	public Recipe findOneById(Long id) {
		return recipeRepository.findOneById(id);
	}

	public Recipe verify(Recipe recipe, MedicalWorker nurse) {
		recipe.setVerified(true);
		recipe.setNurse(nurse);
		return recipeRepository.save(recipe);
	}
}
