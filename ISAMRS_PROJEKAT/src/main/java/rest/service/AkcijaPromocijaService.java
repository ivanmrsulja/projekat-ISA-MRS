package rest.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import rest.dto.ApotekaDTO;

@Service
public interface AkcijaPromocijaService {
	
	Collection<ApotekaDTO> getForUser(int id);
	
}
