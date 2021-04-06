package rest.service;

import java.util.Collection;

import rest.domain.Preparat;

public interface PreparatService {

	Collection<Preparat> getAll();
	Collection<Preparat> getAllForPharmacy(int id);

	Preparat getOne(int id);
}
