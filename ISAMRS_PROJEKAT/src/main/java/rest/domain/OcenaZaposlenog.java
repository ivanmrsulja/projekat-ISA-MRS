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
public class OcenaZaposlenog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "ocena", nullable = false)
	private int ocena;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "zaposleni_id", referencedColumnName = "id")
	private Korisnik zaposleni;
	
	public OcenaZaposlenog() {}
	public OcenaZaposlenog(int ocena, Pacijent p, Korisnik k) {
		this.ocena = ocena;
		this.pacijent = p;
		this.zaposleni = k;
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
	public Korisnik getZaposleni() {
		return zaposleni;
	}
	public void setZaposleni(Korisnik zaposleni) {
		this.zaposleni = zaposleni;
	}
	
}
