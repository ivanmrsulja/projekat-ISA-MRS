package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.AkcijaPromocija;
import rest.domain.Apoteka;
import rest.domain.Pacijent;
import rest.dto.ApotekaDTO;
import rest.repository.AkcijaPromocijaRepository;
import rest.repository.ApotekeRepository;
import rest.repository.PacijentRepository;

@Service
@Transactional
public class AkcijaPromocijaServiceImpl implements AkcijaPromocijaService{

	private PacijentRepository pacijentRepo;
	private AkcijaPromocijaRepository akcijaPromocijaRepository;
	private ApotekeRepository apotekeRepository;
	
	@Autowired
	public AkcijaPromocijaServiceImpl(ApotekeRepository aptrks,PacijentRepository pacijentRepo, AkcijaPromocijaRepository apr) {
		this.pacijentRepo = pacijentRepo;
		this.apotekeRepository = aptrks;
		this.akcijaPromocijaRepository = apr;
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

	@Override
	public AkcijaPromocija create(AkcijaPromocija ap) throws Exception {
		AkcijaPromocija akcijaPromocija = akcijaPromocijaRepository.save(ap);

		return akcijaPromocija;
	}

	@Override
	public void removeForUser(int idUser, int idApo) {
		Pacijent p = null;
		Apoteka a = null;

		Optional<Pacijent> pOptional = pacijentRepo.findById(idUser);
		Optional<Apoteka> aOptional = apotekeRepository.findById(idApo);

		if (!pOptional.isPresent() || !aOptional.isPresent())
			return;

		p = pOptional.get();
		a = aOptional.get();

		if (p.getApoteke().contains(a)) {
			p.getApoteke().remove(a);
			apotekeRepository.save(a);
			pacijentRepo.save(p);
		}
	}
}
