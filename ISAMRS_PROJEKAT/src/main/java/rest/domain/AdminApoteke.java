package rest.domain;

import java.util.HashSet;
import java.util.Set;

public class AdminApoteke extends Korisnik {
	
	private Apoteka apoteka;
	private Set<AkcijaPromocija> akcijeIPromocije;
	private Set<Narudzbenica> nerudzbenice;

	public AdminApoteke() {
		this.akcijeIPromocije = new HashSet<AkcijaPromocija>();
		this.nerudzbenice = new HashSet<Narudzbenica>();
	}
	
	public AdminApoteke(int id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,Apoteka apoteka) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.apoteka = apoteka;
		this.akcijeIPromocije = new HashSet<AkcijaPromocija>();
		this.nerudzbenice = new HashSet<Narudzbenica>();
	}
	
	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Set<AkcijaPromocija> getAkcijeIPromocije() {
		return akcijeIPromocije;
	}

	public void setAkcijeIPromocije(Set<AkcijaPromocija> akcijeIPromocije) {
		this.akcijeIPromocije = akcijeIPromocije;
	}

	public Set<Narudzbenica> getNerudzbenice() {
		return nerudzbenice;
	}

	public void setNerudzbenice(Set<Narudzbenica> nerudzbenice) {
		this.nerudzbenice = nerudzbenice;
	}
}
