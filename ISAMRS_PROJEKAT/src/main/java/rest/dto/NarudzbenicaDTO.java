package rest.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import rest.domain.NaruceniProizvod;
import rest.domain.Narudzbenica;
import rest.domain.StatusNarudzbenice;

public class NarudzbenicaDTO {

	private int id;
	private LocalDate rok;
	private StatusNarudzbenice status;
	private ArrayList<NaruceniProizvodDTO> naruceniProizvodi = new ArrayList<NaruceniProizvodDTO>();
	private int idAdmina;

	public NarudzbenicaDTO() {}

	public StatusNarudzbenice getStatus() {
		return status;
	}

	public void setStatus(StatusNarudzbenice status) {
		this.status = status;
	}

	public NarudzbenicaDTO(Narudzbenica n) {
		this.id = n.getId();
		this.rok = n.getRok();
		this.idAdmina = n.getAdminApoteke().getId();
		this.status = n.getStatus();
		for (NaruceniProizvod np : n.getNaruceniProizvodi()) {
			this.naruceniProizvodi.add(new NaruceniProizvodDTO(np));
		}
	}

	public NarudzbenicaDTO(int id, LocalDate rok, ArrayList<NaruceniProizvodDTO> naruceniProizvodi, int idAdmina) {
		super();
		this.id = id;
		this.rok = rok;
		this.naruceniProizvodi = naruceniProizvodi;
		this.idAdmina = idAdmina;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getRok() {
		return rok;
	}
	public void setRok(LocalDate rok) {
		this.rok = rok;
	}
	public ArrayList<NaruceniProizvodDTO> getNaruceniProizvodi() {
		return naruceniProizvodi;
	}
	public void setNaruceniProizvodi(ArrayList<NaruceniProizvodDTO> naruceniProizvodi) {
		this.naruceniProizvodi = naruceniProizvodi;
	}
	public int getIdAdmina() {
		return idAdmina;
	}
	public void setIdAdmina(int idAdmina) {
		this.idAdmina = idAdmina;
	}
	@Override
	public String toString() {
		return "NarudzbenicaDTO [id=" + id + ", rok=" + rok + ", naruceniProizvodi=" + naruceniProizvodi + ", idAdmina="
				+ idAdmina + "]";
	}

	

}
