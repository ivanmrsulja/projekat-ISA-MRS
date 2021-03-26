package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Penal;

@Repository
public interface PenalRepository extends JpaRepository<Penal, Integer> {
	
	@Query("select p from Penal p where p.pacijent.id = ?1")
	Collection<Penal> penalForUser(int id);
}
