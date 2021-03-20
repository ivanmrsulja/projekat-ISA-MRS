package rest.domain;

import java.util.HashSet;
import java.util.Set;

public class Dobavljac extends Korisnik {
	
	private Set<Ponuda> ponude;
	
	public Dobavljac() {
		this.ponude = new HashSet<Ponuda>();
	}
	
	public Dobavljac(int id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.ponude = new HashSet<Ponuda>();
	}

	public Set<Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}
	
	
	
}
