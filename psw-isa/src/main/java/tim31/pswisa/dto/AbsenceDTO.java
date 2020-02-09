package tim31.pswisa.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import tim31.pswisa.model.Absence;

@Getter
@Setter
public class AbsenceDTO {

	private Long id;
	private LocalDate startVacation;
	private LocalDate endVacation;
	private ClinicDTO clinicOfAbsence;
	private MedicalWorkerDTO medicalWorker;
	private String typeOfAbsence;
	private String accepted;

	public AbsenceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbsenceDTO(Absence a) {
		this(a.getId(), a.getStartVacation(), a.getEndVacation(), new ClinicDTO(a.getClinicOfAbsence()),
				new MedicalWorkerDTO(a.getMedicalWorker()), a.getTypeOfAbsence(), a.getAccepted());
	}

	public AbsenceDTO(Long id, LocalDate start, LocalDate end, ClinicDTO clinicDTO, MedicalWorkerDTO medicalWorker2,
			String t, String a) {
		super();
		this.id = id;
		this.startVacation = start;
		this.endVacation = end;
		this.clinicOfAbsence = clinicDTO;
		this.medicalWorker = medicalWorker2;
		this.typeOfAbsence = t;
		this.accepted = a;
	}

}
