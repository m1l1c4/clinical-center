package tim31.pswisa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Absence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email; // to know who sends request

	@Column(name = "start", nullable = false)
	private Date start;

	@Column(name = "duration", nullable = false)
	private int duration;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ClinicAdministrator clinicAdministrator;

	// @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalWorker mw;

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

	public ClinicAdministrator getClinicAdministrator() {
		return clinicAdministrator;
	}

	public void setClinicAdministrator(ClinicAdministrator clinicAdministrator) {
		this.clinicAdministrator = clinicAdministrator;
	}

}
