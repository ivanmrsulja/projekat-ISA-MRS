package rest.repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

	
	@Query("select date_trunc('month', r.datumPreuzimanja) as datum, sum(r.cena) as cena from Rezervacija r where r.datumPreuzimanja >= ?1 and r.datumPreuzimanja <= ?2 and r.status = 1 and r.apoteka.id = ?3 group by date_trunc('month', r.datumPreuzimanja)")
	public ArrayList<Object[]> getIncomeFromReservationsPerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('day', r.datumPreuzimanja) as datum, sum(r.cena) as cena from Rezervacija r where r.datumPreuzimanja >= ?1 and r.datumPreuzimanja <= ?2 and r.status = 1 and r.apoteka.id = ?3 group by date_trunc('day', r.datumPreuzimanja)")
	public ArrayList<Object[]> getIncomeFromReservationsForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('month', r.datumPreuzimanja) as datum, count(r.id) as kolicina from Rezervacija r where r.datumPreuzimanja >= ?1 and r.datumPreuzimanja <= ?2 and r.status = 1 and r.apoteka.id = ?3 group by date_trunc('month', r.datumPreuzimanja)")
	public ArrayList<Object[]> getDrugsUsagePerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('day', r.datumPreuzimanja) as datum, count(r.id) as kolicina from Rezervacija r where r.datumPreuzimanja >= ?1 and r.datumPreuzimanja <= ?2 and r.status = 1 and r.apoteka.id = ?3 group by date_trunc('day', r.datumPreuzimanja)")
	public ArrayList<Object[]> getDrugsUsageForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);
	
}
