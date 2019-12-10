package tim31.pswisa.dto;

import java.util.HashSet;
import java.util.Set;

import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.Room;

public class RoomDTO {

	private Long id;
	private String name;
	private String type;
	private boolean isFree;
	private int number;
	private Set<Checkup> bookedCheckups = new HashSet<Checkup>();
	private Clinic clinic;

	public RoomDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomDTO(Room r) {
		this(r.getId(), r.getName(), r.getType(), r.isFree(), r.getNumber(), r.getBookedCheckups(), r.getClinic());
	}

	public RoomDTO(Long id, String name, String type, boolean isFree, int number, Set<Checkup> bookedCheckups,
			Clinic clinic) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.isFree = isFree;
		this.number = number;
		this.bookedCheckups = bookedCheckups;
		this.clinic = clinic;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}
