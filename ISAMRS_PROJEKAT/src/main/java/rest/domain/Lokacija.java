package rest.domain;

public class Lokacija {
	private int id;
	private double sirina;
	private double duzina;
	private String ulica;
	private String grad;
	private String drzava;
	
	public Lokacija() {}
	
	public Lokacija(int id,double sirina, double duzina, String ulica, String grad, String drzava) {
		super();
		this.id=id;
		this.sirina = sirina;
		this.duzina = duzina;
		this.ulica = ulica;
		this.grad = grad;
		this.drzava = drzava;
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
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	
	
}
