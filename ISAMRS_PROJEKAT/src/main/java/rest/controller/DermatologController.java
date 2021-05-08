package rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Dermatolog;
import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.domain.Ponuda;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.DermatologDTO;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.repository.DermatologRepository;
import rest.service.AdminService;
import rest.service.DermatologService;
import rest.service.KorisnikService;


@RestController
@RequestMapping("/api/dermatolog")
public class DermatologController {

	private DermatologService dermatologService;
	private DermatologRepository dermatologRepository;
	
	@Autowired
	public DermatologController(DermatologService dermatolog, DermatologRepository dr) {
		this.dermatologService = dermatolog;
		this.dermatologRepository = dr;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsers() {
		Collection<Dermatolog> users = dermatologService.findAll();
		ArrayList<KorisnikDTO> dermatolozi=new ArrayList<KorisnikDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new KorisnikDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> getAllDermatologists() {
		Collection<Dermatolog> users = dermatologRepository.getAllDermatologists();
		ArrayList<DermatologDTO> dermatolozi=new ArrayList<DermatologDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new DermatologDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/searchUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> userSearchPharmacists(@RequestParam String ime, @RequestParam String prezime, @RequestParam int startOcena, @RequestParam int endOcena, @RequestParam String kriterijumSortiranja, @RequestParam boolean opadajuce){
		Collection<Dermatolog> users = dermatologRepository.getAllDermatologists();
		ArrayList<DermatologDTO> farmaceuti = new ArrayList<DermatologDTO>();
		for (Dermatolog f : users) {
			if (f.getIme().contains(ime) && f.getPrezime().contains(prezime) && (f.getOcena() <= endOcena && f.getOcena() >= startOcena)) {
				farmaceuti.add(new DermatologDTO(f, kriterijumSortiranja));
			}
		}
		Collections.sort(farmaceuti);
		if (opadajuce)
			Collections.reverse(farmaceuti);

		return farmaceuti;
	}
	
	@GetMapping(value="/apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsersForPharmacy(@PathVariable("id") int id) {
		Collection<Dermatolog> users = dermatologService.findAllForPharmacy(id);
		ArrayList<KorisnikDTO> dermatolozi = new ArrayList<KorisnikDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new KorisnikDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/apoteka/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> getDermatologistsForPharmacy(@PathVariable("id") int id) {
		Collection<Dermatolog> users = dermatologService.findAllForPharmacy(id);
		ArrayList<DermatologDTO> dermatolozi = new ArrayList<DermatologDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new DermatologDTO(d));
		return dermatolozi;
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		//System.out.println(user);
		Dermatolog updatedDermatolog = dermatologService.update(user);

		if (updatedDermatolog == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(updatedDermatolog), HttpStatus.OK);
	}
}