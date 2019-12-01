package tim31.pswisa.service;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicalCenterAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.model.User;
import tim31.pswisa.repository.ClinicRepository;
 
@Service
public class ClinicService {
   
    @Autowired
    private ClinicRepository clinicRepository;
   
    public List<MedicalWorker>findAllMedicalWorkerById(Long id){
    	return clinicRepository.findAllMedicalWorkerById(id);
    }
    
    public List<CheckUpType>findAllCheckUpTypeById(Long id){
    	return clinicRepository.findAllCheckUpTypeById(id);
    }
    
    public List<Clinic> findAll(){
        return clinicRepository.findAll();
    }
    
    public Clinic findOneById(Long id) {
    	return clinicRepository.findOneById(id);
    }
    
    public Clinic findOneByName(String clinic) {
    	return clinicRepository.findOneByName(clinic);
    }
   
    public Clinic save(Clinic clinic) {
        List<Clinic> clinics = clinicRepository.findAll();
       
        if(clinic.getRooms().size() == 0)
            return null;
       
        for (Clinic c : clinics) {
            if (c.getName().equals(clinic.getName()) && c.getId()!=clinic.getId())
                return null;
        }
       
        for (Room r: clinic.getRooms())
            r.setClinic(clinic);
        return clinicRepository.save(clinic);
    }
 
}
