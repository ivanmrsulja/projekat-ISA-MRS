package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Zaposlenje;

@Repository
public interface ZaposlenjeRepository extends JpaRepository<Zaposlenje, Integer> {

}
