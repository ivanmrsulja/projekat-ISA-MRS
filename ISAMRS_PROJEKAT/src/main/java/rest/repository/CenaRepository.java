package rest.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.DostupanProizvod;
import rest.domain.Preparat;

@Repository
public interface CenaRepository extends JpaRepository<Cena, Integer> {
	
	@Query("select dp.preparat from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?2)")
	public Collection<Preparat> drugsForPharmacy(int id, LocalDate now);
	
	@Query("select c.apoteka from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1")
	Collection<Apoteka> getPharmaciesForDrug(int id);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("select dp from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1 and c.apoteka.id = ?2")
	DostupanProizvod getProduct(int idp, int ida);
	
	@Query("select dp.cena from Cena c join c.dostupniProizvodi dp where dp.preparat.id = ?1 and c.apoteka.id = ?2")
	double getPrice(int idp, int ida);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Cena> findById(int id);

	@Query("select c from Cena c join fetch c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?2)")
	public Cena getLatestPricelistForPharmacy(int idApoteke, LocalDate now);

	@Query("select dp from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?1 and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?2)")
	public Collection<DostupanProizvod> getDostupniProizvodiZaApoteku(int idApoteke, LocalDate now);

	@Query("delete from Cena c where c.krajVazenja = ?1")
	public void deleteOutdatedPromotion(LocalDate now);
	
	@Query("select c from Cena c")
	public Collection<Cena> getAll();
}
