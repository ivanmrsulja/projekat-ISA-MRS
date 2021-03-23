package rest.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Cena {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DostupanProizvod> dostupniProizvodi;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@Column(name = "pocetakVazenja", nullable = false)
	private LocalDate pocetakVazenja;
	
	public Cena() {
		this.dostupniProizvodi = new HashSet<DostupanProizvod>();
	}

	public Cena(int id, Apoteka apoteka, LocalDate pocetakVazenja) {
		this();
		this.id=id;
		this.apoteka = apoteka;
		this.pocetakVazenja = pocetakVazenja;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
