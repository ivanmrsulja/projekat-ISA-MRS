package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.AdminApoteke;
import rest.domain.Apoteka;

public interface AdminApotekeRepository extends JpaRepository<AdminApoteke, Integer>{
	
	@Query("select a.apoteka from AdminApoteke a where a.id = ?1")
	Apoteka getApoteka(int id);

}