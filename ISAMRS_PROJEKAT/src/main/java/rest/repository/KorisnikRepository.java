package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Korisnik;
import rest.domain.Pacijent;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {


}
