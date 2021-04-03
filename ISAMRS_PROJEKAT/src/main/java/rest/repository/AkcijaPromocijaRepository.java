package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.domain.AkcijaPromocija;

public interface AkcijaPromocijaRepository extends JpaRepository<AkcijaPromocija, Integer> {
	
}
