package rest.repository;

import java.util.Collection;

import rest.domain.Apoteka;

public interface ApotekeRepository {

	Collection<Apoteka> findAllDrugStores();

	Apoteka findByID(int id);

	Apoteka update(Apoteka apoteka);

}
