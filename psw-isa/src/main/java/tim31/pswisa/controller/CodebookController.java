package tim31.pswisa.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim31.pswisa.model.Codebook;
import tim31.pswisa.model.Patient;
import tim31.pswisa.service.CodebookService;

@RestController
@RequestMapping(value = "/codebook", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodebookController {
	
	@Autowired
	private CodebookService codebookService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveClinic(@RequestBody Codebook c)
	{
		Codebook codebook = new Codebook();
		codebook.setName(c.getName());
		codebook.setCode(c.getCode());
		codebook.setType(c.getType());
		
		codebook = codebookService.save(codebook);
		if (codebook == null)
			return new ResponseEntity<>("Sifra mora biti jedinstvena.", HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>("Uspjesno dodata nova stavka.", HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<Codebook>> getCodebook(){
		List<Codebook> codebook = codebookService.findAll();
		return new ResponseEntity<>(codebook, HttpStatus.OK);
	}
	
	@PostMapping(value="/{code}")
	public ResponseEntity<String> deleteCode(@PathVariable String code){
		codebookService.remove(code);
		return new ResponseEntity<>("Obrisano", HttpStatus.OK);
	}

}
