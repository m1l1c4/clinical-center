package tim31.pswisa.model;

import java.util.ArrayList;

public class ClinicalCenterAdministrator {
	
	private User user;
	private ArrayList<Patient> requests;
	
	public ClinicalCenterAdministrator() {
		requests = new ArrayList<Patient>();
	}

	public ArrayList<Patient> getRequests() {
		return requests;
	}

	public void setRequests(ArrayList<Patient> requests) {
		this.requests = requests;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
