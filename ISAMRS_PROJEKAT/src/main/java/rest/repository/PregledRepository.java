package rest.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Pacijent;
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
	
	@Query("select distinct pr.pacijent from Pregled pr where pr.zaposleni.id =?1 ")
	Collection<Pacijent> getMine(int id);

	@Query("select p from Pregled p where p.pacijent.id = ?1 and p.status = 0")
	Collection<Pregled> rezervacijeZaKorisnika(int id);
}
