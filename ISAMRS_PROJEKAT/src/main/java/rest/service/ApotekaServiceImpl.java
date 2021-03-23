package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;
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
		Collection<Apoteka> allStores = apoteke.findAll();
		return allStores;
	}

	@Override
	public Apoteka getByID(int id) {
		return this.apoteke.findById(id).get();
	}

	@Override
	public void update(ApotekaDTO apoteka) throws Exception {
		Apoteka apotekaToUpdate = getByID(apoteka.getId());
		if (apotekaToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		apotekaToUpdate.setNaziv(apoteka.getNaziv());
		apotekaToUpdate.setOpis(apoteka.getOpis());
		apotekaToUpdate.setLokacija(apoteka.getLokacija());
		apoteke.save(apotekaToUpdate);
	}

}
