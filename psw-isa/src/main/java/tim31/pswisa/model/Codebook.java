package tim31.pswisa.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Codebook {
	
	private HashMap<String, String> codebook;
	private ArrayList<String> type; // type moze imati vrijednosti D(diagnosis) ili M(medicine)
	
	public Codebook() {
		codebook = new HashMap<String, String>();
		type = new ArrayList<String>();
	}
	
	public Codebook(HashMap<String, String> codebook, ArrayList<String> type) {
		super();
		this.codebook = codebook;
		this.type = type;
	}
	public HashMap<String, String> getCodebook() {
		return codebook;
	}
	public void setCodebook(HashMap<String, String> codebook) {
		this.codebook = codebook;
	}
	public ArrayList<String> getType() {
		return type;
	}
	public void setType(ArrayList<String> type) {
		this.type = type;
	}

}
