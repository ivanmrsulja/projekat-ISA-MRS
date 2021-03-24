package rest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;

@Repository
public interface ApotekeRepository extends JpaRepository<Apoteka, Integer> {
	
	public Page<Apoteka> findAll(Pageable pageable);
	
	@Query("select count(a) from Apoteka a")
	public int getNumOf();
}
