package tim31.pswisa.model;

import java.util.ArrayList;
import java.util.Date;


public class MedicalWorker {
	private User user;	
	private Clinic clinic;	//klinika na kojoj je radik zaposlen
	
	private ArrayList<Patient> patients;	//lista pregledanih pacijenata	
	private ArrayList<Absence> hollydays;
	
	// samo za role == doktor
	private int rating;
	private Date startHr;	// pocetak rv
	private Date endHr;		//kraj rv
	private ArrayList<Checkup> finishedApp;		//obavljeni pregledi
	
	//samo za sestru
	private ArrayList<Recipe> receipts; //sestra ima recepte	
	
	
	public MedicalWorker() {
		super();
		this.patients = new ArrayList<Patient>();	//lista pregledanih pacijenata	
		this.hollydays = new ArrayList<Absence>();
		this.finishedApp = new ArrayList<Checkup>();
		this.receipts = new ArrayList<Recipe>();
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

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}

	public ArrayList<Absence> getHollydays() {
		return hollydays;
	}

	public void setHollydays(ArrayList<Absence> hollydays) {
		this.hollydays = hollydays;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getStartHr() {
		return startHr;
	}

	public void setStartHr(Date startHr) {
		this.startHr = startHr;
	}

	public Date getEndHr() {
		return endHr;
	}

	public void setEndHr(Date endHr) {
		this.endHr = endHr;
	}

	public ArrayList<Checkup> getFinishedApp() {
		return finishedApp;
	}

	public void setFinishedApp(ArrayList<Checkup> finishedApp) {
		this.finishedApp = finishedApp;
	}

	public ArrayList<Recipe> getReceipts() {
		return receipts;
	}

	public void setReceipts(ArrayList<Recipe> receipts) {
		this.receipts = receipts;
	}
	
	
	
	
	
	
	
	
}
