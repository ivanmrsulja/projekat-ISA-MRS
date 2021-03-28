package rest.service;

import java.util.Collection;

import rest.dto.EReceptDTO;

public interface EreceptService {
	Collection<EReceptDTO>getForUser(int id, EReceptSortFilterParams parameters);
}
