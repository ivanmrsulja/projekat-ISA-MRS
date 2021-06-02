package rest.repository;

import java.util.Collection;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import rest.domain.Farmaceut;
import rest.domain.Pregled;


@Repository
public interface FarmaceutRepository extends JpaRepository<Farmaceut, Integer>{
	
	@Query("select f from Farmaceut f where f.zaposlenje.apoteka.id = ?1")
	Collection<Farmaceut> getWithEmployments(int idApoteke);
	
	@Query("select p from Pregled p where p.zaposleni.id = ?1 and p.pacijent.id = ?2 and p.tip = 0 and p.status = 2")
	Collection<Pregled> getConsultmentsForPatientAndPharmacist(int idFarmaceuta, int idPacijenta);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select f from Farmaceut f where f.id = ?1")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Farmaceut findOneById(int id);
	
	@Query("select distinct f from Farmaceut f")
	Collection<Farmaceut> getAllPharmacist();
}