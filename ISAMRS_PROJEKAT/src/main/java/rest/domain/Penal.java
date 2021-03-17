package rest.domain;

import java.util.Date;

public class Penal {

	private Long id;
	private Date datum;
	
	private Pacijent pacijent;
	
	public Penal() {}

	public Penal(Long id,Date datum, Pacijent pacijent) {
		super();
		this.id=id;
		this.datum = datum;
		this.pacijent = pacijent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	
	
}
