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

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.domain.Ponuda;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.service.AdminService;
import rest.service.DermatologService;
import rest.service.KorisnikService;


@RestController
@RequestMapping("/api/dermatolog")
public class DermatologController {

	private DermatologService dermatologService;
	
	@Autowired
	public DermatologController(DermatologService dermatolog) {
		this.dermatologService = dermatolog;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsers() {
		Collection<Dermatolog> users = dermatologService.findAll();
		ArrayList<KorisnikDTO> dermatolozi=new ArrayList<KorisnikDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new KorisnikDTO(d));
		return dermatolozi;
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		System.out.println(user);
		Dermatolog updatedDermatolog = dermatologService.update(user);

		if (updatedDermatolog == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(updatedDermatolog), HttpStatus.OK);
	}
}
