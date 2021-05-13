package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Preparat;

@Repository
public interface PreparatRepository extends JpaRepository<Preparat, Integer> {
	
	@Query("select p from Preparat p where p.naziv = ?1")
	public Preparat getPreparatByName(String name);

}
