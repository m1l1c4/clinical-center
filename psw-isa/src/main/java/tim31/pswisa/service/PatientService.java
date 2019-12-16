package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.User;
import tim31.pswisa.repository.PatientRepository;
import tim31.pswisa.dto.PatientDTO;
import tim31.pswisa.model.Patient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public List<PatientDTO> getAllPatients() {
		List<Patient> temp = patientRepository.findAll();
		List<PatientDTO> retVal = new ArrayList<PatientDTO>();
		for (Patient p : temp) {
			retVal.add(new PatientDTO(p));
		}
		return retVal;
	}
	
	public List<PatientDTO> filterPatients(String jbo){
		List<Patient> temp = patientRepository.findAll();
		List<PatientDTO> retVal = new ArrayList<PatientDTO>();
		for(Patient p : temp) {
			if(p.getJbo().contains(jbo)) {
				retVal.add(new PatientDTO(p));
			}
		}
		if(retVal.size() == 0)
		return null;
		else return retVal;
	}

	public List<PatientDTO> findPatients(String name, String surname, String jbo) {
		List<Patient> temp = patientRepository.findAll();
		List<PatientDTO> retVal = new ArrayList<PatientDTO>();
		for (Patient p : temp) {
			if (p.getJbo().equals(jbo)) {
				retVal.add(new PatientDTO(p));
				return retVal;
			}
		}

		if (name.equals("") && !surname.equals("")) {
			for (Patient p : temp) {
				if (p.getUser().getSurname().equals(surname)) {
					retVal.add(new PatientDTO(p));
				}
			}
		}
		if (!name.equals("") && surname.equals("")) {
			for (Patient p : temp) {
				if (p.getUser().getName().equals(name)) {
					retVal.add(new PatientDTO(p));
				}
			}
		}

		if (retVal.size() == 0) {
			return null;
		} else {
			return retVal;
		}
	}

	public List<Patient> findAllByActive(List<User> users) {
		List<Patient> patients = new ArrayList<>();
		for (User u : users) {
			if (!u.getActivated()) {
				Patient p = patientRepository.findByUserId(u.getId());
				patients.add(p);
			}
		}
		return patients;
	}

	public Patient findOneById(Long id) {
		return patientRepository.findOneById(id);
	}

	public Patient findOneByUserId(Long id) {
		return patientRepository.findByUserId(id);
	}

	public Patient save(Patient p) {
		return patientRepository.save(p);
	}

	public Patient editP(Patient patient, Patient editp) {
		patient.getUser().setName(editp.getUser().getName());
		patient.getUser().setSurname(editp.getUser().getSurname());
		patient.setAddress(editp.getAddress());
		patient.setCity(editp.getCity());
		patient.setPhoneNumber(editp.getPhoneNumber());
		patient.setState(editp.getState());
		return patient;
	}

}
