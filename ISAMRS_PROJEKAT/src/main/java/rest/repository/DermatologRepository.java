package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.domain.Dermatolog;


public interface DermatologRepository extends JpaRepository<Dermatolog, Integer>{
	
}
