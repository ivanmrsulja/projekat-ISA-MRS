package rest.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.service.ApotekaService;
import rest.util.ApotekaSearchParams;

@RestController
@RequestMapping("/api/apoteke")
public class ApotekaController {
	
	private ApotekaService apotekaService;
	
	@Autowired
	public ApotekaController(ApotekaService as) {
		apotekaService = as;
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
			apoteke = apotekaService.getAllDrugStores(page, params, user.getLokacija().getSirina(), user.getLokacija().getDuzina());
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

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApotekaDTO> updateApoteka(@RequestBody ApotekaDTO apoteka)
			throws Exception {
		
		if (apoteka == null) {
			return new ResponseEntity<ApotekaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		apotekaService.update(apoteka);
		return new ResponseEntity<ApotekaDTO>(apoteka, HttpStatus.OK);
	}
}