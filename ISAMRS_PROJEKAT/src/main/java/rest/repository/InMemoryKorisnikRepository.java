package rest.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import rest.domain.Korisnik;

@Repository
public class InMemoryKorisnikRepository implements KorisnikRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Korisnik> users = new ConcurrentHashMap<Long, Korisnik>();
	
	@PostConstruct
	public void initMetoda() throws Exception {
		System.out.println("Poziv init metode posle inicijalizacije komponente");
		users.put(1l, new Korisnik(1l, "Ivan", "Mrsulja", "ivan123", "ivan123"));
	}
	
	@Override
	public Collection<Korisnik> findAll() {
		return this.users.values();
	}

	@Override
	public Korisnik create(Korisnik user) {
		Long id = user.getId();

		if (id == null) {
			id = counter.incrementAndGet();
			user.setId(id);
		}

		this.users.put(id, user);
		return user;
	}

	@Override
	public Korisnik findOne(Long id) {
		return this.users.get(id);
	}

	@Override
	public void delete(Long id) {
		this.users.remove(id);
	}

	@Override
	public Korisnik update(Korisnik greeting) {
		Long id = greeting.getId();

		if (id != null) {
			this.users.put(id, greeting);
		}

		return greeting;
	}

}
