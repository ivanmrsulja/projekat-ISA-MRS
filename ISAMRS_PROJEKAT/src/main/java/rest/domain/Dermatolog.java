package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Dermatolog extends Korisnik implements Ocenjivo{
	@Column(name = "brojOcena", nullable = true)
	private int brojOcena;
	@Column(name = "sumaOcena", nullable = true)
	private int sumaOcena;
	@Column(name = "ocena", nullable = true)
	private double ocena;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Zaposlenje> zaposlenja;
	
	public Dermatolog() {
		super();
		this.zaposlenja = new HashSet<Zaposlenje>();
	}
	
	public Dermatolog(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,int brojOcena,int sumaOcena) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.zaposlenja = new HashSet<Zaposlenje>();
		this.ocena=izracunajOcenu();
	}
	
	public void addZaposlenje(Zaposlenje z) {
		zaposlenja.add(z);
		z.setKorisnik(this);
	}
	
	public void removeZaposlenje(Zaposlenje z) {
		zaposlenja.remove(z);
		z.setKorisnik(null);
	}
	
	public double izracunajOcenu()
	{
		if(this.brojOcena!=0) {
			return this.sumaOcena/this.brojOcena;
		}
		return 0;
	}

	public int getBrojOcena() {
		return brojOcena;
	}

	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}

	public int getSumaOcena() {
		return sumaOcena;
	}

	public void setSumaOcena(int sumaOcena) {
		this.sumaOcena = sumaOcena;
	}

	public Set<Zaposlenje> getZaposlenja() {
		return zaposlenja;
	}

	public void setZaposlenja(Set<Zaposlenje> zaposlenja) {
		this.zaposlenja = zaposlenja;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	
}
