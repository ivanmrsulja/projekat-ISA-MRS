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
public class NaruceniProizvod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "kolicina", nullable = false)
	private int kolicina;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "preparat_id", referencedColumnName = "id")
	private Preparat preparat;
	@ManyToOne(fetch = FetchType.EAGER)
	private Narudzbenica narudzbenica;
	
	public NaruceniProizvod() {}

	public NaruceniProizvod(int id,int kolicina, Preparat preparat, Narudzbenica narudzbenica) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.preparat = preparat;
		this.narudzbenica = narudzbenica;
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

	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}

	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	
}
