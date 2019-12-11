package tim31.pswisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.CodebookDTO;
import tim31.pswisa.model.Codebook;
import tim31.pswisa.repository.CodebookRepository;

@Service
public class CodebookService {

	@Autowired
	private CodebookRepository codebookRepository;

	public List<Codebook> findAll() {
		return codebookRepository.findAll();
	}

	public Codebook findOneByCode(String code) {
		return codebookRepository.findOneByCode(code);
	}

	public Codebook save(CodebookDTO c) {
		Codebook codebook = new Codebook();
		codebook.setName(c.getName());
		codebook.setCode(c.getCode());
		codebook.setType(c.getType());

		Codebook code = findOneByCode(codebook.getCode());
		if (code != null) {
			return null;
		}

		return codebookRepository.save(codebook);
	}

	public void remove(String code) {
		codebookRepository.delete(findOneByCode(code));
	}
}
