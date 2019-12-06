package tim31.pswisa.service;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.repository.MedicalWorkerRepository;
import tim31.pswisa.repository.UserRepository;

@Service
public class MedicalWorkerService {

	@Autowired
	private MedicalWorkerRepository medicalWorkerRepository;

	
	@Autowired
	private UserRepository userRepository;
	
	public Set<MedicalWorker>findAllByClinicId(Long id){
		return medicalWorkerRepository.findAllByClinicId(id);
	}


	public MedicalWorker findByUser(Long id) {
		return medicalWorkerRepository.findOneByUserId(id);
	}

	public MedicalWorker findOne(Long id) {
		return medicalWorkerRepository.findById(id).orElseGet(null);

	}
	
	public MedicalWorker update(MedicalWorker mw) {
		return medicalWorkerRepository.save(mw);
	}
	
	public MedicalWorker findOneById(Long id){
		return medicalWorkerRepository.findOneById(id);
	}
}
