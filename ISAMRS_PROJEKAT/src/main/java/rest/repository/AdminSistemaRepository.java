package rest.repository;


import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rest.domain.AdminSistema;

public interface AdminSistemaRepository extends JpaRepository<AdminSistema, Integer> {

	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from AdminSistema a where a.username = ?1")
	AdminSistema getAdminByUser(String username);

}