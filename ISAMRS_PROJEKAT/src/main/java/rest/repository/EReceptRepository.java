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
}
