package rest.domain;

import java.util.HashSet;
import java.util.Set;

public class Dermatolog extends Korisnik implements Ocenjivo{
	private int brojOcena;
	private int sumaOcena;
	private double ocena;
	
	private Set<Zaposlenje> zaposlenja;
	
	public Dermatolog() {
		this.zaposlenja = new HashSet<Zaposlenje>();
	}
	
	public Dermatolog(int id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,ZaposlenjeKorisnika zaposlenjeKorisnika,int brojOcena,int sumaOcena) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.zaposlenja = new HashSet<Zaposlenje>();
		this.ocena=izracunajOcenu();
	}

	public int izracunajOcenu()
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
