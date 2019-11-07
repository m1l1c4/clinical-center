package tim31.pswisa.model;

import java.util.ArrayList;

public class Report {

	private String diagnosis;
	private ArrayList<String>medicines;
	
	public Report() {
		medicines = new ArrayList<String>();
	}

	public Report(String diagnosis) {
		super();
		this.diagnosis = diagnosis;
	}

	
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public ArrayList<String> getMedicines() {
		return medicines;
	}

	public void setMedicines(ArrayList<String> medicines) {
		this.medicines = medicines;
	}
	
	
}
