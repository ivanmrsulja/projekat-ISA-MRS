package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Apoteka;
import rest.dto.ApotekaDTO;
import rest.service.ApotekaService;

@RestController
@RequestMapping("/api/apoteke")
public class ApotekaController {
	
	private ApotekaService apotekaService;
	
	@Autowired
	public ApotekaController(ApotekaService as) {
		apotekaService = as;
	}
	
	@GetMapping(value="/all/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ApotekaDTO> getAll(@PathVariable("page") int page) {
		Page<ApotekaDTO> apoteke = apotekaService.getAllDrugStores(page);
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