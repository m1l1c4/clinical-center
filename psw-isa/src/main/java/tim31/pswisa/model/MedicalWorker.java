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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class MedicalWorker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonManagedReference(value = "medworker_movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;

	@JsonBackReference(value = "clinic_movement")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Clinic clinic;

	@Column(name = "phone", unique = false, nullable = true)
	private String phone;

	/*
	// @JsonManagedReference(value="mw_movement")
	@OneToMany(mappedBy = "mw", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Patient> patients;
	*/
	@JsonManagedReference(value = "vacation")
	@OneToMany(mappedBy = "mw", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Absence> hollydays;
	
	@JsonManagedReference(value = "nurse_recipe_mov")
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recipe> recipes;

	// just for doctors

	@Column(name = "rating", unique = false, nullable = true)
	private int rating;

	@Column(name = "startHr", unique = false, nullable = true)
	private int startHr;

	@Column(name = "endHr", unique = false, nullable = true)
	private int endHr;

	@Column(name = "typeOfDoctor", unique = false, nullable = true)
	private String type;

	@JsonManagedReference(value = "doctor_checkup_mov")
	@ManyToMany(mappedBy = "doctors")
	private Set<Checkup> checkUps = new HashSet<Checkup>();

	// just for nurse
	// @ManyToMany(mappedBy = "medicalWorkers")
	// private Set<Recipe> receipts = new HashSet<Recipe>();

	public MedicalWorker() {
		super();
	}

	public MedicalWorker(MedicalWorker m) {
		this.id = m.id ;
		this.checkUps = m.getCheckUps();
		this.clinic = m.clinic ;
		this.endHr  = m.endHr;
		this.hollydays = m.hollydays;
		this.phone = m.phone;
		this.rating = m.rating;
		this.recipes  = m.recipes;
		this.startHr = m.startHr ;
		this.type = m.type ;
		this.user = m.user ;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Checkup> getCheckUps() {
		return checkUps;
	}

	public void setCheckUps(Set<Checkup> checkUps) {
		this.checkUps = checkUps;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public String getType() {
		return type;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	/*
	 * public Set<Patient> getPatients() { return patients; }
	 * 
	 * public void setPatients(Set<Patient> patients) { this.patients = patients; }
	 */

	public Set<Absence> getHollydays() {
		return hollydays;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setHollydays(Set<Absence> hollydays) {
		this.hollydays = hollydays;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getStartHr() {
		return startHr;
	}

	public void setStartHr(int startHr) {
		this.startHr = startHr;
	}

	public int getEndHr() {
		return endHr;
	}

	public void setEndHr(int endHr) {
		this.endHr = endHr;
	}

	
	

}