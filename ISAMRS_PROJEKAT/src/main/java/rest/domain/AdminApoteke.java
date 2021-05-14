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
	private Set<Narudzbenica> narudzbenice;

	public AdminApoteke() {
		super();
		this.akcijeIPromocije = new HashSet<AkcijaPromocija>();
		this.narudzbenice = new HashSet<Narudzbenica>();
		this.apoteka = new Apoteka();
	}
	
	public AdminApoteke(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,Apoteka apoteka) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.apoteka = apoteka;
		this.akcijeIPromocije = new HashSet<AkcijaPromocija>();
		this.narudzbenice = new HashSet<Narudzbenica>();
	}
	
	public void addAkcijaPromocija(AkcijaPromocija ap) {
		akcijeIPromocije.add(ap);
		ap.setAdminApoteke(this);
	}
	
	public void removeAkcijaPromocija(AkcijaPromocija ap) {
		akcijeIPromocije.remove(ap);
		ap.setAdminApoteke(null);
	}
	
	public void addNarudzbenica(Narudzbenica na) {
		narudzbenice.add(na);
		na.setAdminApoteke(this);
	}
	
	public void removeNarudzbenica(Narudzbenica na) {
		narudzbenice.remove(na);
		na.setAdminApoteke(null);
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

	public Set<Narudzbenica> getNarudzbenice() {
		return narudzbenice;
	}

	public void setNarudzbenice(Set<Narudzbenica> nerudzbenice) {
		this.narudzbenice = nerudzbenice;
	}
}
