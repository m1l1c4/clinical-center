package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Codebook;
import tim31.pswisa.repository.CodebookRepository;

@Service
public class CodebookService {

	@Autowired
	private CodebookRepository codebookRepository;

	public List<Codebook> findAll() {
		return codebookRepository.findAll();
	}

	public Codebook save(Codebook codebook) {
		List<Codebook> codebooks = codebookRepository.findAll();

		for (Codebook c : codebooks) {
			if (c.getCode().equals(codebook.getCode()))
				return null;
		}

		return codebookRepository.save(codebook);
	}

	public void remove(String code) {
		codebookRepository.delete(codebookRepository.findOneByCode(code));
	}
}
