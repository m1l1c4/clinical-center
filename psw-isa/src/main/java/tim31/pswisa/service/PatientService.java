package tim31.pswisa.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.User;
import tim31.pswisa.repository.PatientRepository;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
 

import java.util.ArrayList;
import java.util.List;
 
 
@Service
public class PatientService {
   
    @Autowired
    private PatientRepository patientRepository;
 
    public List<Patient> findAllByActive(List<User> users){
        List<Patient> patients = new ArrayList<>();
        for (User u : users) {
            if(!u.getActivated()) {
                Patient p = patientRepository.findByUserId(u.getId());
                patients.add(p);
            }
        }
        return patients;
    }
    
    public Patient findOneById(Long id){
		return patientRepository.findOneById(id);
	}
    
    public Patient findOneByEmail(String id){
		return patientRepository.findOneByEmail(id);
	}
    
    public Patient save(Patient p){
		return patientRepository.save(p);
	}
    
    public Patient editP(Patient patient, Patient editp){
    	patient.getUser().setName(editp.getUser().getName());
		patient.getUser().setSurname(editp.getUser().getSurname());
		patient.setAddress(editp.getAddress());;
		patient.setCity(editp.getCity());
		patient.setPhoneNumber(editp.getPhoneNumber());
		patient.setState(editp.getState());
    	return patient;
    }
    
    
}
