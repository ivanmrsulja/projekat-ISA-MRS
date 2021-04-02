package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import rest.domain.AdminApoteke;
import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;

@Service
public class ApotekaServiceImpl implements ApotekaService {

	private ApotekeRepository apoteke;
	private AdminApotekeRepository admin;
	private static final int pageSize = 10;

	@Autowired
	public ApotekaServiceImpl(ApotekeRepository ar, AdminApotekeRepository are) {
		apoteke = ar;
		admin = are;
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

	@Override
	public Apoteka getForAdmin(int id) {
		return admin.getApoteka(id);
	}

}