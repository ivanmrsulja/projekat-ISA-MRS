package rest.domain;

import java.time.LocalDate;

public class Ponuda {
	private int id;
	private StatusPonude status;
	private double ukupnaCena;
	private LocalDate rokIsporuke;
	
	private Narudzbenica narudzbenica;
	private Dobavljac dobavljac;
	
	public Ponuda()	{};
	
	public Ponuda(int id,StatusPonude status, double ukupnaCena, LocalDate rokIsporuke, Narudzbenica narudzbenica,
			Dobavljac dobavljac) {
		super();
		this.id=id;
		this.status = status;
		this.ukupnaCena = ukupnaCena;
		this.rokIsporuke = rokIsporuke;
		this.narudzbenica = narudzbenica;
		this.dobavljac = dobavljac;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StatusPonude getStatus() {
		return status;
	}

	public void setStatus(StatusPonude status) {
		this.status = status;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public LocalDate getRokIsporuke() {
		return rokIsporuke;
	}

	public void setRokIsporuke(LocalDate rokIsporuke) {
		this.rokIsporuke = rokIsporuke;
	}

	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}

	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}
		
}
