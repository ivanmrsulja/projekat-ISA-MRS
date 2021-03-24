package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Zahtjev {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tip", nullable = false)
	private TipZahtjeva tip;
	@Column(name = "status", nullable = false)
	private StatusZahtjeva status;
	@ManyToOne(fetch = FetchType.EAGER)
	private Korisnik korisnik;
	
	public Zahtjev() {}
	
	public Zahtjev(TipZahtjeva tip, StatusZahtjeva status, Korisnik k) {
		super();
		this.tip = tip;
		this.status = status;
		this.korisnik = k;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipZahtjeva getTip() {
		return tip;
	}

	public void setTip(TipZahtjeva tip) {
		this.tip = tip;
	}

	public StatusZahtjeva getStatus() {
		return status;
	}

	public void setStatus(StatusZahtjeva status) {
		this.status = status;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
}
