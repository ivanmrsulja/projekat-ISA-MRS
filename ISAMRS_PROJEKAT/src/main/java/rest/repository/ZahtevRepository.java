package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Zahtjev;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtjev, Integer>{

	@Query("select z from Zahtjev z where z.korisnik.id in ?1")
	Collection<Zahtjev> getAllForPharmacy(Collection<Integer> ids);
	
	@Query("select z from Zahtjev z where z.korisnik.id = ?1 and z.status != 1")
	public Collection<Zahtjev> getAcceptedAndPendingForUser(int userId);

}
