package tim31.pswisa.dto;

import lombok.Getter;
import lombok.Setter;
import tim31.pswisa.model.Codebook;

@Getter @Setter
public class CodebookDTO {

	private Long id;
	private String name;
	private String code;
	private String type;
	
	public CodebookDTO() {
		super();
	}

	public CodebookDTO(Codebook codebook) {
		this(codebook.getId(), codebook.getName(), codebook.getCode(), codebook.getType());
	}

	public CodebookDTO(Long id, String name, String code, String type) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.type = type;
	}

}
