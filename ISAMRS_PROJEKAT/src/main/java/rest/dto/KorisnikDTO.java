package rest.dto;

import rest.domain.Korisnik;
import rest.domain.Lokacija;
import rest.domain.ZaposlenjeKorisnika;

public class KorisnikDTO {

	private int id;
	private String ime;
	private String prezime;
	private String username;
	private String email;
	private String telefon;
	private Lokacija lokacija;
	private ZaposlenjeKorisnika zaposlenjeKorisnika;
	private String stariPassw;
	private String noviPassw;
	
	public KorisnikDTO() {
		super();
	}
	
	public KorisnikDTO(Korisnik korisnik) {
		super();
		this.id=korisnik.getId();
		this.ime=korisnik.getIme();
		this.prezime=korisnik.getPrezime();
		this.username=korisnik.getUsername();
		this.email=korisnik.getEmail();
		this.telefon=korisnik.getTelefon();
		this.lokacija=korisnik.getLokacija();
		this.zaposlenjeKorisnika=korisnik.getZaposlenjeKorisnika();
		this.stariPassw = null;
		this.noviPassw = null;
	}	

	@Override
	public String toString() {
		return "KorisnikDTO [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", username=" + username + ", email="
				+ email + ", telefon=" + telefon + ", lokacija=" + lokacija + ", zaposlenjeKorisnika="
				+ zaposlenjeKorisnika + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public ZaposlenjeKorisnika getZaposlenjeKorisnika() {
		return zaposlenjeKorisnika;
	}

	public void setZaposlenjeKorisnika(ZaposlenjeKorisnika zaposlenjeKorisnika) {
		this.zaposlenjeKorisnika = zaposlenjeKorisnika;
	}
	
	
}
