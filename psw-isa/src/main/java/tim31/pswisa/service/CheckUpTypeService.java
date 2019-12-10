package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.repository.CheckUpTypeRepository;

@Service
public class CheckUpTypeService {

	@Autowired
	private CheckUpTypeRepository checkUpTypeRepository;

	public List<CheckUpType> findAll() {
		return checkUpTypeRepository.findAll();
	}

	public CheckUpType findOneByName(String name) {
		return checkUpTypeRepository.findOneByName(name);
	}

	public CheckUpType save(CheckUpType ct) {
		List<CheckUpType> cek = checkUpTypeRepository.findAll();

		for (CheckUpType c : cek) {
			if (c.getName().equals(ct.getName())) {
				return null;
			}
		}
		return checkUpTypeRepository.save(ct);
	}
}
