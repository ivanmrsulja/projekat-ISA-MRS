package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.DostupanProizvod;

@Repository
public interface DostupanProizvodRepository extends JpaRepository<DostupanProizvod, Integer>{

}
