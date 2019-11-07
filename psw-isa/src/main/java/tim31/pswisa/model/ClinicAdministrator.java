package tim31.pswisa.model;

import java.util.ArrayList;

public class ClinicAdministrator {
	
	private String clinic;
	private ArrayList<Absence> absences;
	
	public ClinicAdministrator() {
		absences = new ArrayList<Absence>();
	}
	
	public ClinicAdministrator(String clinic, ArrayList<Absence> absences) {
		super();
		this.clinic = clinic;
		this.absences = absences;
	}
	public String getClinic() {
		return clinic;
	}
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	public ArrayList<Absence> getAbsences() {
		return absences;
	}
	public void setAbsences(ArrayList<Absence> absences) {
		this.absences = absences;
	}
	
	

}