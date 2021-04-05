package rest.service;

import java.util.Collection;

import rest.dto.PregledDTO;

public interface PregledService {
	
	Collection<PregledDTO> zakaziPregled(int idp, int idpa) throws Exception;
	void otkaziPregled(int idp) throws Exception;
}
