package rest.service;

import java.util.Collection;

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;


public interface DermatologService {

	Dermatolog findOne(int id);

	Collection<Dermatolog> findAll();
	Collection<Dermatolog> findAllForPharmacy(int id);

	Korisnik create(Dermatolog user) throws Exception;

	void delete(int id);

	Dermatolog update(KorisnikDTO user) throws Exception;
	
	void zavrsi(PregledDTO pregled, int id) throws Exception;;
}