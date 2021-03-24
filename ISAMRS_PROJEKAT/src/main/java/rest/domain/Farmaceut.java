package rest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Farmaceut extends Korisnik implements Ocenjivo{
	@Column(name = "brojOcena", nullable = true)
	private int brojOcena;
	@Column(name = "sumaOcena", nullable = true)
	private int sumaOcena;
	@Column(name = "ocena", nullable = true)
	private double ocena;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "zaposlenje_id", referencedColumnName = "id")
	private Zaposlenje zaposlenje;
	
	public Farmaceut() {
		super();
	}
	
	public Farmaceut(String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon, Lokacija lokacija,
			ZaposlenjeKorisnika zaposlenjeKorisnika,int brojOcena,int sumaOcena,Zaposlenje zaposlenje) {
		super(ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.zaposlenje = zaposlenje;
		this.ocena=izracunajOcenu();
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

	public Zaposlenje getZaposlenje() {
		return zaposlenje;
	}

	public void setZaposlenje(Zaposlenje zaposlenje) {
		this.zaposlenje = zaposlenje;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

}
