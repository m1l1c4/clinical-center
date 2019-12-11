package tim31.pswisa.dto;

import java.util.HashSet;
import java.util.Set;

import tim31.pswisa.model.Absence;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.MedicalWorker;
import tim31.pswisa.model.Patient;
import tim31.pswisa.model.User;

public class MedicalWorkerDTO {

	private Long id;
	private User user;
	private Clinic clinic;
	private String phone;
	private Set<Patient> patients;
	private Set<Absence> hollydays;
	private int rating;
	private int startHr;
	private int endHr;
	private String type;
	private Set<Checkup> checkUps = new HashSet<Checkup>();

	public MedicalWorkerDTO(MedicalWorker m) {
		this(m.getId(), m.getUser(), m.getClinic(), m.getPhone(), m.getPatients(), m.getHollydays(), m.getRating(),
				m.getStartHr(), m.getEndHr(), m.getType(), m.getCheckUps());
	}

	public MedicalWorkerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalWorkerDTO(Long id, User user, Clinic clinic, String phone, Set<Patient> patients,
			Set<Absence> hollydays, int rating, int startHr, int endHr, String type, Set<Checkup> checkUps) {
		super();
		this.id = id;
		this.user = user;
		this.clinic = clinic;
		this.phone = phone;
		this.patients = patients;
		this.hollydays = hollydays;
		this.rating = rating;
		this.startHr = startHr;
		this.endHr = endHr;
		this.type = type;
		this.checkUps = checkUps;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Checkup> getCheckUps() {
		return checkUps;
	}

	public void setCheckUps(Set<Checkup> checkUps) {
		this.checkUps = checkUps;
	}

}
