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
	private Integer id;
	@Column(name = "kolicina", nullable = false)
	private int kolicina;
	@Column(name = "cena", nullable = false)
	private double cena;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "preparat_id", referencedColumnName = "id")
	private Preparat preparat;
	
	public DostupanProizvod() {}
	
	public DostupanProizvod(int kolicina, double cena,Preparat preparat) {
		super();
		this.kolicina = kolicina;
		this.cena = cena;
		this.preparat = preparat;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
