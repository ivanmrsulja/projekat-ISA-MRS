package rest.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import rest.domain.Apoteka;
import rest.domain.Lokacija;

@Repository
public class InMemoryApotekeRepository implements ApotekeRepository {
	
	private final ConcurrentMap<Integer, Apoteka> apoteke = new ConcurrentHashMap<Integer, Apoteka>();
	
	@PostConstruct
	public void init() {
		Lokacija l1 = new Lokacija(1, 45.2474, 19.85112, "Bulevar Cara Lazara 3, Novi Sad");
		Lokacija l2 = new Lokacija(2, 45.2474, 19.85112, "Bulevar Cara Lazara 4, Novi Sad");
		Apoteka a = new Apoteka(1, "Benu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 3, 8, l1);
		Apoteka b = new Apoteka(2, "Lilly", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 0, 0, l2);
		apoteke.put(1, a);
		apoteke.put(2, b);
		System.out.println("Apoteke kreirane.");
	}
	
	@Override
	public Collection<Apoteka> findAllDrugStores() {
		return this.apoteke.values();
	}

	@Override
	public Apoteka findByID(int id) {
		return this.apoteke.get(id);
	}

	@Override
	public Apoteka update(Apoteka apoteka) {
		apoteke.put(apoteka.getId(), apoteka);
		return apoteka;
	}

}
