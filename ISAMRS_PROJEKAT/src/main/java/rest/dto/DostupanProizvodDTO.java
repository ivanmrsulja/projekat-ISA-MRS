package rest.dto;

import rest.domain.DostupanProizvod;

public class DostupanProizvodDTO {

	private int id;
	private int kolicina;
	private double cena;
	private String preparat;

	public DostupanProizvodDTO() {}

	public DostupanProizvodDTO(DostupanProizvod dp) {
		this.id = dp.getId();
		this.kolicina = dp.getKolicina();
		this.cena = dp.getCena();
		this.preparat = dp.getPreparat().getNaziv();
	}
	
	public DostupanProizvodDTO(int id, int kolicina, double cena, String preparat) {
		super();
		this.id = id;
		this.kolicina = kolicina;
		this.cena = cena;
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
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getPreparat() {
		return preparat;
	}
	public void setPreparat(String preparat) {
		this.preparat = preparat;
	}
	@Override
	public String toString() {
		return "DostupanProizvodDTO [id=" + id + ", kolicina=" + kolicina + ", cena=" + cena + ", preparat=" + preparat
				+ "]";
	}

}
