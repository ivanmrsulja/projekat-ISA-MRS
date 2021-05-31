package rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Apoteka;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.dto.PacijentDTO;
import rest.repository.ApotekeRepository;
import rest.repository.PacijentRepository;
import rest.service.KorisnikService;
import rest.service.PacijentService;

@RestController
@RequestMapping("/api/pacijenti")
public class PacijentController {
	
	private KorisnikService korisnikService;
	private PacijentService pacijentService;
	private PacijentRepository pacijentRepository;
	private ApotekeRepository apotekeRepository;
	
	@Autowired
	public PacijentController(KorisnikService ks, PacijentService er, PacijentRepository pr, ApotekeRepository ar) {
		this.korisnikService = ks;
		this.pacijentService = er;
		this.pacijentRepository = pr;
		this.apotekeRepository = ar;
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
	
	@PutMapping(value="updateApoteke/{idPacijent}/{idApoteka}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updatePacijentApoteke(@PathVariable("idPacijent") int idPacijenta, @PathVariable("idApoteka") int idApoteke) {
		Pacijent pacijent = pacijentRepository.getPatientWithPharmacies(idPacijenta);
		Optional<Apoteka> apotekaOption = apotekeRepository.findById(idApoteke);
		Apoteka apoteka;
		if(apotekaOption.isPresent()) {
			apoteka = apotekaOption.get();
		}
		else {
			return "ERROR";
		}
		Set<Apoteka> apoteke = pacijent.getApoteke();
		apoteke.add(apoteka);
		pacijent.setApoteke(apoteke);
		pacijentRepository.save(pacijent);
		return "OK";
	}
	
	@PutMapping(value = "penal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void addPenal(@PathVariable("id") int id,@RequestBody Penal p){
		pacijentService.addPenal(id, p);
	}
}