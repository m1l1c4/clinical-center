package tim31.pswisa.model;

public class Recipe {

	private Patient patient;
	private MedicalWorker nurse; // medicinska sestra koja ga je ovjerila
	private MedicalWorker doctor; // doktor koji ga je propisao
	private String medicine; // lijek koje je propisan
	
	public Recipe() {
		
	}

	public Recipe(Patient patient, MedicalWorker nurse, MedicalWorker doctor, String medicine) {
		super();
		this.patient = patient;
		this.nurse = nurse;
		this.doctor = doctor;
		this.medicine = medicine;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public MedicalWorker getNurse() {
		return nurse;
	}

	public void setNurse(MedicalWorker nurse) {
		this.nurse = nurse;
	}

	public MedicalWorker getDoctor() {
		return doctor;
	}

	
	public void setDoctor(MedicalWorker doctor) {
		this.doctor = doctor;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	
	
	
}
