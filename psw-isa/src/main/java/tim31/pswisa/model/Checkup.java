package tim31.pswisa.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Checkup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "discount", nullable = true)
	private double discount;

	@Column(name = "scheduled", nullable = false)
	private boolean scheduled;

	@Column(name = "DateOfCheckup", nullable = true)
	private LocalDate date;

	@Column(name = "TimeOfCheckup", nullable = true)
	private String time;

	// operation or appointment
	@Column(name = "tip", nullable = true)
	private String tip;

	@Column(name = "duration", nullable = true)
	private int duration;

	@Column(name = "price", nullable = true)
	private double price;

	@JsonBackReference(value = "soba_mov")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Room room;

	@JsonBackReference(value = "checkup_patient_movement")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;

	@JsonBackReference(value = "checkup_mov")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Clinic clinic;

	//@JsonBackReference(value = "doctor_checkup_mov")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "DOCTOR_AND_CHECKUP", joinColumns = {
			@JoinColumn(name = "checkup_id") }, inverseJoinColumns = { @JoinColumn(name = "medical_worker_id") })
	private Set<MedicalWorker> doctors = new HashSet<MedicalWorker>();
	
	@JsonBackReference(value = "checkup")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private CheckUpType checkUpType;

	@JsonBackReference(value = "checkup_report_mov")
	@OneToOne(mappedBy = "checkUp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Report report;
	
	/*@JsonBackReference(value = "ca_mov")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ClinicAdministrator clinicAdministrator;*/
	
	@Column(name = "pending", nullable = true)
	private boolean pending;	// T or F depending on whether a patient sent request or not

	public Checkup() {

	}

	public Checkup(double discount, boolean scheduled, LocalDate date, String time, String type, int duration,
			double price, Room room) {
		super();
		this.discount = discount;
		this.scheduled = scheduled;
		this.date = date;
		this.tip = type;
		this.duration = duration;
		this.price = price;
		this.room = room;
		this.time = time;
	}

//	public MedicalWorker getMedicalWorker() {
//		return medicalWorker;
	// }
//
//	public void setMedicalWorker(MedicalWorker medicalWorker) {
//		this.medicalWorker = medicalWorker;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public CheckUpType getCheckUpType() {
		return checkUpType;
	}

	public void setCheckUpType(CheckUpType checkUpType) {
		this.checkUpType = checkUpType;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String type) {
		this.tip = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}


	public boolean getPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}
	

	public Set<MedicalWorker> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<MedicalWorker> doctors) {
		this.doctors = doctors;
	}


}
