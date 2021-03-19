package rest.service;

import java.util.Collection;

import rest.domain.Ponuda;


public interface AdminService {
	Collection<Ponuda> findAllOffers();
}
