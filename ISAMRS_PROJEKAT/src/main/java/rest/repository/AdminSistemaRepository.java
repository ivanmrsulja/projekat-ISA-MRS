package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.AdminSistema;
import rest.domain.Apoteka;
import rest.domain.Pacijent;
import rest.domain.Preparat;
import rest.domain.Zalba;

public interface AdminSistemaRepository extends JpaRepository<AdminSistema, Integer> {

	
	@Query("select a from AdminSistema a where a.username = ?1")
	AdminSistema getPatientByUser(String username);

}