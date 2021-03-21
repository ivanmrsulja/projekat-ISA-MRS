package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.repository.ApotekeRepository;

@Service
public class ApotekaServiceImpl implements ApotekaService {

	private ApotekeRepository apoteke;
	
	@Autowired
	public ApotekaServiceImpl(ApotekeRepository ar) {
		apoteke = ar;
	}
	
	@Override
	public Collection<Apoteka> getAllDrugStores() {
		Collection<Apoteka> allStores = apoteke.findAllDrugStores();
		return allStores;
	}

}
