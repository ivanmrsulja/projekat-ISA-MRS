package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Rezervacija;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {
	
	@Query("select r from Rezervacija r where r.pacijent.id = ?1 and r.status = 0")
	Collection<Rezervacija> rezervacijeZaKorisnika(int id);
}
