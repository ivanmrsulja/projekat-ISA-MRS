package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.aspect.Pacijent;
import rest.domain.Korisnik;
import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.service.ApotekaService;
import rest.service.KorisnikService;
import rest.service.PregledService;
import rest.util.ApotekaSearchParams;

@RestController
@RequestMapping("/api/apoteke")
public class ApotekaController {
	
	private ApotekaService apotekaService;
	private PregledService pregledService;
	private KorisnikService userService;
	
	@Autowired
	public ApotekaController(ApotekaService as, PregledService ps, KorisnikService us) {
		apotekaService = as;
		pregledService = ps;
		userService = us;
	}
	
	@GetMapping(value="/all/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ApotekaDTO> getAll(HttpSession sess, @PathVariable("page") int page, @RequestParam String naziv, @RequestParam String adresa, @RequestParam double dOcena, @RequestParam double gOcena, @RequestParam double rastojanje, @RequestParam String kriterijum, @RequestParam boolean smer) {
		ApotekaSearchParams params = new ApotekaSearchParams(naziv, adresa, dOcena, gOcena, rastojanje, kriterijum, smer);
		KorisnikDTO user = (KorisnikDTO) sess.getAttribute("user");
		Page<ApotekaDTO> apoteke = null;
		if(user == null) {
			params.setRastojanje(50000);
			apoteke = apotekaService.getAllDrugStores(page, params, 0.0, 0.0);
		}else {
			Korisnik k = userService.findOne(user.getId());
			apoteke = apotekaService.getAllDrugStores(page, params, k.getLokacija().getSirina(), k.getLokacija().getDuzina());
		}
		ArrayList<ApotekaDTO> retVals = new ArrayList<ApotekaDTO>();
		for(ApotekaDTO a : apoteke) {
			retVals.add(a);
		}
		return apoteke;
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApotekaDTO getOne(@PathVariable int id) {
		return new ApotekaDTO(this.apotekaService.getByID(id));
	}
	
	@GetMapping(value="admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApotekaDTO getOneForAdmin(@PathVariable int id) {
		return new ApotekaDTO(this.apotekaService.getForAdmin(id));
	}
	
	@GetMapping(value="pregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PregledDTO> getPreCreatedExaminations(@PathVariable int id, @RequestParam String criteria) {
		return apotekaService.getPregledi(id, criteria);
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApotekaDTO> updateApoteka(@RequestBody ApotekaDTO apoteka)
			throws Exception {
		
		if (apoteka == null) {
			return new ResponseEntity<ApotekaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apotekaService.update(apoteka);
		return new ResponseEntity<ApotekaDTO>(apoteka, HttpStatus.OK);
	}
	
	@PutMapping(value="zakaziPregled/{idp}/{idpa}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PregledDTO> scheduleExamination(@PathVariable int idp, @PathVariable int idpa) {
		try {
			return pregledService.zakaziPregled(idp, idpa);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Pacijent
	@PatchMapping(value="otkazi/{idp}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String unscheduleExamination(@PathVariable int idp) {
		try {
			pregledService.otkaziPregled(idp);
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}