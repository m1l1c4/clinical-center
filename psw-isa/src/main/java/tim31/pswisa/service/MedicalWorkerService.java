package tim31.pswisa.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.MedicalWorkerDTO;
import tim31.pswisa.model.Authority;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	public Set<MedicalWorker> findAllByClinicId(Long id) {
		return medicalWorkerRepository.findAllByClinicId(id);
	}

	public MedicalWorker updateMedicalWorker(MedicalWorker medWorker, MedicalWorkerDTO mw) {
		medWorker.getUser().setName(mw.getUser().getName());
		medWorker.getUser().setSurname(mw.getUser().getSurname());
		medWorker.setPhone(mw.getPhone());
		medWorker = update(medWorker);
		return medWorker;
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

	public MedicalWorker save(MedicalWorkerDTO mw) {
		User user = userRepository.findOneByEmail(mw.getUser().getEmail());
		if (user != null) {
			return null;
		}
		MedicalWorker medicalWorker = new MedicalWorker();
		user = new User();
		user.setName(mw.getUser().getName());
		user.setSurname(mw.getUser().getSurname());
		user.setEmail(mw.getUser().getEmail());
		user.setType(mw.getUser().getType());
		medicalWorker.setUser(user);
		medicalWorker.setPhone(mw.getPhone());
		medicalWorker.setEndHr(mw.getEndHr());
		medicalWorker.setStartHr(mw.getStartHr());
		medicalWorker.getUser().setPassword(passwordEncoder.encode("sifra123"));
		medicalWorker.getUser().setFirstLogin(false);
		medicalWorker.getUser().setEnabled(true);
		medicalWorker.getUser().setActivated(true);
		List<Authority> auth = authorityService.findByname(medicalWorker.getType());
		medicalWorker.getUser().setAuthorities(auth);

		return medicalWorkerRepository.save(medicalWorker);
	}

	public MedicalWorker findOneById(Long id) {
		return medicalWorkerRepository.findOneById(id);
	}
}
