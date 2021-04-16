package rest.service;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pregled;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;

public interface PregledService {
	
	Collection<PregledDTO> zakaziPregled(int idp, int idpa) throws Exception;
	void otkaziPregled(int idp) throws Exception;
	
	@Async
	void sendConfirmationEmail(KorisnikDTO user);
	
	Collection<Pregled> dobaviZaDermatologa(int id);
	
	Pregled dobaviPregledZa(Integer id);
}
