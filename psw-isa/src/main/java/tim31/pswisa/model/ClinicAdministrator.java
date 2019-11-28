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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ClinicAdministrator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonManagedReference(value="cadmin_movement")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
	
	
	@JsonBackReference(value="admin_clinic_mov")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Column
	private Clinic clinic;	
	
	@OneToMany(mappedBy = "clinicAdministrator" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Absence> absences;
	
	public ClinicAdministrator() {
	}
	
	public ClinicAdministrator(Clinic clinic, ArrayList<Absence> absences) {
		super();
		this.clinic = clinic;
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
	///public Set<Absence> getAbsences() {
	//	return absences;
	//}
	//public void setAbsences(Set<Absence> absences) {
	//	this.absences = absences;
	//}
	
	

}
