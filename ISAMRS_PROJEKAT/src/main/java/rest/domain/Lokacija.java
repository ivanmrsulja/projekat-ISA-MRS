package rest.domain;

public class Lokacija {
	private int id;
	private double sirina;
	private double duzina;
	private String adresa;
	
	public Lokacija() {}
	
	public Lokacija(int id,double sirina, double duzina, String adresa) {
		super();
		this.id=id;
		this.sirina = sirina;
		this.duzina = duzina;
		this.adresa = adresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSirina() {
		return sirina;
	}

	public void setSirina(double sirina) {
		this.sirina = sirina;
	}

	public double getDuzina() {
		return duzina;
	}

	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}

	public String getUlica() {
		return adresa;
	}

	public void setUlica(String adresa) {
		this.adresa = adresa;
	}
	
	
}
