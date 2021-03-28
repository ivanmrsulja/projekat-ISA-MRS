package rest.dto;

import java.time.LocalDate;

import rest.domain.Penal;

public class PenalDTO {
	private int id;
	private LocalDate datum;
	
	public PenalDTO() {}
	public PenalDTO(Penal p) {
		this.datum = p.getDatum();
		this.id = p.getId();
	}
	
	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
