package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Dobavljac;

@Repository
public interface DobavljacRepository extends JpaRepository<Dobavljac, Integer>{

	@Query("select d from Dobavljac d where d.username = ?1")
	public Dobavljac getSupplierByUsername(String username);

}
