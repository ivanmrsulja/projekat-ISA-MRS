package rest.dto;

import java.time.LocalDate;

import rest.domain.Notifikacija;

public class NotifikacijaDTO {

	private String preparat;
	private LocalDate datum;
	private String status;
	private KorisnikDTO korisnik;

	public NotifikacijaDTO() {}

	public NotifikacijaDTO(Notifikacija n) {
		this.preparat = n.getNazivPreparata();
		this.datum = n.getDatum();
		this.status = (n.getPregledana()) ? "Pregledano" : "Novo";
		this.korisnik = new KorisnikDTO(n.getKorisnik());
	}

	public NotifikacijaDTO(String preparat, LocalDate datum, String status, KorisnikDTO korisnik) {
		super();
		this.preparat = preparat;
		this.datum = datum;
		this.status = status;
		this.korisnik = korisnik;
	}

	public String getPreparat() {
		return preparat;
	}
	public void setPreparat(String preparat) {
		this.preparat = preparat;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public KorisnikDTO getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(KorisnikDTO korisnik) {
		this.korisnik = korisnik;
	}

}
