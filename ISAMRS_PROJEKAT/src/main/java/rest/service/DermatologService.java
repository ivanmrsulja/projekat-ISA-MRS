package rest.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.domain.Pregled;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;


public interface DermatologService {

	Dermatolog findOne(int id);

	Collection<Dermatolog> findAll();
	Collection<Dermatolog> findAllForPharmacy(int id);

	Korisnik create(Dermatolog user) throws Exception;

	void delete(int id);

	Dermatolog update(KorisnikDTO user) throws Exception;
	
	void zavrsi(PregledDTO pregled, int id) throws Exception;
	
	public void updateAlergije(int pid,int aid);
	
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediSedmica(HttpSession s);
	
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediMesec(HttpSession s);
	
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediGodina(HttpSession s);
	
	public Set<PreparatDTO> proveriAlergije(int pid);
	
	public Pregled registerExamination(PregledDTO examinationDTO, Integer  dermatologistId, Integer  pharmacyId) throws Exception;
}