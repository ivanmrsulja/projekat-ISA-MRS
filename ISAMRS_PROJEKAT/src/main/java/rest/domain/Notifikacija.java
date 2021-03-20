package rest.domain;

import java.time.LocalDate;

public class Notifikacija {
	private int id;
	private String nazivPreparata;
	private LocalDate datum;
	private Apoteka apoteka;
	private Boolean pregledana;
	
	private Korisnik korisnik;
	
	public Notifikacija() {}

	public Notifikacija(int id,String nazivPreparata, LocalDate datum, Apoteka apoteka, Korisnik korisnik) {
		super();
		this.id=id;
		this.nazivPreparata = nazivPreparata;
		this.datum = datum;
		this.apoteka = apoteka;
		this.korisnik = korisnik;
		this.pregledana=false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazivPreparata() {
		return nazivPreparata;
	}

	public void setNazivPreparata(String nazivPreparata) {
		this.nazivPreparata = nazivPreparata;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Boolean getPregledana() {
		return pregledana;
	}

	public void setPregledana(Boolean pregledana) {
		this.pregledana = pregledana;
	}
		
}
