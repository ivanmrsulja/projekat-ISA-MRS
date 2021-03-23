package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class AdminApoteke extends Korisnik {
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@OneToMany(mappedBy = "adminApoteke", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AkcijaPromocija> akcijeIPromocije;
	@OneToMany(mappedBy = "adminApoteke", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Narudzbenica> nerudzbenice;

	public AdminApoteke() {
		super();
		this.akcijeIPromocije = new HashSet<AkcijaPromocija>();
		this.nerudzbenice = new HashSet<Narudzbenica>();
	}
	
	public AdminApoteke(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,Apoteka apoteka) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
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
