package tim31.pswisa.service;

import java.util.ArrayList;
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
	private CheckUpTypeRepository checkupTypeRepository;

	public Room findRoomById(Long id) {
		return clinicRepository.findRoomById(id);
	}

	public List<Clinic> findAll() {
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

		if (clinic.getRooms().size() == 0)
			return null;

		for (Clinic c : clinics) {
			if (c.getName().equals(clinic.getName()) && c.getId() != clinic.getId())
				return null;
		}

		for (Room r : clinic.getRooms())
			r.setClinic(clinic);
		return clinicRepository.save(clinic);
	}

	public List<Clinic> searchClinics(String[] params) {
		List<Clinic> retClinics = new ArrayList<Clinic>();
		List<Clinic> result = new ArrayList<Clinic>();
		int counter = 0; // assuming there is 7 checkups in one day
		CheckUpType srchType = checkupTypeRepository.findOneByName(params[0]);

		if (params[0].equals("") || params[1].equals("") || srchType == null)
			return null; // nothing to search

		else {
			for (Clinic cl : srchType.getClinics()) {
				retClinics.add(cl); // all clinics of specified type of check-up
			}

			// check if clinic has available doctor, if not remove that clinic from list
			for (Clinic cl : retClinics) {
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

	}

	public List<Clinic> filterClinics(String parametar, ArrayList<Clinic> clinics) {
		int ranging = -1;
		List<Clinic> filtered = new ArrayList<Clinic>();
		ranging = Integer.parseInt(parametar);

		for (Clinic clinic : clinics) {
			if (clinic.getRating() >= ranging) {
				filtered.add(clinic);
			}
		}

		return filtered;
	}

	public Clinic update(Clinic clinic) {
		for (Room r : clinic.getRooms())
			r.setClinic(clinic);
		return clinicRepository.save(clinic);
	}

}
