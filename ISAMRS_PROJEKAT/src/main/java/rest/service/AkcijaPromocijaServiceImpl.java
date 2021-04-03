package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;
import rest.repository.PacijentRepository;

@Service
public class AkcijaPromocijaServiceImpl implements AkcijaPromocijaService{

	private PacijentRepository pacijentRepo;
	
	@Autowired
	public AkcijaPromocijaServiceImpl(PacijentRepository pacijentRepo) {
		this.pacijentRepo = pacijentRepo;
	}
	
	@Override
	public Collection<ApotekaDTO> getForUser(int id) {
		Collection<Apoteka> result =  pacijentRepo.getPharmaciesForUser(id);
		ArrayList<ApotekaDTO> retVal = new ArrayList<ApotekaDTO>();
		for(Apoteka a : result) {
			retVal.add(new ApotekaDTO(a));
		}
		return retVal;
	}

}
