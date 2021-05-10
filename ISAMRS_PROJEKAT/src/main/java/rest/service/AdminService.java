package rest.service;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;


public interface AdminService {
	Collection<Ponuda> findAllOffers();

	@Async
	void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo);
}
