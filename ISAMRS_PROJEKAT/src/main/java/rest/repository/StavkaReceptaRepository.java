package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.StavkaRecepta;

@Repository
public interface StavkaReceptaRepository extends JpaRepository<StavkaRecepta, Integer>{

}
