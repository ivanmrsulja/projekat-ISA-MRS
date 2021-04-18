package rest.dto;

import rest.domain.Apoteka;
import rest.domain.Lokacija;

public class ApotekaDTO {

	private String naziv;
	private String opis;
	private double ocena;
	private int id;
	private Lokacija lokacija;
	private double cena;

	public ApotekaDTO() {}
	
	public ApotekaDTO(Apoteka apoteka)
	{
		this.naziv = apoteka.getNaziv();
		this.opis = apoteka.getOpis();
		this.ocena = apoteka.getOcena();
		this.id = apoteka.getId();
		this.lokacija = apoteka.getLokacija();
		this.cena = apoteka.getCenaSavetovanja();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
}
