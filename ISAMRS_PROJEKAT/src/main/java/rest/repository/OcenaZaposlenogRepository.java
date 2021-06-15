package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rest.domain.OcenaZaposlenog;

@Repository
public interface OcenaZaposlenogRepository extends JpaRepository<OcenaZaposlenog, Integer> {
	
	@Query("select o from OcenaZaposlenog o where o.pacijent.id = ?1 and o.zaposleni.id = ?2")
	public OcenaZaposlenog ocenaZaKorisnika(int idPacijenta, int idZaposlenog);
	
}
