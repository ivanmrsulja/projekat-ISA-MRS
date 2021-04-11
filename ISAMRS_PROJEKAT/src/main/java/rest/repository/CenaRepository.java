package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.DostupanProizvod;
import rest.domain.Preparat;

@Repository
public interface CenaRepository extends JpaRepository<Cena, Integer> {
	
	@Query("select dp.preparat from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce)")
	public Collection<Preparat> drugsForPharmacy(int id);
	
	@Query("select c.apoteka from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1")
	Collection<Apoteka> getPharmaciesForDrug(int id);
	
	@Query("select dp from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1 and c.apoteka.id = ?2")
	DostupanProizvod getCount(int idp, int ida);
	
	@Query("select dp.cena from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1 and c.apoteka.id = ?2")
	double getPrice(int idp, int ida);
}
