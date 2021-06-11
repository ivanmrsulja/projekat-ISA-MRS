package rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.AdminSistema;

public interface AdminSistemaRepository extends JpaRepository<AdminSistema, Integer> {

	
	@Query("select a from AdminSistema a where a.username = ?1")
	AdminSistema getPatientByUser(String username);

}