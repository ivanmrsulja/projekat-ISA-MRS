package rest.dto;

import rest.domain.NaruceniProizvod;

public class NaruceniProizvodDTO {

	private int id;
	private int kolicina;
	private String preparat;

	public NaruceniProizvodDTO() {}
	
	public NaruceniProizvodDTO(NaruceniProizvod np) {
		this.id = np.getId();
		this.kolicina = np.getKolicina();
		this.preparat = np.getPreparat().getNaziv();
	}

	public NaruceniProizvodDTO(int id, int kolicina, String preparat) {
		super();
		this.id = id;
		this.kolicina = kolicina;
		this.preparat = preparat;
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
	@Override
	public String toString() {
		return "NaruceniProizvodDTO [id=" + id + ", kolicina=" + kolicina + ", preparat=" + preparat + "]";
	}

}
