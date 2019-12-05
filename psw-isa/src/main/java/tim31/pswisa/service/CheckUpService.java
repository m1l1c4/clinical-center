package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Checkup;
import tim31.pswisa.repository.CheckUpRepository;

@Service
public class CheckUpService {

	@Autowired
	private CheckUpRepository checkupRepository;

	public Checkup save(Checkup c) {
		return checkupRepository.save(c);
	}
}
