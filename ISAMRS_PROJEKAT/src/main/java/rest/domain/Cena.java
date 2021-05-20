package rest.domain;

import java.time.LocalDate;
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
	private Integer id;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<DostupanProizvod> dostupniProizvodi;
	@ManyToOne(fetch = FetchType.EAGER)
	private Apoteka apoteka;
	@Column(name = "pocetakVazenja", nullable = false)
	private LocalDate pocetakVazenja;
	@Column(name = "krajVazenja")
	private LocalDate krajVazenja;
	
	public Cena() {
		this.dostupniProizvodi = new HashSet<DostupanProizvod>();
		this.krajVazenja = null;
	}

	public Cena(Apoteka apoteka, LocalDate pocetakVazenja) {
		this();
		this.apoteka = apoteka;
		this.pocetakVazenja = pocetakVazenja;
		this.krajVazenja = null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public LocalDate getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(LocalDate krajVazenja) {
		this.krajVazenja = krajVazenja;
	}
	
}
