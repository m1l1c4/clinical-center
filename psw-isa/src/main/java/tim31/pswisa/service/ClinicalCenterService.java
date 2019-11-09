package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.repository.ClinicalCenterRepository;

@Service
public class ClinicalCenterService {
	
	@Autowired
	private ClinicalCenterRepository clinicalCenterRepository;

}
