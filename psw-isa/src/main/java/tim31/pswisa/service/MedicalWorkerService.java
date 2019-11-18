package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.repository.MedicalWorkerRepository;

@Service
public class MedicalWorkerService {

	@Autowired
	private MedicalWorkerRepository medicalWorkerRepository;
	
	public MedicalWorker findOne(Long id) {
		return medicalWorkerRepository.findById(id).orElseGet(null);
	}
	
	public MedicalWorker update(MedicalWorker mw) {
		return medicalWorkerRepository.save(mw);
	}
}
