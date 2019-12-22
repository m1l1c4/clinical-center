package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Absence;
import tim31.pswisa.repository.AbsenceRepository;

@Service
public class AbsenceService {

	@Autowired
	private AbsenceRepository abesenceRepository;

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

}
