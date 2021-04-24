package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.OcenaPreparata;

@Repository
public interface OcenaPreparataRepository extends JpaRepository<OcenaPreparata, Integer> {
	
	@Query("select o from OcenaPreparata o where o.pacijent.id = ?1 and o.preparat.id = ?2")
	OcenaPreparata zaKorisnika(int id, int idPreparata);
	
}
