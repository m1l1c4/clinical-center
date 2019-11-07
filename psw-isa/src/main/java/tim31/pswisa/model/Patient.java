package tim31.pswisa.model;

import java.util.ArrayList;

public class Patient {
	private String email;
	private String password;	//pass hesirati
	private String name;
	private String surname;
	private String phoneNumber;
	private String jbo;
	private String city;
	private String state;
	private String adress;	//ulica i broj
	private Codebook cb; 	//zdravstveni karton
	
	private ArrayList<Checkup> appointments;
	private ArrayList<Operation> operations;
	
	public Patient() {
		super();
		this.appointments = new ArrayList<Checkup>();
		this.operations = new ArrayList<Operation>();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

	public Codebook getCb() {
		return cb;
	}

	public void setCb(Codebook cb) {
		this.cb = cb;
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
