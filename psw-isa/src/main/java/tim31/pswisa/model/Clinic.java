package tim31.pswisa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Clinic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "clinicName", unique = true, nullable = false)
	private String name;

	@Column(name = "city", unique = false, nullable = false)
	private String city;

	@Column(name = "address", unique = true, nullable = false)
	private String address;

	@Column(name = "rating", unique = false, nullable = true)
	private int rating;
	
	@Column(name="description", unique = false, nullable = false)
	private String description;

	@JsonManagedReference(value="admin_clinic_mov")
	@OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ClinicAdministrator> clAdmins;

	@JsonManagedReference(value="clinic_movement")
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<MedicalWorker> medicalStuff;

	//@JsonManagedReference(value="clinic_movement")
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Patient> patients;

	@JsonManagedReference(value="room_mov")
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Room> rooms = new HashSet<Room>();
	
	@JsonBackReference(value="type_mov")
	@ManyToMany(mappedBy="clinics")
	private Set<CheckUpType> checkUpTypes;

	// appointment for one click
	@JsonManagedReference(value="checkup_mov")
	@OneToMany(mappedBy = "clinic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Checkup> availableAppointments;

	/*
	 * key - tip pregleda value - cena za taj tip pregleda
	 */
	// private HashMap<String, Double> pricelist;

	public Clinic() {
		super();

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

	/*public Set<ClinicAdministrator> getClAdmins() {
		return clAdmins;
	}

	public void setClAdmins(Set<ClinicAdministrator> clAdmins) {
		this.clAdmins = clAdmins;
	}*/

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

	public Set<CheckUpType> getCheckUpTypes() {
		return checkUpTypes;
	}

	public void setCheckUpTypes(Set<CheckUpType> checkUpTypes) {
		this.checkUpTypes = checkUpTypes;
	}
	
	
	
	/*
	 * public HashMap<String, Double> getPricelist() { return pricelist; }
	 * 
	 * public void setPricelist(HashMap<String, Double> pricelist) { this.pricelist
	 * = pricelist; }
	 */
}
