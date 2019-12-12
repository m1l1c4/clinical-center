package tim31.pswisa.dto;

import tim31.pswisa.model.Recipe;

public class RecipeDTO {

	private Long id;
	private CodebookDTO code;
	private ReportDTO report;
	private Boolean verified;

	public RecipeDTO(Recipe r) {
		this(r.getId(), new CodebookDTO(r.getCode()), new ReportDTO(r.getReport()), r.getVerified());
	}

	public RecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecipeDTO(Long id, CodebookDTO code, ReportDTO report, Boolean verified) {
		super();
		this.id = id;
		this.code = code;
		this.report = report;
		this.verified = verified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CodebookDTO getCode() {
		return code;
	}

	public void setCode(CodebookDTO code) {
		this.code = code;
	}

	public ReportDTO getReport() {
		return report;
	}

	public void setReport(ReportDTO report) {
		this.report = report;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

}
