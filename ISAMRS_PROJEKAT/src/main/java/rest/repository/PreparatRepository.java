package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Preparat;

@Repository
public interface PreparatRepository extends JpaRepository<Preparat, Integer> {

}
