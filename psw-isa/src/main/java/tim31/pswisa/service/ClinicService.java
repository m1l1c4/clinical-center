package tim31.pswisa.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Room;
import tim31.pswisa.repository.CheckUpTypeRepository;
import tim31.pswisa.repository.ClinicRepository;
 
@Service
public class ClinicService {
   
    @Autowired
    private ClinicRepository clinicRepository;
    
    @Autowired
    private CheckUpTypeRepository chTypeRepository;
   
    public List<Clinic> findAll(){
        return clinicRepository.findAll();
    }
   
    public Clinic save(Clinic clinic) {
        List<Clinic> clinics = clinicRepository.findAll();
       
        if(clinic.getRooms().size() == 0)
            return null;
       
        for (Clinic c : clinics) {
            if (c.getName().equals(clinic.getName()))
                return null;
        }
       
        for (Room r: clinic.getRooms())
            r.setClinic(clinic);
        return clinicRepository.save(clinic);
    }
    
    public List<Clinic> searchClinics(String[] params)
    {
    	List<Clinic> retClinics = new ArrayList<Clinic>();
    	List<Clinic> result = new ArrayList<Clinic>();    	
    	CheckUpType srchType = chTypeRepository.findByName(params[0]);
    	int counter = 0 ;	// assuming there is 7 checkups in one day
    	
    	for (Clinic cl : srchType.getClinics()) {
			retClinics.add(cl);			//all clinics of specified type of checkup
		}
    	
    	// check if clinc has available doctor, if not remove that clinic from list
    	for (Clinic cl : retClinics)
    	{
    		for (MedicalWorker mw : cl.getMedicalStuff()) {
    			if (mw.getUser().getType().equals("DOKTOR") && mw.getType().equals(params[0])) {
    				for (Checkup c : mw.getCheckUps()) {
    					if (c.getDate().toString().equals(params[1])) {
    						counter++;
    					}
    						
    				}
    				if (counter < 7) {
    					result.add(cl);
    					break;
    				}
    			}
    		}
    	}
    	
    	
    	return result;
    	
    }

    public List<Clinic> filterClinics(String[] parametri, ArrayList<Clinic> clinics){    	
    	int ranging = -1;
    	double price = -1;
    	HashMap<Long, Clinic> filtered = new HashMap<Long, Clinic>();
    	
    	if( parametri[0] != "") {
    		ranging = Integer.parseInt(parametri[0])  ;
    	}
    	
    	if (parametri[1] != "") {
    		price = Double.parseDouble(parametri[1]) ;
    	}
    	
        for (Clinic clinic : clinics) {
			if (ranging != -1 && clinic.getRating() >= ranging && !filtered.containsKey(clinic.getId())) {
				filtered.put(clinic.getId(), clinic);
			}
			/*else if (price != -1 && clinic.getpr() >= ranging && !filtered.containsKey(clinic.getId())) {
				
			}*/
		}
        
        
        
        
        return (List<Clinic>) filtered.values();
    }
    
    
    
 
}
