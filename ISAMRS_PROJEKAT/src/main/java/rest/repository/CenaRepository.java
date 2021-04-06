package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.Cena;
import rest.domain.Preparat;

public interface CenaRepository extends JpaRepository<Cena, Integer> {
	
	@Query("select dp.preparat from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce)")
	public Collection<Preparat> drugsForPharmacy(int id);
	
}
