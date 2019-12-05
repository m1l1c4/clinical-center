package tim31.pswisa.model;

import java.util.Date;
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
	
	@Column(name = "DateOfCheckup", nullable = false)
	private String date; 
	
	@Column(name = "TimeOfCheckup", nullable = false)
	private String time; 
	
	// operation or appointment
	@Column(name = "type", nullable = false)
	private String type; 
	
	@Column(name = "duration", nullable = false)
	private int duration; 
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MedicalWorker doctor;
	
	@JsonBackReference(value="soba_mov")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Room room;
	
	//@JsonBackReference(value="cup_movement")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	
	@JsonBackReference(value="checkup_mov")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;
	
	@JsonBackReference(value="doktor_mov")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MedicalWorker medicalWorker;
	

	@JsonBackReference(value="checkup")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CheckUpType checkUpType;
	
	public Checkup() {
		
	}
	
	public Checkup(double discount, boolean scheduled, String date, String time, String type, int duration, double price, Room room) {
		super();
		this.discount = discount;
		this.scheduled = scheduled;
		this.date = date;
		this.type = type;
		this.duration = duration;
		this.price = price;
		this.room = room;
		this.time = time;
	}

	
//	public MedicalWorker getMedicalWorker() {
//		return medicalWorker;
	//}
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

	public MedicalWorker getDoctors() {
		return doctor;
	}

	public void setDoctor(MedicalWorker doctor) {
		this.doctor = doctor;
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
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
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

	public MedicalWorker getMedicalWorker() {
		return medicalWorker;
	}

	public void setMedicalWorker(MedicalWorker medicalWorker) {
		this.medicalWorker = medicalWorker;
	}

	public CheckUpType getCheckUpType() {
		return checkUpType;
	}

	public void setCheckUpType(CheckUpType checkUpType) {
		this.checkUpType = checkUpType;
	}

	public MedicalWorker getDoctor() {
		return doctor;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
	
	
	
	
}
