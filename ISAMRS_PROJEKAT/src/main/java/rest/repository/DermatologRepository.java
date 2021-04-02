package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Dermatolog;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Integer>{
	
	@Query("select d from Dermatolog d inner join fetch d.zaposlenja z where z.apoteka.id = ?1")
	Collection<Dermatolog> getWithEmployments(int idApoteke);
	
}