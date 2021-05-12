package rest.repository;

import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Rezervacija;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
	
	@Query("select r from Rezervacija r where r.pacijent.id = ?1 and (r.status = 0 or r.status = 1)")
	Collection<Rezervacija> rezervacijeZaKorisnika(int id);
	
	@Query("select r from Rezervacija r where r.apoteka.id = ?1 and r.pacijent.id = ?2 and r.status = 1")
	Collection<Rezervacija> rezervacijaUApoteci(int idApoteke, int idPacijenta);
	
	@Query("select r from Rezervacija r where r.preparat.id = ?1 and r.pacijent.id = ?2 and r.status = 1")
	Collection<Rezervacija> rezervacijaLijeka(int idLijeka, int idPacijenta);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("select r from Rezervacija r where r.id = ?1")
	Rezervacija findOneById(int id);
}
