package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.NarudzbenicaDTO;


public interface AdminService {
	Collection<Ponuda> findAllOffers();

	@Async
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo);

	public ArrayList<NarudzbenicaDTO> findOrdersForPharmacy(int idAdmina);
}
