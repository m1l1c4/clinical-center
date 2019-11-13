package tim31.pswisa.model;

import java.util.ArrayList;

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


@Entity
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User user;	
	
	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;
	
	@Column(name = "jbo", nullable = false)
	private String jbo;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@Column(name = "adress", nullable = false)
	private String adress;	// ulica i broj
	
	@OneToOne(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord; 	// zdravstveni karton
	
	@OneToMany(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Checkup> appointments;
	
	@OneToMany(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Operation> operations;
	
	public Patient() {
		super();
		this.appointments = new ArrayList<Checkup>();
		this.operations = new ArrayList<Operation>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJbo() {
		return jbo;
	}

	public void setJbo(String jbo) {
		this.jbo = jbo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public ArrayList<Checkup> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Checkup> appointments) {
		this.appointments = appointments;
	}

	public ArrayList<Operation> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Operation> operations) {
		this.operations = operations;
	}	
	
	
}
