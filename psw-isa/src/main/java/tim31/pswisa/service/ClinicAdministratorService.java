package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.repository.ClinicAdministratorRepository;


@Service
public class ClinicAdministratorService {

	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;
	
	public ClinicAdministrator findByUser(Long id) {
		return clinicAdministratorRepository.findOneByUserId(id);
	}
	
	public ClinicAdministrator update(ClinicAdministrator ca) {
		return clinicAdministratorRepository.save(ca);
	}
	
	public ClinicAdministrator findOneById(Long id){
		return clinicAdministratorRepository.findOneById(id);
	}
}
