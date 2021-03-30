package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.StavkaRecepta;

@Repository
public interface StavkaReceptaRepository extends JpaRepository<StavkaRecepta, Integer>{
	
	@Query("select sr from StavkaRecepta sr where sr.preparat.id = ?1")
	Collection<StavkaRecepta> getForRecipeId(int id);
}
