package rest.dto;

import rest.domain.StavkaRecepta;

public class StavkaReceptaDTO {
	private int id;
	private int kolicina;
	private String preparat;
	
	public StavkaReceptaDTO() {}
	public StavkaReceptaDTO(StavkaRecepta sr) {
		id = sr.getId();
		kolicina = sr.getKolicina();
		preparat = sr.getPreparat().getNaziv();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public String getPreparat() {
		return preparat;
	}
	public void setPreparat(String preparat) {
		this.preparat = preparat;
	}
}
