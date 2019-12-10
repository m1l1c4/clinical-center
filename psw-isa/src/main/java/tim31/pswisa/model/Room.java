package tim31.pswisa.model;

import java.util.HashSet;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "roomName", unique = true, nullable = false)
	private String name;

	@Column(name = "type", unique = false, nullable = true)
	private String type;

	@Column(name = "isFree", unique = false, nullable = true)
	private boolean isFree;

	@Column(name = "roomNumber", unique = true, nullable = false)
	private int number;

	@JsonManagedReference(value = "soba_mov")
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Checkup> bookedCheckups = new HashSet<Checkup>();

	@JsonBackReference(value = "room_mov")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;

	public Room() {
		super();
		bookedCheckups = new HashSet<Checkup>();
	}

	public Room(Long id, String name, String type, boolean isFree, int number, Clinic clinic) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.isFree = isFree;
		this.number = number;
		this.clinic = clinic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Checkup> getBookedCheckups() {
		return bookedCheckups;
	}

	public void setBookedCheckups(Set<Checkup> bookedCheckups) {
		this.bookedCheckups = bookedCheckups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}
