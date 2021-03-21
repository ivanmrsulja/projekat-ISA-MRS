package rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Apoteka;
import rest.service.ApotekaService;

@RestController
@RequestMapping("/api/apoteke")
public class ApotekaController {
	
	private ApotekaService apotekaService;
	
	@Autowired
	public ApotekaController(ApotekaService as) {
		apotekaService = as;
	}
	
	@GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Apoteka> getAll() {
		return apotekaService.getAllDrugStores();
	}
}
