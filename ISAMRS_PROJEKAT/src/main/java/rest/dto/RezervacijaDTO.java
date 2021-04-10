package rest.dto;

import java.time.LocalDate;

import rest.domain.Rezervacija;
import rest.domain.StatusRezervacije;

public class RezervacijaDTO {

	private Integer id;
	private StatusRezervacije status;
	private LocalDate datumPreuzimanja;
	private String preparat;
	private String apoteka;
	
	public RezervacijaDTO() {}
	public RezervacijaDTO(Rezervacija r) {
		id = r.getId();
		status = r.getStatus();
		datumPreuzimanja = r.getDatumPreuzimanja();
		preparat = r.getPreparat().getNaziv();
		apoteka = r.getApoteka().getNaziv() + " - " + r.getApoteka().getLokacija().getUlica();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getPreparat() {
		return preparat;
	}
	public void setPreparat(String preparat) {
		this.preparat = preparat;
	}
	public String getApoteka() {
		return apoteka;
	}
	public void setApoteka(String apoteka) {
		this.apoteka = apoteka;
	}
	
}
