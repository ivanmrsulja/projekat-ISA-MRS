package rest.repository;

import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select z from Zaposlenje z where z.korisnik.id = ?1 and z.apoteka.id = ?2")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Zaposlenje zaposlenjeZaStrucnjaka(int idDermatologa, int idApoteke);

	@Query("select z from Zaposlenje z where z.korisnik.id = ?1")
	Zaposlenje zaposlenjeFarmaceuta(int idFarmaceuta);

	@Transactional
	@Modifying
	@Query("delete from Zaposlenje z where z.korisnik.id = ?1")
	void deleteForPharmacist(int id);

	@Query("select z from Zaposlenje z where z.korisnik.id = ?1")
	public Collection<Zaposlenje> getEmploymentsForDermatologist(int id);

	@Query("select z from Zaposlenje z where z.korisnik.id = ?2 and z.apoteka.id = ?1")
	public Zaposlenje getEmploymentForDermatologist(int pharmacyId, int dermatologistId);

	@Transactional
	@Modifying
	@Query("delete from Zaposlenje z where z.id = ?1")
	public void deleteForDermatologist(int zaposlenjeId);
}
