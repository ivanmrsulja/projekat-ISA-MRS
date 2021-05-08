package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "ime", nullable = false)
	private String ime;
	@Column(name = "prezime", nullable = false)
	private String prezime;
	@Column(name = "username", nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "loggedBefore", nullable = false)
	private Boolean loggedBefore;
	@Column(name = "telefon", nullable = false)
	private String telefon;
	@Column(name = "zaposlenjeKorisnika", nullable = true)
	private ZaposlenjeKorisnika zaposlenjeKorisnika;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Lokacija lokacija;
	
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Zahtjev> zahtjevi;
	@OneToMany(mappedBy = "korisnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notifikacija> notifikacije;
	@OneToMany(mappedBy = "zaposleni", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;
		
	
	public Korisnik() {	
		this.zahtjevi = new HashSet<Zahtjev>();		
		this.notifikacije = new HashSet<Notifikacija>();
		this.pregledi = new HashSet<Pregled>();
		this.loggedBefore = false;
	}

	public Korisnik(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacije,ZaposlenjeKorisnika zaposlenjeKorisnika) {
		this();
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
		this.email = email;
		this.loggedBefore = loggedBefore;
		this.telefon = telefon;
		this.lokacija = lokacije;
		this.zaposlenjeKorisnika=zaposlenjeKorisnika;
	}	
	
	public void addNotifikacija(Notifikacija n) {
		notifikacije.add(n);
		n.setKorisnik(this);
	}
	
	public void removeNotifikacija(Notifikacija n) {
		notifikacije.remove(n);
		n.setKorisnik(null);
	}
	
	public void addZahtjev(Zahtjev z) {
		zahtjevi.add(z);
		z.setKorisnik(this);
	}
	
	public void removeZahtjev(Zahtjev z) {
		zahtjevi.remove(z);
		z.setKorisnik(null);
	}
	
	public void addPregled(Pregled p) {
		pregledi.add(p);
		p.setZaposleni(this);
	}
	
	public void removePregled(Pregled p) {
		pregledi.remove(p);
		p.setZaposleni(null);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public Boolean getLoggedBefore() {
		return loggedBefore;
	}

	public void setLoggedBefore(Boolean loggedBefore) {
		this.loggedBefore = loggedBefore;
	}

	public Set<Zahtjev> getZahtjevi() {
		return zahtjevi;
	}

	public void setZahtjevi(Set<Zahtjev> zahtjevi) {
		this.zahtjevi = zahtjevi;
	}

	public Set<Notifikacija> getNotifikacije() {
		return notifikacije;
	}

	public void setNotifikacije(Set<Notifikacija> notifikacije) {
		this.notifikacije = notifikacije;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public ZaposlenjeKorisnika getZaposlenjeKorisnika() {
		return zaposlenjeKorisnika;
	}

	public void setZaposlenjeKorisnika(ZaposlenjeKorisnika zaposlenjeKorisnika) {
		this.zaposlenjeKorisnika = zaposlenjeKorisnika;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}
		
}
