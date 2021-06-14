package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.NaruceniProizvod;

@Repository
public interface NaruceniProizvodRepository extends JpaRepository<NaruceniProizvod, Integer>{

}
