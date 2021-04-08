package rest.service;

import java.util.Collection;

import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;


public interface FarmaceutService {

	Farmaceut findOne(int id);

	Collection<Farmaceut> findAll();
	Collection<Farmaceut> findAllForPharmacy(int id);

	Farmaceut create(FarmaceutDTO user, int idApoteke) throws Exception;

	void delete(int id);

	Farmaceut update(KorisnikDTO user) throws Exception;
	
}