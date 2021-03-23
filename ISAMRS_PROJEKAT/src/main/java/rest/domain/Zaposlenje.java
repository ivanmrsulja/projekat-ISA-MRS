package rest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Zaposlenje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "pocetakRadnogVremena", nullable = false)
	private int pocetakRadnogVremena;
	@Column(name = "krajRadnogVremena", nullable = false)
	private int krajRadnogVremena;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "korisnik_id", referencedColumnName = "id")
	private Korisnik korisnik;
	
	public Zaposlenje() {}
	
	public Zaposlenje(int id,int pocetakRadnogVremena, int krajRadnogVremena, Apoteka apoteka,Korisnik korisnik) {
		super();
		this.id=id;
		this.pocetakRadnogVremena = pocetakRadnogVremena;
		this.krajRadnogVremena = krajRadnogVremena;
		this.apoteka = apoteka;
		this.korisnik=korisnik;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public int getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(int pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public int getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(int krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
}
