package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Dobavljac extends Korisnik {
	
	@OneToMany(mappedBy = "dobavljac", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ponuda> ponude;
	
	public Dobavljac() {
		super();
		this.ponude = new HashSet<Ponuda>();
	}
	
	public Dobavljac(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.ponude = new HashSet<Ponuda>();
	}
	
	public void addPonuda(Ponuda p) {
		ponude.add(p);
		p.setDobavljac(this);
	}
	
	public void removePonuda(Ponuda p) {
		ponude.remove(p);
		p.setDobavljac(null);
	}
	
	public Set<Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}
	
	
	
}
