package tim31.pswisa.dto;

import tim31.pswisa.model.CheckUpType;

public class CheckUpTypeDTO {

	private Long id;
	private String name;

	public CheckUpTypeDTO(CheckUpType c) {
		this(c.getId(), c.getName());
	}

	public CheckUpTypeDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CheckUpTypeDTO() {
		super();
		// TODO Auto-generated constructor stub
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

}