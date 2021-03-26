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
	private static final int pageSize = 1;

	@Autowired
	public ApotekaServiceImpl(ApotekeRepository ar) {
		apoteke = ar;
	}
	
	@Override
	public Page<ApotekaDTO> getAllDrugStores(int stranica) {
		Page<ApotekaDTO> allStores = apoteke.findAll(new PageRequest(stranica, pageSize)).map(a -> new ApotekaDTO(a));
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
