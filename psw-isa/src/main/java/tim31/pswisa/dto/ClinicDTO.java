package tim31.pswisa.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.Room;

public class ClinicDTO {
	private Long id;
	private String name;
	private String city;
	private String address;
	private int rating;
	private String description;
	private Set<ClinicAdministrator> clAdmins;
	private Set<MedicalWorker> medicalStuff;
	private Set<Patient> patients;
	private Set<Room> rooms = new HashSet<Room>();
	private Set<CheckUpType> checkUpTypes;
	private Set<Checkup> availableAppointments;

	public ClinicDTO(Clinic c) {
		this(c.getId(), c.getName(), c.getCity(), c.getAddress(), c.getRating(), c.getDescription(), c.getClAdmins(),
				c.getMedicalStuff(), c.getPatients(), c.getRooms(), c.getCheckUpTypes(), c.getAvailableAppointments());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ClinicAdministrator> getClAdmins() {
		return clAdmins;
	}

	public void setClAdmins(Set<ClinicAdministrator> clAdmins) {
		this.clAdmins = clAdmins;
	}

	public Set<MedicalWorker> getMedicalStuff() {
		return medicalStuff;
	}

	public void setMedicalStuff(Set<MedicalWorker> medicalStuff) {
		this.medicalStuff = medicalStuff;
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Set<CheckUpType> getCheckUpTypes() {
		return checkUpTypes;
	}

	public void setCheckUpTypes(Set<CheckUpType> checkUpTypes) {
		this.checkUpTypes = checkUpTypes;
	}

	public Set<Checkup> getAvailableAppointments() {
		return availableAppointments;
	}

	public void setAvailableAppointments(Set<Checkup> availableAppointments) {
		this.availableAppointments = availableAppointments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ClinicDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicDTO(Long id, String name, String city, String address, int rating, String description,
			Set<ClinicAdministrator> clAdmins, Set<MedicalWorker> medicalStuff, Set<Patient> patients, Set<Room> rooms,
			Set<CheckUpType> checkUpTypes, Set<Checkup> availableAppointments) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.address = address;
		this.rating = rating;
		this.description = description;
		this.clAdmins = clAdmins;
		this.medicalStuff = medicalStuff;
		this.patients = patients;
		this.rooms = rooms;
		this.checkUpTypes = checkUpTypes;
		this.availableAppointments = availableAppointments;
	}

}
