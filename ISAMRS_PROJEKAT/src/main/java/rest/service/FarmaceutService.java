package rest.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import rest.domain.Farmaceut;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;


public interface FarmaceutService {

	Farmaceut findOne(int id);

	Collection<Farmaceut> findAll();
	Collection<Farmaceut> findAllForPharmacy(int id);

	Farmaceut create(FarmaceutDTO user, int idApoteke) throws Exception;

	void delete(int id);

	Farmaceut update(KorisnikDTO user) throws Exception;

	public boolean checkIfPharmacistHasAppointments(int pharmacistId, int pharmacyId);

	public void deletePharmacist(int pharmacistId);
	
	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaSedmica(HttpSession s);
	
	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaMesec(HttpSession s);
	
	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaGodina(HttpSession s);
	
}