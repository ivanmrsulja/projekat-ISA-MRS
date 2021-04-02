package rest.service;

import java.util.Collection;

import rest.domain.Preparat;

public interface PreparatService {

	Collection<Preparat> getAll();
	
	Preparat getOne(int id);
}
