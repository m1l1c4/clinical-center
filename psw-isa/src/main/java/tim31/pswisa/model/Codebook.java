package tim31.pswisa.model;


import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


public class Codebook {

	
	private HashMap<String, String> codebook;
	private ArrayList<String> type; // type moze imati vrijednosti D(diagnosis) ili M(medicine)
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@Column(name = "code", unique = true, nullable = false)
	private String code;

	@Column(name = "code_type", unique = false, nullable = false)
	private String type;


	public Codebook() {
		codebook = new HashMap<String, String>();
		type = new ArrayList<String>();
	}

	
	public Codebook(HashMap<String, String> codebook, ArrayList<String> type) {
		super();
		this.codebook = codebook;
		this.type = type;
	}
	public HashMap<String, String> getCodebook() {
		return codebook;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;

	}
	public void setCodebook(HashMap<String, String> codebook) {
		this.codebook = codebook;
	}
	public ArrayList<String> getType() {
		return type;
	}
	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
