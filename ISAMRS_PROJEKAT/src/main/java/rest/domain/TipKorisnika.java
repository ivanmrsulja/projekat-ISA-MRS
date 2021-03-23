package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipKorisnika {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "naziv", nullable = false)
	private String naziv;
	@Column(name = "bodovi", nullable = false)
	private int bodovi;
	@Column(name = "popust", nullable = false)
	private double popust;
	
	public TipKorisnika() {}
	
	public TipKorisnika(String naziv, int bodovi, double popust) {
		super();
		this.naziv = naziv;
		this.bodovi = bodovi;
		this.popust = popust;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
