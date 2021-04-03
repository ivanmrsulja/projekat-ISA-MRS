package rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;

@Repository
public interface ApotekeRepository extends JpaRepository<Apoteka, Integer> {
	
	@Query("select a from Apoteka a "
			+ "where upper(a.naziv) like %?1% and upper(a.lokacija.ulica) like %?2%"
			+ " and a.ocena >= ?3 and a.ocena <= ?4"
			+ " and sqrt( pow(a.lokacija.sirina - ?5, 2) + pow(a.lokacija.duzina - ?6, 2)) * 111 <= ?7")
	public Page<Apoteka> findAll(Pageable pageable, String naziv, String adresa, double dOcena, double gOcena, double lat, double lon, double rastojanje);
	
	@Query("select count(a) from Apoteka a")
	public int getNumOf();
}
