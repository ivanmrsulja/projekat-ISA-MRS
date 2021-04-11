package rest.dto;

import rest.domain.Apoteka;

public class CenaDTO {
	private ApotekaDTO apoteka;
	private double cena;
	
	public CenaDTO() {}
	public CenaDTO(Apoteka a, double c) {
		this.apoteka = new ApotekaDTO(a);
		this.cena = c;
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
}
