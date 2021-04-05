package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pacijent extends Korisnik {
	
	@Column(name = "statusNaloga", nullable = true)
	private StatusNaloga statusNaloga;
	
	@Column(name = "brojPoena", nullable = true)
	private int brojPoena;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "tip_korisnika_id", referencedColumnName = "id")
	private TipKorisnika tipKorisnika;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ERecept> eRecepti;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Penal> penali;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Zalba> zalbe;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OcenaApoteke> ocene;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Rezervacija> rezervacije;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "preparat_id", referencedColumnName = "id"))
	private Set<Preparat> alergije;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<Preparat> kupljeniPreparati;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(joinColumns = @JoinColumn(name = "pacijent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "apoteka_id", referencedColumnName = "id"))
	private Set<Apoteka> apoteke;
	
	@OneToMany(mappedBy = "pacijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Pregled> pregledi;
	
	public Pacijent() {
		super();
		this.eRecepti = new HashSet<ERecept>();
		this.penali = new HashSet<Penal>();
		this.zalbe = new HashSet<Zalba>();
		this.ocene = new HashSet<OcenaApoteke>();
		this.rezervacije = new HashSet<Rezervacija>();
		this.alergije = new HashSet<Preparat>();
		this.kupljeniPreparati = new HashSet<Preparat>();
		this.apoteke = new HashSet<Apoteka>();
		this.pregledi = new HashSet<Pregled>();
	}
	
	public Pacijent(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,ZaposlenjeKorisnika zaposlenjeKorisnika, Lokacija lokacija, StatusNaloga statusNaloga, int brojPoena,
			TipKorisnika tipKorisnika) {
		super(ime, prezime, username, password, email, loggedBefore, telefon, lokacija,zaposlenjeKorisnika);
		this.statusNaloga = statusNaloga;
		this.brojPoena = brojPoena;
		this.tipKorisnika = tipKorisnika;
		this.eRecepti = new HashSet<ERecept>();
		this.penali = new HashSet<Penal>();
		this.zalbe = new HashSet<Zalba>();
		this.ocene = new HashSet<OcenaApoteke>();
		this.rezervacije = new HashSet<Rezervacija>();
		this.alergije = new HashSet<Preparat>();
		this.kupljeniPreparati = new HashSet<Preparat>();
		this.apoteke = new HashSet<Apoteka>();
		this.pregledi = new HashSet<Pregled>();
	}
	
	public void addPregled(Pregled p) {
		pregledi.add(p);
		p.setPacijent(this);
	}
	
	public void removePregled(Pregled p) {
		pregledi.remove(p);
		p.setPacijent(null);
	}
	
	public void addRezervacija(Rezervacija r) {
		rezervacije.add(r);
		r.setPacijent(this);
	}
	
	public void removeRezervacija(Rezervacija r) {
		rezervacije.remove(r);
		r.setPacijent(null);
	}
	
	public void addZalba(Zalba z) {
		zalbe.add(z);
		z.setPacijent(this);
	}
	
	public void removeZalba(Zalba z) {
		zalbe.remove(z);
		z.setPacijent(null);
	}
	
	public void addPenal(Penal p) {
		penali.add(p);
		p.setPacijent(this);
	}
	
	public void removePenal(Penal p) {
		penali.remove(p);
		p.setPacijent(null);
	}
	
	public void addERecept(ERecept er) {
		eRecepti.add(er);
		er.setPacijent(this);
	}
	
	public void removeERecept(ERecept er) {
		eRecepti.remove(er);
		er.setPacijent(null);
	}
	
	public void addApoteka(Apoteka a) {
		apoteke.add(a);
		a.getPacijenti().add(this);
	}
	
	public void removeApoteka(Apoteka a) {
		apoteke.remove(a);
		a.getPacijenti().remove(this);
	}
	
	public StatusNaloga getStatusNaloga() {
		return statusNaloga;
	}

	public void setStatusNaloga(StatusNaloga statusNaloga) {
		this.statusNaloga = statusNaloga;
	}

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public Set<ERecept> geteRecepti() {
		return eRecepti;
	}

	public void seteRecepti(Set<ERecept> eRecepti) {
		this.eRecepti = eRecepti;
	}

	public Set<Penal> getPenali() {
		return penali;
	}

	public void setPenali(Set<Penal> penali) {
		this.penali = penali;
	}

	public Set<Zalba> getZalbe() {
		return zalbe;
	}

	public void setZalbe(Set<Zalba> zalbe) {
		this.zalbe = zalbe;
	}

	public Set<OcenaApoteke> getOcene() {
		return ocene;
	}

	public void setOcene(Set<OcenaApoteke> ocene) {
		this.ocene = ocene;
	}

	public Set<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public Set<Preparat> getAlergije() {
		return alergije;
	}

	public void setAlergije(Set<Preparat> alergije) {
		this.alergije = alergije;
	}

	public Set<Preparat> getKupljeniPreparati() {
		return kupljeniPreparati;
	}

	public void setKupljeniPreparati(Set<Preparat> kupljeniPreparati) {
		this.kupljeniPreparati = kupljeniPreparati;
	}

	public Set<Apoteka> getApoteke() {
		return apoteke;
	}

	public void setApoteke(Set<Apoteka> apoteke) {
		this.apoteke = apoteke;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}
	
	
	
	
}
