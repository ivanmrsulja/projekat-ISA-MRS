package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Dermatolog;

@Repository
public interface DermatologRepository extends JpaRepository<Dermatolog, Integer>{
	
}
