package rest.controller;

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

import rest.domain.Korisnik;
import rest.domain.Penal;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.PenalDTO;
import rest.service.KorisnikService;

@RestController
@RequestMapping("/api/users")
public class KorisnikController {

	private KorisnikService userService;
	
	@Autowired
	public KorisnikController(KorisnikService us) {
		this.userService = us;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Korisnik>> getUsers() {
		Collection<Korisnik> users = userService.findAll();
		return new ResponseEntity<Collection<Korisnik>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public Korisnik currentUser(){
		// TODO: Ovo samo da se ne crveni na frontu
		Korisnik k = new Korisnik();
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.ADMIN_APOTEKE);
		return k;
	}
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(){
		// TODO: Ovo samo da se ne crveni na frontu
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "OK";
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> getUSer(@PathVariable("id") int id) {
		Korisnik user = userService.findOne(id);

		if (user == null) {
			return new ResponseEntity<Korisnik>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Korisnik>(user, HttpStatus.OK);
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> createUser(@RequestBody Korisnik user) throws Exception {
		Korisnik savedUser = userService.create(user);
		return new ResponseEntity<Korisnik>(savedUser, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> updateUser(@RequestBody Korisnik user, @PathVariable("id") int id)
			throws Exception {
		Korisnik userForUpdate = userService.findOne(id);

		Korisnik updatedUser = userService.update(userForUpdate);

		if (updatedUser == null) {
			return new ResponseEntity<Korisnik>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Korisnik>(updatedUser, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return new ResponseEntity<String>("OK.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/penali/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getPenali(@PathVariable("id") int id){
		return userService.getPenali(id).stream().map(p -> new PenalDTO(p)).toArray();
	}
	
}
