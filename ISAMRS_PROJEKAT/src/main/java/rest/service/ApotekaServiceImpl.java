package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.Pregled;
import rest.dto.ApotekaDTO;
import rest.dto.EReceptDTO;
import rest.dto.PregledDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.PregledRepository;
import rest.util.ApotekaSearchParams;

@Service
public class ApotekaServiceImpl implements ApotekaService {

	private ApotekeRepository apoteke;
	private AdminApotekeRepository admin;
	private DermatologRepository dermatolozi;
	
	private static final int pageSize = 10;

	@Autowired
	public ApotekaServiceImpl(ApotekeRepository ar, AdminApotekeRepository are, DermatologRepository dr) {
		apoteke = ar;
		admin = are;
		dermatolozi = dr;
	}
	
	@Override
	public Page<ApotekaDTO> getAllDrugStores(int stranica, ApotekaSearchParams params, double lat, double lon) {
		Direction dir;
		if(params.isOpadajuce()) {
			dir = Direction.DESC;
		}else {
			dir = Direction.ASC;
		}
		Page<ApotekaDTO> allStores = apoteke.findAll(new PageRequest(stranica, pageSize, dir, params.getKriterijumSortiranja().toLowerCase()), params.getNaziv().toUpperCase(), params.getAdresa().toUpperCase(), params.getdOcena(), params.getgOcena(), lat, lon, params.getRastojanje()).map(a -> new ApotekaDTO(a));
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

	@Override
	public Collection<PregledDTO> getPregledi(int id, String criteria) {
		Collection<Pregled> pregledi = apoteke.getPreCreated(id);
		
		ArrayList<PregledDTO> ret = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
			Dermatolog d = dermatolozi.findById(p.getZaposleni().getId()).get();
			ret.add(new PregledDTO(p, d.getOcena()));
		}
		
		if(criteria.equals("cena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
							
							@Override
							public int compare(PregledDTO e1, PregledDTO e2) {
								return Double.compare(e1.getCijena(), e2.getCijena());
							}
						});
		}else if(criteria.equals("ocena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
				
				@Override
				public int compare(PregledDTO e1, PregledDTO e2) {
					return Double.compare(e2.getOcena(), e1.getOcena());
				}
			});
		}
		
		return ret;
	}

}