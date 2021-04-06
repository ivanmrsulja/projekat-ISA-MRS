package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Preparat;
import rest.domain.StatusERecepta;
import rest.dto.EReceptDTO;
import rest.dto.PreparatDTO;
import rest.dto.StavkaReceptaDTO;
import rest.service.EReceptSortFilterParams;
import rest.service.KorisnikService;
import rest.service.PreparatService;

@RestController
@RequestMapping("/api/preparat")
public class PreparatController {
	
	private KorisnikService korisnikService;
	private PreparatService preparatService;
	
	@Autowired
	public PreparatController(KorisnikService ks, PreparatService er) {
		this.korisnikService = ks;
		this.preparatService = er;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getPreparat() {
		Collection<Preparat> lekovi = preparatService.getAll();
		ArrayList<PreparatDTO> preparati=new ArrayList<PreparatDTO>();
		for(Preparat p : lekovi)
			preparati.add(new PreparatDTO(p));
		return preparati;
	}
	
	
	@GetMapping(value = "spec/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PreparatDTO getSpec(@PathVariable("id") int id){
		return new PreparatDTO(preparatService.getOne(id));
	}

	@GetMapping(value = "apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getPreparatiForApoteka(@PathVariable("id") int id)
	{
		Collection<Preparat> preparati = preparatService.getAllForPharmacy(id);
		ArrayList<PreparatDTO> ret = new ArrayList<PreparatDTO>();
		for(Preparat p : preparati) {
			ret.add(new PreparatDTO(p));
		}
		return ret;
	}

}
