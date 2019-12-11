package tim31.pswisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Report;
import tim31.pswisa.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;

	public Report add(Report r) {
		Report report = new Report();
		report.setDiagnose(r.getDiagnose());
		report.setInformations(r.getInformations());
		report.setRecipes(r.getRecipes());
		report.setMedicalRecord(r.getMedicalRecord());
		return reportRepository.save(report);
	}

}
