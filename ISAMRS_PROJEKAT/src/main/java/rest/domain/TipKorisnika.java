package rest.domain;

public class TipKorisnika {

	private Long id;
	private String naziv;
	private int bodovi;
	private double popust;
	
	public TipKorisnika() {}
	
	public TipKorisnika(Long id,String naziv, int bodovi, double popust) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.bodovi = bodovi;
		this.popust = popust;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
