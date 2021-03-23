package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Lokacija;

@Repository
public interface LokacijaRepository extends JpaRepository<Lokacija, Integer> {

}
