package tim31.pswisa.model;

public class Operation {

	private Patient patient;
	private ArrayList<Doctor>doctors;
	private Room room;
	private int duration; //minute
	
	public Operation() {
		doctors = new ArrayList<Doctor>();
	}

	public Operation(Patient patient, Room room, int duration) {
		super();
		this.patient = patient;
		this.room = room;
		this.duration = duration;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
}
