package rest.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;

import rest.domain.AdminApoteke;
import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.TipKorisnika;
import rest.dto.AdminApotekeDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PacijentDTO;
import rest.dto.PharmacyAdminDTO;
import rest.dto.PregledDTO;
import rest.dto.RezervacijaDTO;

public interface KorisnikService {

	Collection<Korisnik> findAll();

	Korisnik findOne(int id);

	Korisnik create(Korisnik user) throws Exception;

	Korisnik update(KorisnikDTO user) throws Exception;
	
	Korisnik updateSupp(KorisnikDTO user, KorisnikDTO updateInfo) throws Exception;
	
	Korisnik changePass(KorisnikDTO user) throws Exception;

	void delete(int id);
	
	Collection<Penal> getPenali(int id);
	
	Page<PregledDTO> preglediZaKorisnika(int id, int page, String criteria);
	Page<PregledDTO> zakazivanjaZaKorisnika(int id, int page);
	Collection<RezervacijaDTO> rezervacijeZaKorisnika(int id);
	PacijentDTO findPacijentById(int id);
	AdminApotekeDTO findAdminApotekeById(int id);
	TipKorisnika pocetniTip();
	AdminApoteke createAdminPharm(PharmacyAdminDTO user) throws Exception;
	
	@Async
	void sendRegistrationMail(Pacijent p);
}