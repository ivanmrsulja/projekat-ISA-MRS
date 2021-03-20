package rest.domain;

import java.time.LocalDate;

public class Rezervacija {
	private int id;
	private StatusRezervacije status;
	private LocalDate datumPreuzimanja;
	
	private Pacijent pacijent;
	private Preparat preparat;
	
	public Rezervacija() {}
	
	public Rezervacija(int id,StatusRezervacije status, LocalDate datumPreuzimanja, Pacijent pacijent, Preparat preparat) {
		super();
		this.id=id;
		this.status = status;
		this.datumPreuzimanja = datumPreuzimanja;
		this.pacijent = pacijent;
		this.preparat = preparat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public LocalDate getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(LocalDate datumPreuzimanja) {
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
