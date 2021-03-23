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
public class StavkaRecepta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "datumIzdavanja", nullable = false)
	private int kolicina;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "preparat_id", referencedColumnName = "id")
	private Preparat preparat;
	
	public StavkaRecepta() {}

	public StavkaRecepta(int id,int kolicina, Preparat preparat) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.preparat = preparat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}
	
}
