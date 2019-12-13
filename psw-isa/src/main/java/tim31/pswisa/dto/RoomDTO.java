package tim31.pswisa.dto;

import tim31.pswisa.model.Room;

public class RoomDTO {

	private Long id;
	private String name;
	private String typeRoom;
	private boolean isFree;
	private int number;
	private ClinicDTO clinic;

	public RoomDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomDTO(Room r) {
		this(r.getId(), r.getName(), r.getTypeRoom(), r.isFree(), r.getNumber(), new ClinicDTO(r.getClinic()));
	}

	public RoomDTO(Long id, String name, String type, boolean isFree, int number, ClinicDTO clinic) {
		super();
		this.id = id;
		this.name = name;
		this.typeRoom = type;
		this.isFree = isFree;
		this.number = number;
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

	public String getTypeRoom() {
		return typeRoom;
	}

	public void setTypeRoom(String type) {
		this.typeRoom = type;
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

	public ClinicDTO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDTO clinic) {
		this.clinic = clinic;
	}

}
