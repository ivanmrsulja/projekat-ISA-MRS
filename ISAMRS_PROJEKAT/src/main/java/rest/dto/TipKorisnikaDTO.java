package rest.dto;



public class TipKorisnikaDTO {

	private String naziv;
	private int bodovi;
	private double popust;
	
	public TipKorisnikaDTO() {}
	
	public TipKorisnikaDTO(String naziv, int bodovi, double popust) {
		super();
		this.naziv = naziv;
		this.bodovi = bodovi;
		this.popust = popust;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getBodovi() {
		return bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}
	
}