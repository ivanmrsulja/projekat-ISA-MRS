package rest.dto;

import rest.domain.AdminApoteke;
import rest.domain.Apoteka;

public class AdminApotekeDTO {

	private KorisnikDTO korisnik;

	public AdminApotekeDTO() {
	}

	public AdminApotekeDTO(AdminApoteke a) {
		this.korisnik = new KorisnikDTO(a);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminApotekeDTO other = (AdminApotekeDTO) obj;
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

}
