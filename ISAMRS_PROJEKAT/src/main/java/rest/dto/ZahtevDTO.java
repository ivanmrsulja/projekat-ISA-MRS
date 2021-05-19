package rest.dto;

import java.time.LocalDate;

import rest.domain.Korisnik;
import rest.domain.StatusZahtjeva;
import rest.domain.TipZahtjeva;
import rest.domain.Zahtjev;

public class ZahtevDTO {

	private int id;
	private StatusZahtjeva status;
	private TipZahtjeva tip;
	private LocalDate pocetak;
	private LocalDate kraj;
	private KorisnikDTO korisnik;

	public ZahtevDTO(int id, StatusZahtjeva status, TipZahtjeva tip, Korisnik korisnik) {
		super();
		this.id = id;
		this.status = status;
		this.tip = tip;
		this.korisnik = new KorisnikDTO(korisnik);
	}

	public ZahtevDTO(Zahtjev zahtev) {
		this.id = zahtev.getId();
		this.status = zahtev.getStatus();
		this.tip = zahtev.getTip();
		this.korisnik = new KorisnikDTO(zahtev.getKorisnik());
		this.pocetak = zahtev.getPocetak();
		this.kraj = zahtev.getKraj();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StatusZahtjeva getStatus() {
		return status;
	}
	public void setStatus(StatusZahtjeva status) {
		this.status = status;
	}
	public TipZahtjeva getTip() {
		return tip;
	}
	public void setTip(TipZahtjeva tip) {
		this.tip = tip;
	}
	public LocalDate getPocetak() {
		return pocetak;
	}

	public void setPocetak(LocalDate pocetak) {
		this.pocetak = pocetak;
	}

	public LocalDate getKraj() {
		return kraj;
	}

	public void setKraj(LocalDate kraj) {
		this.kraj = kraj;
	}

	public KorisnikDTO getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(KorisnikDTO korisnik) {
		this.korisnik = korisnik;
	}

	
	
}
