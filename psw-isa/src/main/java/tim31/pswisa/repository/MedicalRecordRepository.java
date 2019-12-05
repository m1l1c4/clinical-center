package tim31.pswisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

}
