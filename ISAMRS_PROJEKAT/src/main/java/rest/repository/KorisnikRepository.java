package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {


}
