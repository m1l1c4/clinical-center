package tim31.pswisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Codebook {

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

	}

	public Codebook(Long id, String name, String code, String type) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.type = type;
	}

}
