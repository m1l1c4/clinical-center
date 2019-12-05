package tim31.pswisa.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.User;
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
	
	public MedicalWorker save(MedicalWorker mw) {
		List<User> users = userRepository.findAll();
		for(User u : users) {
			if(u.getEmail().equals(mw.getUser().getEmail())) {
				return null;
			}
		}
		return medicalWorkerRepository.save(mw);
	}
	
	public MedicalWorker findOneById(Long id){
		return medicalWorkerRepository.findOneById(id);
	}
}
