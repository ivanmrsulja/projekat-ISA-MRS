package rest.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Pregled {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "izvjestaj", nullable = false)
	private String izvjestaj;
	@Column(name = "status", nullable = false)
	private StatusPregleda status;
	@Column(name = "tip", nullable = false)
	private TipPregleda tip;
	@Column(name = "datum", nullable = false)
	private LocalDate datum;
	@Column(name = "vrijeme", nullable = false)
	private LocalTime vrijeme;
	@Column(name = "trajanje", nullable = false)
	private int trajanje;
	@Column(name = "cijena", nullable = false)
	private double cijena;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Korisnik zaposleni;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<Preparat> terapija;
	
	@Version
	private Long version;
	
	public Pregled() {
		this.terapija = new HashSet<Preparat>();
	}
	
	public Pregled(String izvjestaj, StatusPregleda status, TipPregleda tip, LocalDate datum, LocalTime vrijeme, int trajanje,
			double cijena, Korisnik zaposleni, Pacijent pacijent, Apoteka apoteka) {
		this();
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
}
