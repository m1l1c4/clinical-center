package tim31.pswisa.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Clinic {
	private String name;
	private String city;
	private String adress; 	//ulica i broj, za prikaz na mapi
	private int rating; 	//ocena klinike
	
	private ArrayList<ClinicAdministrator> clAdmins;
	private ArrayList<String> appointmentTypes;		//lista tipova pregleda
	private ArrayList<MedicalWorker> medicalStuff;
	private ArrayList<Patient> patients;
	private ArrayList<Room> rooms;	//sale
	private ArrayList<Checkup> availableAppointments;	//slobodni pregledi
	
	/* key - tip pregleda
	 * value - cena za taj tip pregleda	 */	
	private HashMap<String, Double> pricelist;

	public Clinic() {
		super();
		this.clAdmins = new ArrayList<ClinicAdministrator>();
		this.appointmentTypes= new ArrayList<String>();		
		this.medicalStuff = new ArrayList<MedicalWorker>();
		this.patients = new ArrayList<Patient>();
		this.rooms = new ArrayList<Room>();
		this.availableAppointments = new ArrayList<Checkup>();	
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ArrayList<ClinicAdministrator> getClAdmins() {
		return clAdmins;
	}

	public void setClAdmins(ArrayList<ClinicAdministrator> clAdmins) {
		this.clAdmins = clAdmins;
	}

	public ArrayList<String> getAppointmentTypes() {
		return appointmentTypes;
	}

	public void setAppointmentTypes(ArrayList<String> appointmentTypes) {
		this.appointmentTypes = appointmentTypes;
	}

	public ArrayList<MedicalWorker> getMedicalStuff() {
		return medicalStuff;
	}

	public void setMedicalStuff(ArrayList<MedicalWorker> medicalStuff) {
		this.medicalStuff = medicalStuff;
	}

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public ArrayList<Checkup> getAvailableAppointments() {
		return availableAppointments;
	}

	public void setAvailableAppointments(ArrayList<Checkup> availableAppointments) {
		this.availableAppointments = availableAppointments;
	}

	public HashMap<String, Double> getPricelist() {
		return pricelist;
	}

	public void setPricelist(HashMap<String, Double> pricelist) {
		this.pricelist = pricelist;
	}
	
	
	
	
}
