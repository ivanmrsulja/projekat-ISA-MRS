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
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;

@Service
public class PacijentServiceImpl implements PacijentService {
	
	private PacijentRepository pacijentRepository;
	private PreparatRepository preparatRepository;
	private PregledRepository pregledRepository;
	
	@Autowired
	public PacijentServiceImpl(PacijentRepository pacijentRepository, PreparatRepository preparatRepository,PregledRepository pregledRepository) {
		this.pacijentRepository = pacijentRepository;
		this.preparatRepository = preparatRepository;
		this.pregledRepository=pregledRepository;
		
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
	
	@Override
	@Transactional
	public Collection<Pacijent> getAll(){
		Collection<Pacijent> users = pacijentRepository.findAll();
		return users;
	}
	
	@Override
	public Pacijent getOne(int id) {
		Pacijent prep = pacijentRepository.findById(id).get();
		return prep;
	}
	
	@Override
	public Collection<Pacijent> getMine(int id){
		return pregledRepository.getMine(id);
	}
}
