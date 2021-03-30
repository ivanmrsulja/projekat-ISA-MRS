package rest.service;

import java.util.Collection;

import rest.dto.EReceptDTO;
import rest.dto.StavkaReceptaDTO;

public interface EreceptService {
	
	Collection<EReceptDTO>getForUser(int id, EReceptSortFilterParams parameters);
	
	Collection<StavkaReceptaDTO> getStavkeRecepta(int id);
}
