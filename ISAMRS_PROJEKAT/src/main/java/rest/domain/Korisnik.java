package rest.domain;

import java.util.HashSet;
import java.util.Set;

public class Korisnik {
	private Long Id;
	private String ime;
	private String prezime;
	private String username;
	private String password;
	private String email;
	private Boolean loggedBefore;
	private String telefon;
	private ZaposlenjeKorisnika zaposlenjeKorisnika;
	
	private Set<Zahtjev> zahtjevi;
	private Lokacija lokacija;
	private Set<Notifikacija> notifikacije;
	private Set<Pregled> pregledi;
		
	
	public Korisnik() {	}

	public Korisnik(Long id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacije,ZaposlenjeKorisnika zaposlenjeKorisnika) {
		super();
		Id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
		this.email = email;
		this.loggedBefore = loggedBefore;
		this.telefon = telefon;
		this.lokacija = lokacije;
		this.zaposlenjeKorisnika=zaposlenjeKorisnika;
		this.zahtjevi = new HashSet<Zahtjev>();		
		this.notifikacije = new HashSet<Notifikacija>();
		this.pregledi = new HashSet<Pregled>();
	}	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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
