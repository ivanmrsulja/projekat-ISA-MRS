package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.dto.PreparatDTO;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;

@Service
@Transactional
public class PacijentServiceImpl implements PacijentService {
	
	private PacijentRepository pacijentRepository;
	private PreparatRepository preparatRepository;
	private PregledRepository pregledRepository;
	private PenalRepository penaliRepository;
	
	@Autowired
	public PacijentServiceImpl(PacijentRepository pacijentRepository, PreparatRepository preparatRepository,PregledRepository pregledRepository, PenalRepository pr) {
		this.pacijentRepository = pacijentRepository;
		this.preparatRepository = preparatRepository;
		this.pregledRepository=pregledRepository;
		this.penaliRepository = pr;
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
	public Collection<PreparatDTO> addAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().add(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}


	@Override
	public Collection<PreparatDTO> removeAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().remove(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}
	
	@Override
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
	public Collection<Pacijent> getMine(int id, String param, String criteria){
		Collection<Pregled> pregledi = pregledRepository.getMine(id);
		ArrayList<Pacijent> pacijentiRet = new ArrayList<Pacijent>();
		ArrayList<Pregled> preglediRet = new ArrayList<Pregled>();
		
		for(Pregled p : pregledi) {
			if(p.getPacijent() != null) {
				String imePrezime = p.getPacijent().getIme() + " " + p.getPacijent().getPrezime();
				if(imePrezime.toLowerCase().contains(param.toLowerCase())) {
					preglediRet.add(p);
				}
			}
		}
		
		switch(criteria) {
			case "IME":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getPacijent().getIme().compareTo(arg1.getPacijent().getIme());
					}
				});
				break;
			case "PREZIME":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getPacijent().getPrezime().compareTo(arg1.getPacijent().getPrezime());
					}
				});
				break;
			case "DATUM":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getDatum().compareTo(arg1.getDatum());
					}
				});
				break;
		}
		
		for(Pregled p : preglediRet) {
			pacijentiRet.add(p.getPacijent());
		}
		
		return pacijentiRet;
	}

	@Override
	public void removeAllPenalities() {
		Collection<Pacijent> patients = pacijentRepository.getWithPenalities();
		Collection<Penal> penali = penaliRepository.findAll();
		for(Pacijent pa : patients) {
			pa.getPenali().clear();
			pacijentRepository.save(pa);
		}
		for(Penal p : penali) {
			penaliRepository.delete(p);
		}
	}
	
	
	@Override
	public void addPenal(int id, Penal p) {
		Penal pp= new Penal(p.getDatum(),p.getPacijent());
		Pacijent pacijent = pacijentRepository.findById(id).get();
		pacijent.addPenal(pp);
		penaliRepository.save(pp);
		pacijentRepository.save(pacijent);
	}
	
}
