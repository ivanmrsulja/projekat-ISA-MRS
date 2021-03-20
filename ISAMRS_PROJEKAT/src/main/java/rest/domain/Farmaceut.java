package rest.domain;

public class Farmaceut extends Korisnik implements Ocenjivo{
	private int brojOcena;
	private int sumaOcena;
	private double ocena;
	
	private Zaposlenje zaposlenje;
	
	public Farmaceut() {}
	
	public Farmaceut(int id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,ZaposlenjeKorisnika zaposlenjeKorisnika, Lokacija lokacija,
			Zaposlenje zaposlenje,int brojOcena,int sumaOcena) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon,lokacija,zaposlenjeKorisnika);
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.zaposlenje = zaposlenje;
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
