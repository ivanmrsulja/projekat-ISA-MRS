package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.TipKorisnika;

@Repository
public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Integer> {

	
	@Query("select tp from TipKorisnika tp order by tp.bodovi desc")
	public Collection<TipKorisnika> getAllOrdered();
	
	@Query("select tp from TipKorisnika tp where tp.bodovi = ?1")
	TipKorisnika getTipWithPoints(int bodovi);
}
