package tim31.pswisa.model;

import java.util.ArrayList;

public class ClinicalCenter {
	
	private ArrayList<Clinic> clinics;
	private Codebook codebook;
	
	public ClinicalCenter() {
		clinics = new ArrayList<Clinic>();
	}
	
	public ClinicalCenter(ArrayList<Clinic> clinics, Codebook codebook) {
		super();
		this.clinics = clinics;
		this.codebook = codebook;
	}
	public ArrayList<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(ArrayList<Clinic> clinics) {
		this.clinics = clinics;
	}
	public Codebook getCodebook() {
		return codebook;
	}
	public void setCodebook(Codebook codebook) {
		this.codebook = codebook;
	}
	
}
