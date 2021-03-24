package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class AdminSistema extends Korisnik {
	
	@OneToMany(mappedBy = "adminSistema", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Zalba> zalbe;
	
	public AdminSistema() {
		super();
		this.zalbe = new HashSet<Zalba>();
	}
	
	public AdminSistema(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.zalbe = new HashSet<Zalba>();
	}

	public Set<Zalba> getZalbe() {
		return zalbe;
	}

	public void setZalbe(Set<Zalba> zalbe) {
		this.zalbe = zalbe;
	}
	
	public void addZalba(Zalba z) {
		zalbe.add(z);
		z.setAdminSistema(this);
	}
	
	public void removeZalba(Zalba z) {
		zalbe.remove(z);
		z.setAdminSistema(null);
	}
}
