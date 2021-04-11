package rest.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.dto.CenaDTO;
import rest.dto.KorisnikDTO;

public interface PreparatService {

	Collection<Preparat> getAll();
	Collection<Preparat> getAllForPharmacy(int id);
	Collection<CenaDTO> getPharmaciesForDrug(int id);
	Preparat getOne(int id);
	void otkazi(int idr, int id) throws Exception;
	Rezervacija rezervisi(int idp, int idpa, int ida, LocalDate datum) throws Exception;
	
	@Async
	void sendConfirmationEmail(KorisnikDTO user, Rezervacija p);
}
