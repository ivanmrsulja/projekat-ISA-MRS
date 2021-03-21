package rest.service;

import java.util.Collection;

import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;

public interface ApotekaService {

	Collection<Apoteka> getAllDrugStores();

	Apoteka getByID(int id);

	void update(ApotekaDTO apoteka) throws Exception;

}
