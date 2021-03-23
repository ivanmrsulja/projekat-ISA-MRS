package rest.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Notifikacija {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nazivPreparata", nullable = false)
	private String nazivPreparata;
	@Column(name = "datum", nullable = false)
	private LocalDate datum;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "apoteka_id", referencedColumnName = "id")
	private Apoteka apoteka;
	@Column(name = "pregledana", nullable = false)
	private Boolean pregledana;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Korisnik korisnik;
	
	public Notifikacija() {}

	public Notifikacija(String nazivPreparata, LocalDate datum, Apoteka apoteka, Korisnik korisnik) {
		super();
		this.nazivPreparata = nazivPreparata;
		this.datum = datum;
		this.apoteka = apoteka;
		this.korisnik = korisnik;
		this.pregledana=false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
