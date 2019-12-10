package tim31.pswisa.dto;

import java.util.ArrayList;

public class ClinicDTO {
	private Long id;
	private String name;
	private String city;
	private String address;
	private int rating;
	private ArrayList<String> checkupTypes;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ArrayList<String> getCheckupTypes() {
		return checkupTypes;
	}

	public void setCheckupTypes(ArrayList<String> checkupTypes) {
		this.checkupTypes = checkupTypes;
	}

	public ClinicDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClinicDTO(Long id, String name, String city, String address, int rating) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.address = address;
		this.rating = rating;
		this.checkupTypes = new ArrayList<String>();
	}

}
