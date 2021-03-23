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
	private int id;
	@Column(name = "tip", nullable = false)
	private TipZahtjeva tip;
	@Column(name = "status", nullable = false)
	private StatusZahtjeva status;
	
	public Zahtjev() {}
	
	public Zahtjev(int id,TipZahtjeva tip, StatusZahtjeva status) {
		super();
		this.id=id;
		this.tip = tip;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
