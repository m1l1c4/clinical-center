package tim31.pswisa.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.PatientRepository;
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
}
