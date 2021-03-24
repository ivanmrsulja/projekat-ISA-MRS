package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rest.domain.TipKorisnika;

@Repository
public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Integer> {

}
