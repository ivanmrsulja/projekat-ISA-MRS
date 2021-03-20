package rest.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import rest.domain.Korisnik;

@Repository
public class InMemoryKorisnikRepository implements KorisnikRepository {

	private static AtomicInteger counter = new AtomicInteger();

	private final ConcurrentMap<Integer, Korisnik> users = new ConcurrentHashMap<Integer, Korisnik>();
	
	@PostConstruct
	public void initMetoda() throws Exception {
		System.out.println("Poziv init metode posle inicijalizacije komponente");
		users.put(1, new Korisnik(1, "Ivan", "Mrsulja", "ivan123", "ivan123","email",true,"telefon",null,null));
		counter.incrementAndGet();
	}
	
	@Override
	public Collection<Korisnik> findAll() {
		return this.users.values();
	}

	@Override
	public Korisnik create(Korisnik user) {
		int id = user.getId();

		if (id == 0) {
			id = counter.incrementAndGet();
			user.setId(id);
		}

		this.users.put(id, user);
		return user;
	}

	@Override
	public Korisnik findOne(int id) {
		return this.users.get(id);
	}

	@Override
	public void delete(int id) {
		this.users.remove(id);
	}

	@Override
	public Korisnik update(Korisnik greeting) {
		int id = greeting.getId();

		if (id != 0) {
			this.users.put(id, greeting);
		}

		return greeting;
	}

}
