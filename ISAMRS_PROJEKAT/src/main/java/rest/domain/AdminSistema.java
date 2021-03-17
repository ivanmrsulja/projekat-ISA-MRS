package rest.domain;

import java.util.Set;

public class AdminSistema extends Korisnik {
	
	private Set<Zalba> zalbe;
	
	public AdminSistema() {}
	
	public AdminSistema(Long id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,Set<Zalba> zalbe) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.zalbe = zalbe;
	}

	public Set<Zalba> getZalbe() {
		return zalbe;
	}

	public void setZalbe(Set<Zalba> zalbe) {
		this.zalbe = zalbe;
	}
	
	
}
