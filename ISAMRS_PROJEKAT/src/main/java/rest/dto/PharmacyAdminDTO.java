package rest.dto;

import rest.domain.AdminApoteke;
import rest.domain.Lokacija;
import rest.domain.ZaposlenjeKorisnika;

public class PharmacyAdminDTO {

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
	private boolean loggedBefore;
	private String apoteka;
	
	public PharmacyAdminDTO() {
		super();
	}
	
	public PharmacyAdminDTO(AdminApoteke korisnik) {
		super();
		this.id=korisnik.getId();
		this.ime=korisnik.getIme();
		this.prezime=korisnik.getPrezime();
		this.username=korisnik.getUsername();
		this.email=korisnik.getEmail();
		this.telefon=korisnik.getTelefon();
		this.lokacija=korisnik.getLokacija();
		this.zaposlenjeKorisnika=korisnik.getZaposlenjeKorisnika();
		this.loggedBefore = korisnik.getLoggedBefore();
		this.stariPassw = null;
		this.noviPassw = korisnik.getPassword();
		this.apoteka = korisnik.getApoteka().getNaziv();
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

	public boolean isLoggedBefore() {
		return loggedBefore;
	}

	public void setLoggedBefore(boolean loggedBefore) {
		this.loggedBefore = loggedBefore;
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

	public String getStariPassw() {
		return stariPassw;
	}

	public void setStariPassw(String stariPassw) {
		this.stariPassw = stariPassw;
	}

	public String getNoviPassw() {
		return noviPassw;
	}

	public void setNoviPassw(String noviPassw) {
		this.noviPassw = noviPassw;
	}

	public String getApoteka() {
		return apoteka;
	}

	public void setApoteka(String apoteka) {
		this.apoteka = apoteka;
	}
	
	
	
	
}
