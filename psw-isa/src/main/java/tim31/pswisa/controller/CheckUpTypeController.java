package tim31.pswisa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.dto.CheckUpTypeDTO;
import tim31.pswisa.model.CheckUpType;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.ClinicAdministrator;
import tim31.pswisa.model.User;
import tim31.pswisa.security.TokenUtils;
import tim31.pswisa.service.CheckUpTypeService;
import tim31.pswisa.service.ClinicAdministratorService;
import tim31.pswisa.service.ClinicService;
import tim31.pswisa.service.UserService;

@RestController
@RequestMapping(value = "/checkUpType")
public class CheckUpTypeController {

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private UserService userService;

	@Autowired
	private CheckUpTypeService checkUpTypeService;

	@Autowired
	TokenUtils tokenUtils;

	// This method delete type from clinic but type won't be deleted from all types
	// in clinical center
	@PostMapping(value = "/deleteType/{name}")
	public ResponseEntity<String> deleteTypeController(@PathVariable String name, HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				String returnVal = checkUpTypeService.deleteType(name, clinicAdministrator);
				if (returnVal.equals("Obrisano")) {
					return new ResponseEntity<>("Obrisano", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Greska", HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		return new ResponseEntity<>("Greska", HttpStatus.BAD_REQUEST);
	}

	// This method returs all types in clinic center of administrator who is logged
	// in system
	@GetMapping(value = "/getTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<CheckUpTypeDTO>> getTypes(HttpServletRequest request) {
		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				Clinic clinic = clinicService.findOneById(clinicAdministrator.getClinic().getId());
				Set<CheckUpType> lista = clinic.getCheckUpTypes();
				ArrayList<CheckUpTypeDTO> dtos = new ArrayList<CheckUpTypeDTO>();
				for (CheckUpType c : lista) {
					dtos.add(new CheckUpTypeDTO(c));
				}
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// This method adding new type in all type of clinic, checking if there is a
	// type with same name
	@PostMapping(value = "/addType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CheckUpTypeDTO> addTypeController(@RequestBody CheckUpTypeDTO type,
			HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String email = tokenUtils.getUsernameFromToken(token);
		User user = userService.findOneByEmail(email);

		// save types in clinic
		if (user != null) {
			ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByUser(user.getId());
			if (clinicAdministrator != null) {
				CheckUpType temp = checkUpTypeService.addType(type, clinicAdministrator);
				if (temp == null) {
					return new ResponseEntity<>(new CheckUpTypeDTO(temp), HttpStatus.NOT_FOUND);
				} else
					return new ResponseEntity<>(new CheckUpTypeDTO(temp), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/allTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CheckUpType>> allTypes() {
		List<CheckUpType> chTypes = checkUpTypeService.findAll();
		if (chTypes != null) {
			return new ResponseEntity<>(chTypes, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
