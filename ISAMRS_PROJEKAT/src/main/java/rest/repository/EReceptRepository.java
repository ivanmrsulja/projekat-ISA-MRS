package rest.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.ERecept;

@Repository
public interface EReceptRepository extends JpaRepository<ERecept, Integer> {
	
	@Query("select r from ERecept r where r.pacijent.id = ?1")
	public Collection<ERecept> getForUser(int id);
	
	@Query("select r from ERecept r where r.pacijent.id = ?1 and r.apoteka.id = ?2")
	public Collection<ERecept> zaApotekuIKorisnika(int idPacijenta, int idApoteke);
	
	@Query("select r from ERecept r join r.stavkaRecepata sr where r.pacijent.id = ?1 and ?2 in sr.preparat.id")
	public Collection<ERecept> zaPreparatIKorisnika(int idPacijenta, int idPreparata);

	@Query("select date_trunc('month', r.datumIzdavanja) as datum, sum(sr.kolicina) as kolicina from ERecept r join r.stavkaRecepata sr "
			+ "where r.apoteka.id = ?3 and r.status = 1 and r.datumIzdavanja >= ?1 and r.datumIzdavanja <= ?2 group by date_trunc('month', r.datumIzdavanja)")
	public ArrayList<Object[]> getProcessedUsagePerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('day', r.datumIzdavanja) as datum, sum(sr.kolicina) as kolicina from ERecept r join r.stavkaRecepata sr "
			+ "where r.apoteka.id = ?3 and r.status = 1 and r.datumIzdavanja >= ?1 and r.datumIzdavanja <= ?2 group by date_trunc('day', r.datumIzdavanja)")
	public ArrayList<Object[]> getProcessedUsageForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);
	
	@Query("select date_trunc('month', r.datumIzdavanja) as datum, sum(sr.kolicina * (select dp.cena from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?3 "
			+ "and dp.preparat.id = sr.preparat.id and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?1))) as cena "
			+ "from ERecept r join r.stavkaRecepata sr where r.apoteka.id = ?3 and r.status = 1 and r.datumIzdavanja >= ?1 and r.datumIzdavanja <= ?2 "
			+ "group by date_trunc('month', r.datumIzdavanja)")
	public ArrayList<Object[]> getProcessedIncomePerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId, LocalDate now);

	@Query("select date_trunc('day', r.datumIzdavanja) as datum, sum(sr.kolicina * (select dp.cena from Cena c join c.dostupniProizvodi dp where c.apoteka.id = ?3 "
			+ "and dp.preparat.id = sr.preparat.id and c.pocetakVazenja = (select max(ce.pocetakVazenja) from Cena ce where ce.pocetakVazenja <= ?1))) as cena "
			+ "from ERecept r join r.stavkaRecepata sr where r.apoteka.id = ?3 and r.status = 1 and r.datumIzdavanja >= ?1 and r.datumIzdavanja <= ?2 "
			+ "group by date_trunc('day', r.datumIzdavanja)")
	public ArrayList<Object[]> getProcessedIncomeForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId, LocalDate now);
}
