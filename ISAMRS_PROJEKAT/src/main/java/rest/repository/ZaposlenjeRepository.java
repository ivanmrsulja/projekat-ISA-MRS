package rest.repository;

import java.time.LocalTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;
import rest.domain.Zaposlenje;

@Repository
public interface ZaposlenjeRepository extends JpaRepository<Zaposlenje, Integer> {
	
	@Query("select z.korisnik.id from Zaposlenje z where z.pocetakRadnogVremena <= ?1 and z.krajRadnogVremena > ?1 and z.korisnik.zaposlenjeKorisnika = 1")
	Collection<Integer> slobodniFarmaceuti(LocalTime time);
	
	@Query("select z.korisnik.id from Zaposlenje z where z.apoteka.id = ?2 and z.pocetakRadnogVremena <= ?1 and z.krajRadnogVremena > ?1 and z.korisnik.zaposlenjeKorisnika = 1")
	Collection<Integer> slobodniFarmaceutiApoteka(LocalTime time, int id);
	
	@Query("select z.apoteka from Zaposlenje z where z.korisnik.id = ?1")
	Apoteka apotekaZaFarmaceuta(int id);
}
