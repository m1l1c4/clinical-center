package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.AbsenceDTO;
import tim31.pswisa.model.Absence;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.repository.AbsenceRepository;
import tim31.pswisa.repository.ClinicRepository;

@Service
public class AbsenceService {

	@Autowired
	private AbsenceRepository abesenceRepository;

	@Autowired
	private ClinicRepository clinicRepository;

	/**
	 * This method servers for getting all absences in database
	 * 
	 * @return - (List<Absence>) This method returns all absences in database
	 */
	public List<Absence> findAll() {
		return abesenceRepository.findAll();
	}

	/**
	 * This method servers for saving new absence in database
	 * 
	 * @param a - absence that have to be saved
	 * @return - (List<Absence>) This method returns saved absence
	 */
	public Absence save(Absence a) {
		return abesenceRepository.save(a);
	}

	/**
	 * This method servers getting one absence from database
	 * 
	 * @param id - id of absence that have to be found
	 * @return - (Absence) This method returns gotten absence
	 */
	public Absence findOneById(Long id) {
		return abesenceRepository.findOneById(id);
	}

	/**
	 * This method servers getting all absence from database in one clinic
	 * 
	 * @param id - id of clinic
	 * @return - (List<Absence>) This method returns list of absences in clinic
	 */
	public List<Absence> findAllByClinicOfAbsenceId(Long id) {
		return abesenceRepository.findAllByClinicOfAbsenceId(id);
	}

	public List<Absence> findAllByMedicalWorkerId(Long id) {
		return abesenceRepository.findAllByMedicalWorkerId(id);
	}

	public Absence create(AbsenceDTO a, MedicalWorker mw) {
		Absence absence = new Absence();
		absence.setAccepted("SENT");
		Clinic clinic = clinicRepository.getOne(a.getClinicOfAbsence().getId());
		absence.setClinicOfAbsence(clinic);
		absence.setEndVacation(a.getEndVacation());
		absence.setStartVacation(a.getStartVacation());
		absence.setMw(mw);
		absence.setTypeOfAbsence(a.getTypeOfAbsence());
		return absence;
	}

}
