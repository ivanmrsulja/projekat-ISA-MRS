package rest.repository;

import java.util.Collection;

import rest.domain.Ponuda;

public interface AdminRepository {
	Collection<Ponuda> findAllOffers();
}
