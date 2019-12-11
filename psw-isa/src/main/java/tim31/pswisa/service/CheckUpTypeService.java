package tim31.pswisa.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CheckUpTypeDTO;
import tim31.pswisa.dto.ClinicDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.repository.CheckUpTypeRepository;

@Service
public class CheckUpTypeService {

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private CheckUpTypeRepository checkUpTypeRepository;

	public List<CheckUpType> findAll() {
		return checkUpTypeRepository.findAll();
	}

	public CheckUpType findOneByName(String name) {
		return checkUpTypeRepository.findOneByName(name);
	}

	public String deleteType(String name, ClinicAdministrator clinicAdministrator) {
		Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
		Set<CheckUpType> tipovi = clinic.getCheckUpTypes();
		for (CheckUpType t : tipovi) {
			if (t.getName().equals(name)) {
				clinic.getCheckUpTypes().remove(t);
				clinic = clinicService.save(new ClinicDTO(clinic)); // delete type from clinic
				CheckUpType temp = findOneByName(name);
				temp.getClinics().remove(clinic);
				temp = save(temp);
				return "Obrisano";
			}
		}
		return "";

	}

	public CheckUpType save(CheckUpType ct) {
		List<CheckUpType> cek = checkUpTypeRepository.findAll();

		for (CheckUpType c : cek) {
			if (c.getName().equals(ct.getName())) {
				return null;
			}
		}
		return checkUpTypeRepository.save(ct);
	}

	public CheckUpType addType(CheckUpTypeDTO type, ClinicAdministrator clinicAdministrator) {
		CheckUpType tip = new CheckUpType();
		tip.setName(type.getName());
		List<CheckUpType> allTypes = findAll();
		Clinic klinika = new Clinic();
				
		klinika = clinicService.findOneById(clinicAdministrator.getClinic().getId());
		int x = 0;
		for (CheckUpType t : klinika.getCheckUpTypes()) {
			if (t.getName().equals(tip.getName())) {
				x = 1;
			}
		}
		if (x == 0) {
			klinika.getCheckUpTypes().add(tip);
			klinika = clinicService.updateClinic(clinicAdministrator, new ClinicDTO(klinika));
		} else {
			return null; // already exist
		}

		// save types in all types of clinical center
		int y = 0;
		for (CheckUpType t : allTypes) {
			if (tip.getName().equals(t.getName())) {
				y = 1;
			}
		}
		if (y == 0) {
			tip.getClinics().add(klinika);
			tip = save(tip);
			//klinika.getCheckUpTypes().add(tip);
			return tip;
		} else
			return null;
	}
}