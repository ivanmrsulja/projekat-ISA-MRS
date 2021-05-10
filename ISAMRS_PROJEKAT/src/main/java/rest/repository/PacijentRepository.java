package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.Apoteka;
import rest.domain.Pacijent;
import rest.domain.Preparat;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer> {


	@Query("select a from Pacijent p join p.apoteke a where p.id = ?1")
	Collection<Apoteka> getPharmaciesForUser(int id);
	
	@Query("select a from Pacijent p join p.alergije a where p.id = ?1")
	Collection<Preparat> getAllergiesForUser(int id);
	
	@Query("select p from Pacijent p join fetch p.penali")
	Collection<Pacijent> getWithPenalities();
	
	@Query("select size(pen) from Pacijent p join p.penali pen where p.id = ?1")
	int getNumOfPenalities(int id);

	@Query("select p from Pacijent p join fetch p.apoteke where p.id = ?1")
	Pacijent getPatientWithPharmacies(int idPacijenta);

	@Query("select p from Pacijent p join p.apoteke ap where ?1 in ap.id")
	Collection<Pacijent> getPatientsSubscribedToPharmacy(int idApoteke);
}