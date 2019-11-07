package tim31.pswisa.model;

import java.util.Date;

public class Checkup {

	private double discount; //popust
	private boolean scheduled; //zakazan
	private Date date; //datum i vrijeme
	private String type; //tip pregleda
	private int duration; // trajanje 
	private double price;
	private MedicalWorker doctor;
	private Room room;
	
	public Checkup() {
		
	}
	
	public Checkup(double discount, boolean scheduled, Date date, String type, int duration, double price, MedicalWorker doctor, Room room) {
		super();
		this.discount = discount;
		this.scheduled = scheduled;
		this.date = date;
		this.type = type;
		this.duration = duration;
		this.price = price;
		this.doctor = doctor;
		this.room = room;
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
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
	
	public MedicalWorker getDoctor() {
		return doctor;
	}
	
	public void setDoctor(MedicalWorker doctor) {
		this.doctor = doctor;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	
	
}
