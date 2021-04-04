package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Pacijent;
import rest.domain.Preparat;
import rest.dto.PreparatDTO;
import rest.repository.PacijentRepository;
import rest.repository.PreparatRepository;

@Service
public class PacijentServiceImpl implements PacijentService {
	
	private PacijentRepository pacijentRepository;
	private PreparatRepository preparatRepository;
	
	@Autowired
	public PacijentServiceImpl(PacijentRepository pacijentRepository, PreparatRepository preparatRepository) {
		this.pacijentRepository = pacijentRepository;
		this.preparatRepository = preparatRepository;
	}


	@Override
	public Collection<PreparatDTO> allergies(int id) {
		Collection<Preparat> alergije = pacijentRepository.getAllergiesForUser(id);
		ArrayList<PreparatDTO> ret = new ArrayList<PreparatDTO>();
		for(Preparat p : alergije) {
			ret.add(new PreparatDTO(p));
		}
		return ret;
	}

	@Override
	@Transactional
	public Collection<PreparatDTO> addAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().add(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}


	@Override
	@Transactional
	public Collection<PreparatDTO> removeAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().remove(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}

}
