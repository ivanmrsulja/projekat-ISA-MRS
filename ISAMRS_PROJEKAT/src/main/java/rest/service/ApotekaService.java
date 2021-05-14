package rest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.data.domain.Page;

import rest.domain.Apoteka;
import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.domain.Pregled;
import rest.dto.ApotekaDTO;
import rest.dto.PregledDTO;
import rest.util.ApotekaSearchParams;

public interface ApotekaService {

	Page<ApotekaDTO> getAllDrugStores(int stranica, ApotekaSearchParams params, double lat, double lon);
	
	Collection<ApotekaDTO> getAllPharmacies();
	
	Apoteka create(Apoteka user) throws Exception;

	Apoteka getByID(int id);
	
	Apoteka getByName(String name);

	void update(ApotekaDTO apoteka) throws Exception;
	
	Apoteka getForAdmin(int id);
	
	Collection<PregledDTO> getPregledi(int id, String criteria);
	
	Page<ApotekaDTO> apotekeZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, String criteria, int pageNum) throws Exception;
	
	Collection<Farmaceut> farmaceutiZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, int id, String criteria) throws Exception;
	
	Pregled zakaziSavetovanje(PregledDTO podaci, int idApoteke, int idFarmaceuta, int idPacijenta) throws Exception;
}