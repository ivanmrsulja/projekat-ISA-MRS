package rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.StatusERecepta;
import rest.dto.EReceptDTO;
import rest.service.EReceptSortFilterParams;
import rest.service.EreceptService;
import rest.service.KorisnikService;

@RestController
@RequestMapping("/api/eRecept")
public class EReceptController {
	
	private KorisnikService korisnikService;
	private EreceptService eReceptService;
	
	@Autowired
	public EReceptController(KorisnikService ks, EreceptService er) {
		korisnikService = ks;
		eReceptService = er;
	}
	
	@GetMapping(value = "all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<EReceptDTO> getPacijent(@PathVariable("id") int id, @RequestParam("sort") boolean s, @RequestParam("descending") boolean d, @RequestParam("status") String st){
		if(st.equals("SVI")) {
			return eReceptService.getForUser(id, new EReceptSortFilterParams(s, d, null));
		}
		return eReceptService.getForUser(id, new EReceptSortFilterParams(s, d, StatusERecepta.valueOf(st)));
	}
}