package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.MedicalRecordDTO;
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
	
	public MedicalRecord update(MedicalRecordDTO mr) {
		MedicalRecord medicalRecord = medicalRecordRepository.findOneById(mr.getId());
		medicalRecord.setBloodType(mr.getBloodType());
		medicalRecord.setDiopter(mr.getDiopter());
		medicalRecord.setHeight(mr.getHeight());
		medicalRecord.setWeight(mr.getWeight());
		return medicalRecordRepository.save(medicalRecord);
	}

}
