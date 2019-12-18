package tim31.pswisa.dto;

import tim31.pswisa.model.Checkup;

public class CheckupDTO {

	private Long id;
	private double discount;
	private boolean scheduled;
	private String date;
	private String time;
	private String type;
	private int duration;
	private double price;
	private RoomDTO room;
	private PatientDTO patient;
	private ClinicDTO clinic;
	private MedicalWorkerDTO medicalWorker;
	private CheckUpTypeDTO checkUpType;

	public CheckupDTO(Checkup c) {
		this(c.getId(), c.getDiscount(), c.isScheduled(), c.getDate(), c.getTime(), c.getType(), c.getDuration(),
				c.getDiscount(), new RoomDTO(c.getRoom()), new PatientDTO(c.getPatient()), new ClinicDTO(c.getClinic()),
				new MedicalWorkerDTO(c.getMedicalWorker()), new CheckUpTypeDTO(c.getCheckUpType()));
	}
	
	public CheckupDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckupDTO(Long id, double discount, boolean scheduled, String date, String time, String type, int duration,
			double price, RoomDTO room, PatientDTO patient, ClinicDTO clinic, MedicalWorkerDTO medicalWorker,
			CheckUpTypeDTO checkUpType) {
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

	public RoomDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDTO room) {
		this.room = room;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public ClinicDTO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDTO clinic) {
		this.clinic = clinic;
	}

	public MedicalWorkerDTO getMedicalWorker() {
		return medicalWorker;
	}

	public void setMedicalWorker(MedicalWorkerDTO medicalWorker) {
		this.medicalWorker = medicalWorker;
	}

	public CheckUpTypeDTO getCheckUpType() {
		return checkUpType;
	}

	public void setCheckUpType(CheckUpTypeDTO checkUpType) {
		this.checkUpType = checkUpType;
	}

}