package tim31.pswisa.model;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ClinicAdministrator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	@Column
	private String clinic;
	
	@OneToMany(mappedBy = "clinicAdministrator" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Absence> absences;
	
	public ClinicAdministrator() {
	}
	
	public ClinicAdministrator(String clinic, ArrayList<Absence> absences) {
		super();
		this.clinic = clinic;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getClinic() {
		return clinic;
	}
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}
	///public Set<Absence> getAbsences() {
	//	return absences;
	//}
	//public void setAbsences(Set<Absence> absences) {
	//	this.absences = absences;
	//}
	
	

}
