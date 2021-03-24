package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<Apoteka> getAllDrugStores(int stranica) {
		Page<Apoteka> allStores = apoteke.findAll(new PageRequest(stranica, 1));
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

	@Override
	public int getNumOf() {
		return apoteke.getNumOf();
	}

}
