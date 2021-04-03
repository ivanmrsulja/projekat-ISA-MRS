package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.Apoteka;
import rest.domain.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer> {

	@Query("select a from Pacijent p join p.apoteke a where p.id = ?1")
	Collection<Apoteka> getPharmaciesForUser(int id);
}
