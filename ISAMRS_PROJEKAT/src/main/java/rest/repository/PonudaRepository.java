package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rest.domain.Ponuda;

@Repository
public interface PonudaRepository extends JpaRepository<Ponuda, Integer> {

	@Query("select p from Ponuda p where p.narudzbenica.id = ?1")
	public Collection<Ponuda> getOffersForOrder(int orderId);

	@Query("select count(p) from Ponuda p where p.narudzbenica.id = ?1")
	public int getNumberOfOffersForOrder(int orderId);

	@Transactional
	@Modifying
	@Query("update Ponuda p set p.status = 1 where p.narudzbenica.id = ?1")
	public void updateOffersStatus(int orderId);

}
