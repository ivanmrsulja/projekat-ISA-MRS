package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.domain.Farmaceut;


public interface FarmaceutRepository extends JpaRepository<Farmaceut, Integer>{
	
}
