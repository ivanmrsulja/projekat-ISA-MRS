package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.ERecept;
import rest.domain.StavkaRecepta;
import rest.dto.EReceptDTO;
import rest.dto.StavkaReceptaDTO;
import rest.repository.EReceptRepository;
import rest.repository.StavkaReceptaRepository;

@Service
@Transactional
public class EReceptServiceImpl implements EreceptService {

	private EReceptRepository eReceptRepo;
	private StavkaReceptaRepository stavkaRepository;
	
	@Autowired
	public EReceptServiceImpl(EReceptRepository er, StavkaReceptaRepository sr) {
		this.eReceptRepo = er;
		this.stavkaRepository = sr;
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

	@Override
	public Collection<StavkaReceptaDTO> getStavkeRecepta(int id) {
		Collection<StavkaRecepta> stavke = stavkaRepository.getForRecipeId(id);
		ArrayList<StavkaReceptaDTO> ret = new ArrayList<StavkaReceptaDTO>();
		for(StavkaRecepta sr : stavke) {
			ret.add(new StavkaReceptaDTO(sr));
		}
		return ret;
	}

}
