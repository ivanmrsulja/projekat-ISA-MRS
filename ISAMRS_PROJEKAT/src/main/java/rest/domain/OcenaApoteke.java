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
public class OcenaApoteke {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "ocena", nullable = false)
	private int ocena;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "apoteka_id", referencedColumnName = "id")
	private Apoteka apoteka;
	
	public OcenaApoteke() {}

	public OcenaApoteke(int ocena, Pacijent pacijent,Apoteka ocenjivo) {
		super();
		this.ocena = ocena;
		this.pacijent = pacijent;
		this.apoteka=ocenjivo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka ocenjivo) {
		this.apoteka = ocenjivo;
	}
	
}
