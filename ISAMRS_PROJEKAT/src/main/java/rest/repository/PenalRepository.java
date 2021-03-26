package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Penal;

@Repository
public interface PenalRepository extends JpaRepository<Penal, Integer> {

}