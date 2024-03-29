package rest.repository;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Dermatolog;
import rest.domain.Pregled;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Integer>{
	
	@Query("select d from Dermatolog d inner join fetch d.zaposlenja z where z.apoteka.id = ?1")
	Collection<Dermatolog> getWithEmployments(int idApoteke);

	@Query("select distinct d from Dermatolog d inner join fetch d.zaposlenja z")
	Collection<Dermatolog> getAllDermatologists();
	
	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.pacijent.id = ?2 and p.tip = 1 and p.status = 2")
	Collection<Pregled> getExaminationsForPatientAndDermatologist(int idDermatologa, int idPacijenta);

	@Query("select d from Dermatolog d where d.id not in (select zap.korisnik.id from Apoteka a join a.zaposlenja zap where a.id = ?1)")
	public ArrayList<Dermatolog> getDermatologistsOutsidePharmacy(int pharmacyId);

	@Query("select d from Dermatolog d inner join fetch d.zaposlenja where d.id = ?1")
	public Dermatolog getDermatologistWithEmployments(int dermatologistId);
	
	@Query("select distinct d from Dermatolog d")
	Collection<Dermatolog> getAllDerme();
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("select d from Dermatolog d where d.id = ?1")
	public Dermatolog getOneById(int id);
	
}