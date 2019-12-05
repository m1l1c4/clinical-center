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
	
	@JsonManagedReference(value="medworker_movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;	
	
	@JsonBackReference(value="clinic_movement")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;	
	
	@Column(name="phone", unique=false, nullable=true)
	private int phone;
	
	//@JsonManagedReference(value="mw_movement")
	@OneToMany(mappedBy="mw", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Patient> patients;	
	
	@OneToMany(mappedBy="mw", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Absence> hollydays;
	
	// just for doctors
	
	@Column(name="rating", unique=false, nullable=true)
	private int rating;
	
	@Column(name="startHr", unique=false, nullable=true)
	private int startHr;	
	
	@Column(name="endHr", unique=false, nullable=true)
	private int endHr;		
	
	@Column(name="typeOfDoctor", unique=false, nullable=false)
	private String type;
	
	@JsonManagedReference(value="doktor_mov")
	@OneToMany(mappedBy = "medicalWorker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Checkup> checkUps = new HashSet<Checkup>();
	
	// just for nurse
	
	/*@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Recipe> receipts; */
	
	
	
	public MedicalWorker() {
		super();
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Set<Absence> getHollydays() {
		return hollydays;
	}

	public void setHollydays(Set<Absence> hollydays) {
		this.hollydays = hollydays;
	}

	
	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
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

//	public Set<Patient> getPatients() {
//		return patients;
//	}

	//public void setPatients(Set<Patient> patients) {
	//	this.patients = patients;
//	}

	// public Set<Absence> getHollydays() {
	//	return hollydays;
	// }

	public void setType(String type) {
		this.type = type;
	}
	
	// public void setHollydays(Set<Absence> hollydays) {
	// 	this.hollydays = hollydays;
	// }

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

	/*public Set<Recipe> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Recipe> receipts) {
		this.receipts = receipts;
	}*/
	
	
	
	
	
	
	
	
}
