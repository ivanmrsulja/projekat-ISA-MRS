package rest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import rest.domain.AkcijaPromocija;
import rest.dto.ApotekaDTO;

@Service
public interface AkcijaPromocijaService {
	
	Collection<ApotekaDTO> getForUser(int id);
	
	void removeForUser(int idUser, int idApo);
	
	
	AkcijaPromocija create(AkcijaPromocija ap) throws Exception;

}
