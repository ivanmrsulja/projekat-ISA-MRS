package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Pregled;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Integer> {

}
