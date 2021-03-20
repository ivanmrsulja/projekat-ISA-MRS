package rest.domain;

public class Zahtjev {
	private int id;
	private TipZahtjeva tip;
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
