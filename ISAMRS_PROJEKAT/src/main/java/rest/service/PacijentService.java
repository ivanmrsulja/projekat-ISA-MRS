package rest.service;

import java.util.Collection;

import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Zalba;
import rest.dto.PreparatDTO;
import rest.dto.ZalbaDTO;

public interface PacijentService {
	
	Collection<PreparatDTO> allergies(int id);
	
	Collection<PreparatDTO> addAllergy(int id, int idPrep);
	
	Collection<PreparatDTO> removeAllergy(int id, int idPrep);
	
	Collection<Pacijent> getAll();
	
	Collection<Pacijent> getMine(int id, String param, String criteria);
	
	void removeAllPenalities();

	Pacijent getOne(int id);
	
	void addPenal(int id, Penal p);
	
	Collection<ZalbaDTO> getZalbeForPatient(int id);
	ZalbaDTO getZalbaForPatient(int id, int zalId);
	Collection<String> getAllAppealable(int id);
	void sendZalba(ZalbaDTO zdto);

}