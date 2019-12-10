package tim31.pswisa.dto;

import java.util.Set;

import tim31.pswisa.model.Absence;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;

public class ClinicAdministratorDTO {

	private Long id;
	private User user;
	private Clinic clinic;
	private Set<Absence> absences;

	public ClinicAdministratorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicAdministratorDTO(Long id, User user, Clinic clinic, Set<Absence> absences) {
		super();
		this.id = id;
		this.user = user;
		this.clinic = clinic;
		this.absences = absences;
	}

	public ClinicAdministratorDTO(ClinicAdministrator c) {
		this(c.getId(), c.getUser(), c.getClinic(), c.getAbsences());
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

	public Set<Absence> getAbsences() {
		return absences;
	}

	public void setAbsences(Set<Absence> absences) {
		this.absences = absences;
	}

}
