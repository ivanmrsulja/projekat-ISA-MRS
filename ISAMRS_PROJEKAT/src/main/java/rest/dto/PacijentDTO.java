package rest.dto;

import rest.domain.Pacijent;
import rest.domain.TipKorisnika;

public class PacijentDTO {
	
	private KorisnikDTO korisnik;
	private int brojPoena;
	private TipKorisnika tip;
	
	public PacijentDTO() {}
	public PacijentDTO(Pacijent p) {
		korisnik = new KorisnikDTO(p);
		brojPoena = p.getBrojPoena();
		tip = p.getTipKorisnika();
	}
	
	public KorisnikDTO getKorisnik() {
		return korisnik;
	}
	
	public void setKorisnik(KorisnikDTO korisnik) {
		this.korisnik = korisnik;
	}
	
	public int getBrojPoena() {
		return brojPoena;
	}
	
	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}
	
	public TipKorisnika getTip() {
		return tip;
	}
	
	public void setTip(TipKorisnika tip) {
		this.tip = tip;
	}
	
}
