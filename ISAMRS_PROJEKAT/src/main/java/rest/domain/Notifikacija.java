package rest.domain;

import java.util.Date;

public class Notifikacija {
	private Long id;
	private String nazivPreparata;
	private Date datum;
	private Apoteka apoteka;
	private Boolean pregledana;
	
	private Korisnik korisnik;
	
	public Notifikacija() {}

	public Notifikacija(Long id,String nazivPreparata, Date datum, Apoteka apoteka, Korisnik korisnik) {
		super();
		this.id=id;
		this.nazivPreparata = nazivPreparata;
		this.datum = datum;
		this.apoteka = apoteka;
		this.korisnik = korisnik;
		this.pregledana=false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivPreparata() {
		return nazivPreparata;
	}

	public void setNazivPreparata(String nazivPreparata) {
		this.nazivPreparata = nazivPreparata;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
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
