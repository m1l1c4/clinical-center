package tim31.pswisa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Report;
import tim31.pswisa.service.CheckUpService;
import tim31.pswisa.service.ReportService;

@RestController
@RequestMapping(value = "/checkup")
public class CheckupController {

	@Autowired
	private CheckUpService checkupService;

	@Autowired
	private ReportService reportService;

	@PostMapping(value = "/addReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Report> saveClinic(@RequestBody Report r) {
		Report report = reportService.add(r);

		if (report == null)
			return new ResponseEntity<>(report, HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(report, HttpStatus.CREATED);
	}

}
