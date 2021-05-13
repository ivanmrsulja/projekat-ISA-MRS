package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Narudzbenica;

@Repository
public interface NarudzbenicaRepozitory extends JpaRepository<Narudzbenica, Integer> {
	
	@Query("select n from Narudzbenica n join fetch n.naruceniProizvodi")
	public Collection<Narudzbenica> getAllWithProizvodi();

	@Query("select distinct n from Narudzbenica n join fetch n.naruceniProizvodi where n.adminApoteke.id = ?1")
	public Collection<Narudzbenica> getAllForPharmacy(int idAdmina);
}
