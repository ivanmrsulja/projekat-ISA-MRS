package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.Farmaceut;


public interface FarmaceutRepository extends JpaRepository<Farmaceut, Integer>{
	
	@Query("select f from Farmaceut f where f.zaposlenje.apoteka.id = ?1")
	Collection<Farmaceut> getWithEmployments(int idApoteke);
}