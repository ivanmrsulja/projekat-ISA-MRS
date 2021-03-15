package rest.service;

import java.util.Collection;

import rest.domain.Korisnik;

public interface KorisnikService {

	Collection<Korisnik> findAll();

	Korisnik findOne(Long id);

	Korisnik create(Korisnik greeting) throws Exception;

	Korisnik update(Korisnik greeting) throws Exception;

	void delete(Long id);
	
}