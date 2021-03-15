package rest.repository;

import java.util.Collection;

import rest.domain.Korisnik;

public interface KorisnikRepository {

	Collection<Korisnik> findAll();

	Korisnik create(Korisnik user);

	Korisnik findOne(Long id);
	
	Korisnik update(Korisnik user);

	void delete(Long id);

}
