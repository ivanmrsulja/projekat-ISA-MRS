package rest.domain;

import java.util.Date;
import java.util.Set;

public class Cena {
	private Long id;
	private Set<DostupanProizvod> dostupniProizvodi;
	private Apoteka apoteka;

	private Date pocetakVazenja;
	
	public Cena() {}

	public Cena(Long id,Set<DostupanProizvod> dostupniProizvodi, Apoteka apoteka, Date pocetakVazenja) {
		super();
		this.id=id;
		this.dostupniProizvodi = dostupniProizvodi;
		this.apoteka = apoteka;
		this.pocetakVazenja = pocetakVazenja;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<DostupanProizvod> getDostupniProizvodi() {
		return dostupniProizvodi;
	}

	public void setDostupniProizvodi(Set<DostupanProizvod> dostupniProizvodi) {
		this.dostupniProizvodi = dostupniProizvodi;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Date getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(Date pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}
	
}
