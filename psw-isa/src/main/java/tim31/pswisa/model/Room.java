package tim31.pswisa.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "roomName", unique = true, nullable = false)
	private String name;
	
	@Column(name = "type", unique = false, nullable = false)
	private String type;
	
	@Column(name = "isFree", unique = false, nullable = false)
	private boolean isFree;
	
	@Column(name = "roomNumber", unique = true, nullable = false)
	private int number; 
	
	@OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Checkup> bookedCheckups;

	public Room() {
		super();
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

}
