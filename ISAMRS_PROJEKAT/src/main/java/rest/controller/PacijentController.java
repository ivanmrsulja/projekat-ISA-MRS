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

import rest.domain.Pacijent;
import rest.dto.PacijentDTO;
import rest.service.KorisnikService;
import rest.service.PacijentService;

@RestController
@RequestMapping("/api/pacijenti")
public class PacijentController {
	
	private KorisnikService korisnikService;
	private PacijentService pacijentService;
	
	@Autowired
	public PacijentController(KorisnikService ks, PacijentService er) {
		this.korisnikService = ks;
		this.pacijentService = er;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PacijentDTO> getPacijent() {
		Collection<Pacijent> lekovi = pacijentService.getAll();
		ArrayList<PacijentDTO> pacijenti=new ArrayList<PacijentDTO>();
		for(Pacijent p : lekovi)
			pacijenti.add(new PacijentDTO(p));
		return pacijenti;
	}
	
	@GetMapping(value = "spec/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PacijentDTO getSpec(@PathVariable("id") int id){
		return new PacijentDTO(pacijentService.getOne(id));
	}
	
	@GetMapping(value = "pregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PacijentDTO> getMine(@PathVariable("id") int id, @RequestParam("search") String searchParam, @RequestParam("criteria") String criteria){
		Collection<Pacijent> pacijenti = pacijentService.getMine(id, searchParam, criteria);
		ArrayList<PacijentDTO> pacijentiDTO = new ArrayList<PacijentDTO>();
		for(Pacijent p: pacijenti)
			pacijentiDTO.add(new PacijentDTO(p));
		
		ArrayList<PacijentDTO> ret = new ArrayList<PacijentDTO>();
		
		for (PacijentDTO pa : pacijentiDTO) {
			if(!ret.contains(pa)) {
				ret.add(pa);
			}
		}
		
		return ret;
	}
}