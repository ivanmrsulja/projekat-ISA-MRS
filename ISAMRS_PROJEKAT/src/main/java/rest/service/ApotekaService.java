package rest.service;

import org.springframework.data.domain.Page;

import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;

public interface ApotekaService {

	Page<ApotekaDTO> getAllDrugStores(int stranica);

	Apoteka getByID(int id);

	void update(ApotekaDTO apoteka) throws Exception;
	
	Apoteka getForAdmin(int id);
}