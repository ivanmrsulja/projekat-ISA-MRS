package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Zahtjev {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tip", nullable = false)
	private TipZahtjeva tip;
	@Column(name = "status", nullable = false)
	private StatusZahtjeva status;
	
	public Zahtjev() {}
	
	public Zahtjev(TipZahtjeva tip, StatusZahtjeva status) {
		super();
		this.tip = tip;
		this.status = status;
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
	
	
}
