package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.DostupanProizvod;
import rest.domain.Preparat;
import rest.repository.PreparatRepository;
import rest.repository.ApotekeRepository;
import rest.service.ApotekaService;

@Service
public class PreparatServiceImpl implements PreparatService{

private PreparatRepository preparatRepository;
private ApotekeRepository apotekeRepository;
private ApotekaService apotekaService;
	
	@Autowired
	public PreparatServiceImpl(PreparatRepository pr) {
		this.preparatRepository = pr;
	}
	
	@Override
	public Collection<Preparat> getAll() {
		Collection<Preparat> preparati = preparatRepository.findAll();
		return preparati;
	}
	
	@Override
	public Preparat getOne(int id) {
		Preparat prep = preparatRepository.findById(id).get();
		return prep;
	}

	@Override
	public Collection<Preparat> getAllForPharmacy(int id) {
		return null;
	}
}
