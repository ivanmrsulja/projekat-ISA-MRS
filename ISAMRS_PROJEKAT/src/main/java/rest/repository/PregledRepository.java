package rest.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rest.domain.Pregled;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Integer> {
	
	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 2")
	Page<Pregled> istorijaPregledaZaKorisnika(int id, Pageable page);
	
	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 0")
	Page<Pregled> rezervacijePregledaZaKorisnika(int id, Pageable page);
	
	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 2 order by p.datum desc")
	Page<Pregled> istorijaPregledaZaKorisnikaDatumSort(int id, Pageable page);
	
	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 2 order by p.cijena desc")
	Page<Pregled> istorijaPregledaZaKorisnikaCenaSort(int id, Pageable page);
	
	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 2 order by p.trajanje desc")
	Page<Pregled> istorijaPregledaZaKorisnikaTrajanjeSort(int id, Pageable page);
	
	@Query("select distinct pr from Pregled pr where pr.zaposleni.id =?1 ")
	Collection<Pregled> getMine(int id);

	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 0")
	Collection<Pregled> rezervacijeZaKorisnika(int id);
	
	@Query("select p from Pregled p where p.datum = ?1 and p.status = 0 and p.tip = 0 and p.zaposleni.id = ?2")
	Collection<Pregled> zauzetiFarmaceutiNaDan(LocalDate datum, int id);
	
	@Query("select p from Pregled p where p.pacijent.id = ?2 and p.apoteka.id = ?1 and p.status = 2")
	Collection<Pregled> preglediUApoteci(int idApoteke, int idPacijenta);

	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.status = 0")
	Collection<Pregled> preglediZaDermatologa(int id);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("select p from Pregled p where p.id = ?1")
	Pregled findOneById(int id);

	@Query("select p from Pregled p where p.apoteka.id = ?1 and p.tip = 0 and p.status = 1")
	public Collection<Pregled> getOpenConsultationsForPharmacy(int pharmacyId);

	@Query("select p from Pregled p where p.apoteka.id = ?1 and p.tip = 1 and p.status = 1")
	public Collection<Pregled> getOpenExaminationsForPharmacy(int pharmacyId);

	@Query("select date_trunc('month', p.datum) as datum, count(p.id) as count from Pregled p where p.datum >= ?1 and p.datum <= ?2 and p.status = 2 and p.apoteka.id = ?3 group by date_trunc('month', p.datum)")
	public ArrayList<Object[]> getExaminationsPerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('day', p.datum) as datum, count(p.id) as count from Pregled p where p.datum >= ?1 and p.datum <= ?2 and p.status = 2  and p.apoteka.id = ?3 group by date_trunc('day', p.datum)")
	public ArrayList<Object[]> getExaminationsForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('month', p.datum) as datum, sum(p.cijena) as cena from Pregled p where p.datum >= ?1 and p.datum <= ?2 and p.status = 2 and p.apoteka.id = ?3 group by date_trunc('month', p.datum)")
	public ArrayList<Object[]> getIncomeFromExaminationsPerMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Query("select date_trunc('day', p.datum) as datum, sum(p.cijena) as cena from Pregled p where p.datum >= ?1 and p.datum <= ?2 and p.status = 2  and p.apoteka.id = ?3 group by date_trunc('day', p.datum)")
	public ArrayList<Object[]> getIncomeFromExaminationsForMonth(LocalDate date_low, LocalDate date_high, int pharmacyId);

	@Transactional
	@Modifying
	@Query("update Pregled p set p.cijena = ?2 where p.id = ?1")
	public void updateExaminationPrice(int examinationId, double price);

	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.apoteka.id = ?2 and p.status != 2")
	public Collection<Pregled> getScheduledAndOpenExaminations(int dermatologistId, int pharmacyId);

	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.apoteka.id = ?2 and p.status = 0")
	public Collection<Pregled> getScheduledAppointments(int pharmacistId, int pharmacyId);

	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.apoteka.id = ?2 and p.status = 0 and p.datum >= ?3")
	public Collection<Pregled> getScheduledAppointments(int pharmacistId, int pharmacyId, LocalDate now);

	@Transactional
	@Modifying
	@Query("delete from Pregled p where p.zaposleni.id = ?2 and p.apoteka.id = ?1")
	public void deleteOpenAppointmentsForDermatologistForPharmacy(int pharmacyId, int dermatologistId);

}
