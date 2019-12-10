package tim31.pswisa.dto;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.Room;

public class CheckupDTO {

	private Long id;
	private double discount;
	private boolean scheduled;
	private String date;
	private String time;
	private String type;
	private int duration;
	private double price;
	private Room room;
	private Patient patient;
	private Clinic clinic;
	private MedicalWorker medicalWorker;
	private CheckUpType checkUpType;

	public CheckupDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckupDTO(Checkup c) {
		this(c.getId(), c.getDiscount(), c.isScheduled(), c.getDate(), c.getTime(), c.getType(), c.getDuration(),
				c.getPrice(), c.getRoom(), c.getPatient(), c.getClinic(), c.getMedicalWorker(), c.getCheckUpType());
	}

	public CheckupDTO(Long id, double discount, boolean scheduled, String date, String time, String type, int duration,
			double price, Room room, Patient patient, Clinic clinic, MedicalWorker medicalWorker,
			CheckUpType checkUpType) {
		super();
		this.id = id;
		this.discount = discount;
		this.scheduled = scheduled;
		this.date = date;
		this.time = time;
		this.type = type;
		this.duration = duration;
		this.price = price;
		this.room = room;
		this.patient = patient;
		this.clinic = clinic;
		this.medicalWorker = medicalWorker;
		this.checkUpType = checkUpType;
	}

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

}
