package rest.service;

import java.util.Collection;

import rest.dto.PreparatDTO;

public interface PacijentService {
	
	Collection<PreparatDTO> allergies(int id);
	
	Collection<PreparatDTO> addAllergy(int id, int idPrep);
	
	Collection<PreparatDTO> removeAllergy(int id, int idPrep);
}