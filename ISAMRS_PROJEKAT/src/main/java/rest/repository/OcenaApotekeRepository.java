package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.OcenaApoteke;

@Repository
public interface OcenaApotekeRepository extends JpaRepository<OcenaApoteke, Integer> {
	
	@Query("select o from OcenaApoteke o where o.pacijent.id = ?1 and o.apoteka.id = ?2")
	OcenaApoteke zaKorisnika(int id, int idApoteke);
	
}
