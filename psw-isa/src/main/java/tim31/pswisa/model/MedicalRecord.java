package tim31.pswisa.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


public class MedicalRecord {
	
	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;*/
	
	private ArrayList<Report> reports;
	private String bloodType;
	private Double diopter;
	private Double height;
	private Double weight;
	private ArrayList<String> allergies;
	
	public MedicalRecord() {
		reports = new ArrayList<Report>();
		allergies = new ArrayList<String>();
	}
	
	public MedicalRecord(Patient patient, ArrayList<Report> reports, String bloodType, Double diopter, Double height,
			Double weight, ArrayList<String> allergies) {
		super();
		//this.patient = patient;
		this.reports = reports;
		this.bloodType = bloodType;
		this.diopter = diopter;
		this.height = height;
		this.weight = weight;
		this.allergies = allergies;
	}

	/*public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}*/

	public ArrayList<Report> getReports() {
		return reports;
	}

	public void setReports(ArrayList<Report> reports) {
		this.reports = reports;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public Double getDiopter() {
		return diopter;
	}

	public void setDiopter(Double diopter) {
		this.diopter = diopter;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public ArrayList<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(ArrayList<String> allergies) {
		this.allergies = allergies;
	}
	

}
