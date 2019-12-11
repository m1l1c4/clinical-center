package tim31.pswisa.dto;

import java.util.Set;

import tim31.pswisa.model.MedicalRecord;
import tim31.pswisa.model.Recipe;
import tim31.pswisa.model.Report;

public class ReportDTO {

	private Long id;
	private Set<Recipe> recipes;
	private MedicalRecord medicalRecord;
	private String informations;
	private String diagnose;
	
	public ReportDTO() {
		super();
	}

	public ReportDTO(Report report) {
		this(report.getId(), report.getRecipes(), report.getMedicalRecord(), report.getInformations(),
				report.getDiagnose());
	}

	public ReportDTO(Long id, Set<Recipe> recipes, MedicalRecord medicalRecord, String informations, String diagnose) {
		super();
		this.id = id;
		this.recipes = recipes;
		this.medicalRecord = medicalRecord;
		this.informations = informations;
		this.diagnose = diagnose;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public String getInformations() {
		return informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

}
