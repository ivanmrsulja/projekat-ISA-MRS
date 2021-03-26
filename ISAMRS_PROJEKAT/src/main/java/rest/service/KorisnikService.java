package rest.service;

import java.util.Collection;

import rest.domain.Korisnik;
import rest.domain.Penal;

public interface KorisnikService {

	Collection<Korisnik> findAll();

	Korisnik findOne(int id);

	Korisnik create(Korisnik user) throws Exception;

	Korisnik update(Korisnik user) throws Exception;

	void delete(int id);
	
	Collection<Penal> getPenali(int id);
	
}