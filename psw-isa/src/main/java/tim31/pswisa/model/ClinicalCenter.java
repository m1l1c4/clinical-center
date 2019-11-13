package tim31.pswisa.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClinicalCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "mainCenter" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Clinic> clinics;
	
	@OneToMany(mappedBy = "mainCenter" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<User> users;
	
	/*
	@Column(name = "codebook", nullable = false)
	private Codebook codebook;
	*/
	
	public ClinicalCenter() {
		clinics = new ArrayList<Clinic>();
	}
	
	public ClinicalCenter(ArrayList<Clinic> clinics, ArrayList<User> users, Codebook codebook) {
		super();
		this.clinics = clinics;
		this.users = users;
		//this.codebook = codebook;
	}

	public ArrayList<Clinic> getClinics() {
		return clinics;
	}
	public void setClinics(ArrayList<Clinic> clinics) {
		this.clinics = clinics;
	}
	/*
	public Codebook getCodebook() {
		return codebook;
	}
	public void setCodebook(Codebook codebook) {
		this.codebook = codebook;
	}
	*/
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
}
