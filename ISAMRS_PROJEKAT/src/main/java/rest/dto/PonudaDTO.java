package rest.dto;

import java.time.LocalDate;

import rest.domain.Ponuda;
import rest.domain.StatusPonude;

public class PonudaDTO {
	private int id;
	private StatusPonude status;
	private double ukupnaCena;
	private LocalDate rokIsporuke;
	private LocalDate rokIsporukeNarudzbenice;
	private int idNarudzbenice;
	private String dobavljac;
	
	public PonudaDTO() {}
	
	public PonudaDTO(Ponuda p) {
		id = p.getId();
		status = p.getStatus();
		ukupnaCena = p.getUkupnaCena();
		rokIsporuke = p.getRokIsporuke();
		idNarudzbenice = p.getNarudzbenica().getId();
		dobavljac = p.getDobavljac().getUsername();
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

	public int getIdNarudzbenice() {
		return idNarudzbenice;
	}

	public void setIdNarudzbenice(int idNarudzbenice) {
		this.idNarudzbenice = idNarudzbenice;
	}

	public String getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(String dobavljac) {
		this.dobavljac = dobavljac;
	}

	public LocalDate getRokIsporukeNarudzbenice() {
		return rokIsporukeNarudzbenice;
	}

	public void setRokIsporukeNarudzbenice(LocalDate rokIsporukeNarudzbenice) {
		this.rokIsporukeNarudzbenice = rokIsporukeNarudzbenice;
	}
	
	
}
