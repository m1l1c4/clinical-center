package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.MedicalRecord;
import tim31.pswisa.model.Patient;
import tim31.pswisa.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	public MedicalRecord add(Patient p) {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setPatient(p);
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord findOneById(Long id) {
		return medicalRecordRepository.findOneById(id);
	}

}
