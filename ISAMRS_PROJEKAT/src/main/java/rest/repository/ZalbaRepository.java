package rest.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rest.domain.Zalba;

public interface ZalbaRepository extends JpaRepository<Zalba, Integer> {
	@Query("select z from Zalba z")
	public Collection<Zalba> getAll();
}
