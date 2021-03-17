package rest.domain;

import java.util.Date;
import java.util.Set;

public class ERecept {

	private Long sifra;
	private Date datumIzdavanja;
	
	private Set<StavkaRecepta> stavkaRecepata;
	private Pacijent pacijent;
	
	public ERecept() {}

	public ERecept(Long sifra, Date datumIzdavanja, Set<StavkaRecepta> stavkaRecepata, Pacijent pacijent) {
		super();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.stavkaRecepata = stavkaRecepata;
		this.pacijent = pacijent;
	}



	public Long getSifra() {
		return sifra;
	}

	public void setSifra(Long sifra) {
		this.sifra = sifra;
	}

	public Date getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(Date datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}



	public Set<StavkaRecepta> getStavkaRecepata() {
		return stavkaRecepata;
	}



	public void setStavkaRecepata(Set<StavkaRecepta> stavkaRecepata) {
		this.stavkaRecepata = stavkaRecepata;
	}



	public Pacijent getPacijent() {
		return pacijent;
	}



	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
	
}
