package rest.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Pregled {
	private int id;
	private String izvjestaj;
	private StatusPregleda status;
	private TipPregleda tip;
	private LocalDate datum;
	private Time vrijeme;
	private int trajanje;
	private double cijena;
	
	private Korisnik zaposleni;
	private Pacijent pacijent;
	private Apoteka apoteka;
	private Set<Preparat> terapija;
	
	public Pregled() {
		this.terapija = new HashSet<Preparat>();
	}
	
	public Pregled(int id,String izvjestaj, StatusPregleda status, TipPregleda tip, LocalDate datum, Time vrijeme, int trajanje,
			double cijena, Korisnik zaposleni, Pacijent pacijent, Apoteka apoteka) {
		this();
		this.id=id;
		this.izvjestaj = izvjestaj;
		this.status = status;
		this.tip = tip;
		this.datum = datum;
		this.vrijeme = vrijeme;
		this.trajanje = trajanje;
		this.cijena = cijena;
		this.zaposleni = zaposleni;
		this.pacijent = pacijent;
		this.apoteka = apoteka;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Korisnik getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(Korisnik zaposleni) {
		this.zaposleni = zaposleni;
	}

	public String getIzvjestaj() {
		return izvjestaj;
	}

	public void setIzvjestaj(String izvjestaj) {
		this.izvjestaj = izvjestaj;
	}

	public StatusPregleda getStatus() {
		return status;
	}

	public void setStatus(StatusPregleda status) {
		this.status = status;
	}

	public TipPregleda getTip() {
		return tip;
	}

	public void setTip(TipPregleda tip) {
		this.tip = tip;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Time getVrijeme() {
		return vrijeme;
	}

	public void setVrijeme(Time vrijeme) {
		this.vrijeme = vrijeme;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Set<Preparat> getTerapija() {
		return terapija;
	}

	public void setTerapija(Set<Preparat> terapija) {
		this.terapija = terapija;
	}
	
	
}
