package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Checkup;
import tim31.pswisa.repository.CheckUpRepository;

@Service
public class CheckUpService {

	@Autowired
	private CheckUpRepository checkupRepository;

	public List<Checkup> findAll() {
		return checkupRepository.findAll();
	}

	public List<Checkup> findAllByClinicId(Long id) {
		return checkupRepository.findAllByClinicId(id);
	}

	public Checkup save(Checkup c) {
		return checkupRepository.save(c);
	}
}
