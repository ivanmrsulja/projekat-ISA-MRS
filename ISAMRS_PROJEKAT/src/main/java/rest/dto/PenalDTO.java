package rest.dto;

import java.time.LocalDate;

import rest.domain.Penal;

public class PenalDTO {
	private LocalDate datum;
	
	public PenalDTO() {}
	public PenalDTO(Penal p) {
		this.datum = p.getDatum();
	}
	
	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	
}
