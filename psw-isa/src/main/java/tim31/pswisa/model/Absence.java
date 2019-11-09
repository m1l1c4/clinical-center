package tim31.pswisa.model;

import java.util.Date;

public class Absence {

	private String email; // to know who sends request
	private Date start;
	private int duration;
	
	public Absence() {
		
	}

	public Absence(String email, Date start, int duration) {
		super();
		this.email = email;
		this.start = start;
		this.duration = duration;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
