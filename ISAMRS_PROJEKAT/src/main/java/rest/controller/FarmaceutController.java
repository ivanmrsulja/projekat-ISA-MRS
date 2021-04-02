package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.domain.Ponuda;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.service.AdminService;
import rest.service.FarmaceutService;
import rest.service.KorisnikService;


@RestController
@RequestMapping("/api/farmaceut")
public class FarmaceutController {

	private FarmaceutService farmaceutService;
	
	@Autowired
	public FarmaceutController(FarmaceutService farmaceut) {
		this.farmaceutService = farmaceut;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsers() {
		Collection<Farmaceut> users = farmaceutService.findAll();
		ArrayList<KorisnikDTO> farmaceuti=new ArrayList<KorisnikDTO>();
		for(Farmaceut d : users)
			farmaceuti.add(new KorisnikDTO(d));
		return farmaceuti;
	}
	
	@GetMapping(value="/apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsersForPharmacy(@PathVariable("id") int id) {
		Collection<Farmaceut> users = farmaceutService.findAllForPharmacy(id);
		ArrayList<KorisnikDTO> farmaceuti = new ArrayList<KorisnikDTO>();
		for(Farmaceut f : users)
			farmaceuti.add(new KorisnikDTO(f));
		return farmaceuti;
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		System.out.println(user);
		Farmaceut updatedFarmaceut = farmaceutService.update(user);

		if (updatedFarmaceut == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(updatedFarmaceut), HttpStatus.OK);
	}
}