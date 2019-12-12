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
				clinic = clinicService.update(clinic); // delete type from clinic
				CheckUpType temp = findOneByName(name);
				temp.getClinics().remove(clinic);
				temp = saveTwo(temp);
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

	public CheckUpType saveTwo(CheckUpType ct) {
		List<CheckUpType> cek = checkUpTypeRepository.findAll();
		return checkUpTypeRepository.save(ct);
	}

	// can't save name if there is one type with the same name
	public CheckUpType update(CheckUpType c, String after) {
		List<CheckUpType> allTypes = findAll();
		for (CheckUpType cek : allTypes) {
			if (cek.getName().equals(after)) {
				return null;
			}
		}
		c.setName(after);
		return checkUpTypeRepository.save(c);
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

		int o = 0;
		for (CheckUpType c : allTypes) {
			if (c.getName().equals(type.getName())) {
				o = 1;
			}
		}
		if (o == 0) {
			tip.setName(type.getName());
			tip = save(tip);
		}

		if (x == 0) {
			CheckUpType temp = findOneByName(type.getName());
			temp.getClinics().add(klinika);
			temp = save(temp);
			klinika.getCheckUpTypes().add(temp);
			klinika = clinicService.updateClinic(clinicAdministrator, new ClinicDTO(klinika));
			return temp;
		} else
			return null;
	}
}
