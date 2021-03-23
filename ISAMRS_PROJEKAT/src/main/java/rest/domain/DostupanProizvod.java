package rest.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class DostupanProizvod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "kolicina", nullable = false)
	private int kolicina;
	@Column(name = "cena", nullable = false)
	private double cena;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "preparat_id", referencedColumnName = "id")
	private Preparat preparat;
	
	public DostupanProizvod() {}
	
	public DostupanProizvod(int id,int kolicina, double cena,Preparat preparat) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.cena = cena;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	
}
