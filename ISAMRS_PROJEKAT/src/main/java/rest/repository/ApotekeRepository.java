package rest.repository;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;
import rest.domain.Pregled;

@Repository
public interface ApotekeRepository extends JpaRepository<Apoteka, Integer> {
	
	@Query("select a from Apoteka a "
			+ "where upper(a.naziv) like %?1% and upper(a.lokacija.ulica) like %?2%"
			+ " and a.ocena >= ?3 and a.ocena <= ?4"
			+ " and sqrt( pow(a.lokacija.sirina - ?5, 2) + pow(a.lokacija.duzina - ?6, 2)) * 111 <= ?7")
	public Page<Apoteka> findAll(Pageable pageable, String naziv, String adresa, double dOcena, double gOcena, double lat, double lon, double rastojanje);
	
	@Query("select count(a) from Apoteka a")
	public int getNumOf();
	
	@Query("select p from Apoteka a join a.pregledi p where p.status = 1 and p.tip = 1 and a.id = ?1")
	public Collection<Pregled> getPreCreated(int id);

	@Query("select a from Apoteka a where a in ?1")
	public Page<Apoteka> slobodneApoteke(Pageable pageable, ArrayList<Apoteka> apoteke);
	
	@Query("select a from Apoteka a")
	public Collection<Apoteka> getAll();

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select a from Apoteka a where a.id = ?1")
	public Apoteka getOneById(int pharmacyId);
	
}
