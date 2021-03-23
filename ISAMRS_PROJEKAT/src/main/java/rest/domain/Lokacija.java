package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lokacija {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "sirina", nullable = false)
	private double sirina;
	@Column(name = "duzina", nullable = false)
	private double duzina;
	@Column(name = "ulica", nullable = false)
	private String ulica;
	
	public Lokacija() {}
	
	public Lokacija(double sirina, double duzina, String adresa) {
		super();
		this.sirina = sirina;
		this.duzina = duzina;
		this.ulica = adresa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public void setUlica(String adresa) {
		this.ulica = adresa;
	}
	
	
}
