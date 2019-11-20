package tim31.pswisa.model;

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
	
	@Column(name = "address", nullable = false)
	private String address;	// ulica i broj
	
	/*@OneToOne(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord; 	// zdravstveni karton
	*/
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ClinicalCenterAdministrator ccAdmin;
	
	@OneToMany(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Checkup> appointments;
	
	/*@OneToMany(mappedBy = "patient" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Operation> operations;*/
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Clinic clinic;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalWorker mw;
	
	public Patient() {
		super();		
		//this.operations = new ArrayList<Operation>();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}*/

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Checkup> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Checkup> appointments) {
		this.appointments = appointments;
	}

	/*public ArrayList<Operation> getOperations() {
		return operations;
	}

	public void setOperations(ArrayList<Operation> operations) {
		this.operations = operations;
	}*/

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClinicalCenterAdministrator getCcAdmin() {
		return ccAdmin;
	}

	public void setCcAdmin(ClinicalCenterAdministrator ccAdmin) {
		this.ccAdmin = ccAdmin;
	}

	public MedicalWorker getMw() {
		return mw;
	}

	public void setMw(MedicalWorker mw) {
		this.mw = mw;
	}
	
	
	
	
}
