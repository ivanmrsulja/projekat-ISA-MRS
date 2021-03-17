package rest.domain;

import java.util.Set;

public class Dobavljac extends Korisnik {
	
	private Set<Ponuda> ponude;
	
	public Dobavljac() {}
	
	public Dobavljac(Long id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,Set<Ponuda> ponude) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.ponude = ponude;
	}

	public Set<Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}
	
	
	
}
