package rest.repository;

import java.util.Collection;

import rest.domain.Korisnik;

public interface KorisnikRepository {

	Collection<Korisnik> findAll();

	Korisnik create(Korisnik user);

	Korisnik findOne(int id);
	
	Korisnik update(Korisnik user);

	void delete(int id);

}
