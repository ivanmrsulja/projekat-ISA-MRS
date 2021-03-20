package rest.domain;

import java.time.LocalDate;
import java.util.Date;

public class Penal {

	private int id;
	private LocalDate datum;
	
	private Pacijent pacijent;
	
	public Penal() {}

	public Penal(int id,LocalDate datum, Pacijent pacijent) {
		super();
		this.id=id;
		this.datum = datum;
		this.pacijent = pacijent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	
	
}
