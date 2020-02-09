package tim31.pswisa.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Absence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "startVacation")
	private LocalDate startVacation;

	@Column(name = "endVacation")
	private LocalDate endVacation;

	@JsonBackReference(value = "clinicAbsence_mov")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinicOfAbsence;

	@JsonBackReference(value = "vacation")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalWorker medicalWorker;

	@Column(name = "typeOfAbsence")
	private String typeOfAbsence;

	@Column(name = "accepted")
	private String accepted;

	public Absence() {

	}

	public Absence(LocalDate start, LocalDate end, MedicalWorker mw, String t, Clinic c, String a) {
		super();
		this.startVacation = start;
		this.endVacation = end;
		this.medicalWorker = mw;
		this.clinicOfAbsence = c;
		this.typeOfAbsence = t;
		this.accepted = a;
	}

}
