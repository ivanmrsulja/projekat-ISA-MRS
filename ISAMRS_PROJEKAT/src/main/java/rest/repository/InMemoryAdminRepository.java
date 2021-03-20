package rest.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import rest.domain.Dobavljac;
import rest.domain.Korisnik;
import rest.domain.Narudzbenica;
import rest.domain.Ponuda;
import rest.domain.StatusPonude;

@Repository
public class InMemoryAdminRepository implements AdminRepository {
	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Ponuda> offers = new ConcurrentHashMap<Long, Ponuda>();
	
	@PostConstruct
	public void initMetoda() throws Exception {
		System.out.println("Poziv init metode posle inicijalizacije komponente");
		Dobavljac d = new Dobavljac(3, "Pera", "Peric", "perica99", "perhan123", null, null, null, null, null);
		Narudzbenica n = new Narudzbenica(1, LocalDate.parse("2020-04-07"), null);
		offers.put(1l, new Ponuda(1, StatusPonude.CEKA_NA_ODGOVOR, 400.23, LocalDate.parse("2020-04-04"), n, d));
		counter.incrementAndGet();
		offers.put(2l, new Ponuda(2, StatusPonude.PRIHVACENA, 500.23, LocalDate.parse("2020-04-07"), n, d));
		counter.incrementAndGet();
		offers.put(3l, new Ponuda(3, StatusPonude.ODBIJENA, 500.23, LocalDate.parse("2020-04-06"), n, d));
		counter.incrementAndGet();
	}
	
	@Override
	public Collection<Ponuda> findAllOffers() {
		return this.offers.values();
		
	}
	
}
