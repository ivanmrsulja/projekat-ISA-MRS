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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + brojPoena;
		result = prime * result + ((korisnik == null) ? 0 : korisnik.hashCode());
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PacijentDTO other = (PacijentDTO) obj;
		if (other.getKorisnik().getId() == this.korisnik.getId()) {
			return true;
		}
		return false;
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