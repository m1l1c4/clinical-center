package tim31.pswisa.dto;

import java.util.HashSet;
import java.util.Set;

import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;

public class CheckUpTypeDTO {

	private Long id;
	private String name;
	private Set<Clinic> clinics = new HashSet<Clinic>();
	private Set<Checkup> checkups = new HashSet<Checkup>();

	public CheckUpTypeDTO(CheckUpType c) {
		this(c.getId(), c.getName(), c.getClinics(), c.getCheckups());
	}

	public CheckUpTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CheckUpTypeDTO(Long id, String name, Set<Clinic> clinics, Set<Checkup> checkups) {
		super();
		this.id = id;
		this.name = name;
		this.clinics = clinics;
		this.checkups = checkups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}

	public Set<Checkup> getCheckups() {
		return checkups;
	}

	public void setCheckups(Set<Checkup> checkups) {
		this.checkups = checkups;
	}

}
