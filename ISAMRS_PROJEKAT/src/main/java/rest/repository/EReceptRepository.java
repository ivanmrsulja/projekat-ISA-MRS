package rest.repository;

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
}
