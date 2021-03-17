package rest.domain;

import java.util.Date;

public class Rezervacija {
	private Long id;
	private StatusRezervacije status;
	private Date datumPreuzimanja;
	
	private Pacijent pacijent;
	private Preparat preparat;
	
	public Rezervacija() {}
	
	public Rezervacija(Long id,StatusRezervacije status, Date datumPreuzimanja, Pacijent pacijent, Preparat preparat) {
		super();
		this.id=id;
		this.status = status;
		this.datumPreuzimanja = datumPreuzimanja;
		this.pacijent = pacijent;
		this.preparat = preparat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public Date getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(Date datumPreuzimanja) {
		this.datumPreuzimanja = datumPreuzimanja;
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
