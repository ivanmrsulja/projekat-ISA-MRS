package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Preparat;
import rest.repository.PreparatRepository;

@Service
public class PreparatServiceImpl implements PreparatService{

private PreparatRepository preparatRepository ;
	
	@Autowired
	public PreparatServiceImpl(PreparatRepository pr) {
		this.preparatRepository = pr;
	}
	
	@Override
	public Collection<Preparat> getAll() {
		Collection<Preparat> users = preparatRepository.findAll();
		return users;
	}
	
	@Override
	public Preparat getOne(int id) {
		Preparat prep = preparatRepository.findById(id).get();
		return prep;
	}
}
