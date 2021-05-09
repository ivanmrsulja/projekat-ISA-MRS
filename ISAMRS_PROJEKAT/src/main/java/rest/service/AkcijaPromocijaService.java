package rest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import rest.domain.AkcijaPromocija;
import rest.dto.ApotekaDTO;

@Service
public interface AkcijaPromocijaService {
	
	Collection<ApotekaDTO> getForUser(int id);
	
	AkcijaPromocija create(AkcijaPromocija ap) throws Exception;

}
