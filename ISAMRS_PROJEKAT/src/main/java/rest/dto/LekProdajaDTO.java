package rest.dto;

public class LekProdajaDTO {
	private int id;
	private ApotekaDTO apoteka;
	private double cena;
	private String nazivLekova;
	
	public LekProdajaDTO(int id,ApotekaDTO apoteka, double price) {
		super();
		this.id = id;
		this.apoteka = apoteka;
		this.cena = price;
	}
	
	public LekProdajaDTO() {
	}

	public ApotekaDTO getApoteka() {
		return apoteka;
	}

	public void setApoteka(ApotekaDTO apoteka) {
		this.apoteka = apoteka;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazivLekova() {
		return nazivLekova;
	}

	public void setNazivLekova(String nazivLekova) {
		this.nazivLekova = nazivLekova;
	}
	
	
	
	
	
	
	
}
