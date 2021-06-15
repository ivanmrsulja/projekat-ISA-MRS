package rest.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.DostupanProizvod;
import rest.domain.Preparat;

@Repository
public interface DostupanProizvodRepository extends JpaRepository<DostupanProizvod, Integer>{

	@Query("select dp from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) "
			+ "from Cena ce where ce.pocetakVazenja <= ?2)")
	public Collection<DostupanProizvod> getForPharmacy(int pharmacyId, LocalDate today);

	@Query("select p from Preparat p where p.id not in (select dp.preparat.id from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = "
			+ "(select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?2))")
	public Collection<Preparat> getProductsOutsidePharmacy(int pharmacyId, LocalDate today);

}
