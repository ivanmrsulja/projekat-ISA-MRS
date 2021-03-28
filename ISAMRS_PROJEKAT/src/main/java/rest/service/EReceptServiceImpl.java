package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.ERecept;
import rest.dto.EReceptDTO;
import rest.repository.EReceptRepository;

@Service
public class EReceptServiceImpl implements EreceptService {

	private EReceptRepository eReceptRepo;
	
	@Autowired
	public EReceptServiceImpl(EReceptRepository er) {
		this.eReceptRepo = er;
	}
	
	@Override
	public Collection<EReceptDTO> getForUser(int id, EReceptSortFilterParams parameters) {
		Collection<ERecept> recepti = eReceptRepo.getForUser(id);
		ArrayList<EReceptDTO> ret = new ArrayList<EReceptDTO>();
		
		for(ERecept er : recepti) {
			if(parameters.getStatus() != null) {
				if(er.getStatus().equals(parameters.getStatus())) {
					ret.add(new EReceptDTO(er));
				}
			}else {
				ret.add(new EReceptDTO(er));
			}
		}
		if(parameters.isSort()) {
			Collections.sort(ret, new Comparator<EReceptDTO>() {
				
				@Override
				public int compare(EReceptDTO e1, EReceptDTO e2) {
					return e1.getDatumIzdavanja().compareTo(e2.getDatumIzdavanja());
				}
			});
			if(!parameters.isDescending()) {
				Collections.reverse(ret);
			}
		}
		return ret;
	}

}
