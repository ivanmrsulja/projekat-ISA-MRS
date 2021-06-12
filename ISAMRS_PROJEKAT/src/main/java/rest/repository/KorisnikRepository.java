package rest.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.Korisnik;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select k from Korisnik k where k.username = ?1")
	Korisnik getUserByUsername(String username);


}
