package rest.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ERecept {

	private int sifra;
	private LocalDate datumIzdavanja;
	
	private Set<StavkaRecepta> stavkaRecepata;
	private Pacijent pacijent;
	
	public ERecept() {
		this.stavkaRecepata = new HashSet<StavkaRecepta>();
	}

	public ERecept(int sifra, LocalDate datumIzdavanja, Pacijent pacijent) {
		this();
		this.sifra = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.pacijent = pacijent;
	}



	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public LocalDate getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(LocalDate datumIzdavanja) {
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
