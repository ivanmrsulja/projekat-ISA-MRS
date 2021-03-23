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
public class OcenaPreparata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ocena", nullable = false)
	private int ocena;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "apoteka_id", referencedColumnName = "id")
	private Preparat preparat;
	
	public OcenaPreparata() {}
	
	public OcenaPreparata(int id, int ocena, Pacijent pacijent, Preparat preparat) {
		this.id = id;
		this.ocena = ocena;
		this.pacijent = pacijent;
		this.preparat = preparat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}
	
	
}
