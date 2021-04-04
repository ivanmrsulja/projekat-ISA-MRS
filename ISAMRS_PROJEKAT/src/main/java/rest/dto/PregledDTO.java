package rest.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import rest.domain.Pregled;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;

public class PregledDTO {
	private Integer id;
	private String izvjestaj;
	private StatusPregleda status;
	private TipPregleda tip;
	private LocalDate datum;
	private LocalTime vrijeme;
	private int trajanje;
	private double cijena;
	private KorisnikDTO zaposleni;
	private double ocena;
	
	public PregledDTO() {}
	public PregledDTO(Pregled p, double ocena) {
		id = p.getId();
		izvjestaj = p.getIzvjestaj();
		status = p.getStatus();
		tip = p.getTip();
		datum = p.getDatum();
		vrijeme = p.getVrijeme();
		trajanje = p.getTrajanje();
		cijena = p.getCijena();
		zaposleni = new KorisnikDTO(p.getZaposleni());
		this.ocena = ocena;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public LocalTime getVrijeme() {
		return vrijeme;
	}
	public void setVrijeme(LocalTime vrijeme) {
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
	public KorisnikDTO getZaposleni() {
		return zaposleni;
	}
	public void setZaposleni(KorisnikDTO zaposleni) {
		this.zaposleni = zaposleni;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
}
