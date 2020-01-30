package tim31.pswisa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Recipe;
import tim31.pswisa.repository.RecipeRepository;

@Service
@Transactional(readOnly = true)
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Transactional(readOnly = false)
	public Recipe save(Recipe r) {
		return recipeRepository.save(r);
	}

	public Recipe findOneByCode(String code) {
		return recipeRepository.findOneByCode(code);
	}

	public List<Recipe> findAllByVerified(Boolean verified, MedicalWorker nurse) {
		List<Recipe> ret = new ArrayList<>();
		List<Recipe> recipes = recipeRepository.findAllByVerified(verified);
		for(Recipe r: recipes) {
			if(r.getDoctor().getClinic().getId() == nurse.getClinic().getId()) {
				ret.add(r);
			}
		}
		return ret;
	}

	public Recipe findOneById(Long id) {
		return recipeRepository.findOneById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Recipe verify(Recipe recipe, MedicalWorker nurse) throws Exception{
		recipe.setVerified(true);
		recipe.setNurse(nurse);
		return recipeRepository.save(recipe);
	}
}
