package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Ponuda;

@Repository
public interface PonudaRepository extends JpaRepository<Ponuda, Integer> {
}
