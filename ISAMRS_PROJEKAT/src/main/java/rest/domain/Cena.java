package rest.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Cena {
	private Long id;
	private Set<DostupanProizvod> dostupniProizvodi;
	private Apoteka apoteka;

	private LocalDate pocetakVazenja;
	
	public Cena() {
		this.dostupniProizvodi = new HashSet<DostupanProizvod>();
	}

	public Cena(Long id, Apoteka apoteka, LocalDate pocetakVazenja) {
		this();
		this.id=id;
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

	public LocalDate getPocetakVazenja() {
		return pocetakVazenja;
	}

	public void setPocetakVazenja(LocalDate pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}
	
}
