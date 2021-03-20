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
import rest.domain.ZaposlenjeKorisnika;
import rest.service.KorisnikService;

/*
 * @RestController je anotacija nastala od @Controller tako da predstavlja bean komponentu.
 * 
 * @RequestMapping anotacija ukoliko se napise iznad kontrolera oznacava da sve rute ovog kontrolera imaju navedeni prefiks. 
 * U nasem primeru svaka rute kontrolera ima prefiks 'api/greetings'.
 */
@RestController
@RequestMapping("/api/users")
public class KorisnikController {

	private KorisnikService userService;
	
	@Autowired
	public KorisnikController(KorisnikService us) {
		this.userService = us;
	}
	
	/*
	 * Prilikom poziva metoda potrebno je navesti nekoliko parametara
	 * unutar @@GetMapping anotacije: url kao vrednost 'value' atributa (ukoliko se
	 * izostavi, ruta do metode je ruta do kontrolera), u slucaju GET zahteva
	 * atribut 'produce' sa naznakom tipa odgovora (u nasem slucaju JSON).
	 * 
	 * Kao povratna vrednost moze se vracati klasa ResponseEntity koja sadrzi i telo
	 * (sam podatak) i zaglavlje (metapodatke) i status kod, ili samo telo ako se
	 * metoda anotira sa @ResponseBody.
	 * 
	 * url: /api/users GET
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Korisnik>> getUsers() {
		Collection<Korisnik> users = userService.findAll();
		return new ResponseEntity<Collection<Korisnik>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public Korisnik currentUser(){
		// TODO: Ovo samo da se ne crveni na frontu
		Korisnik k = new Korisnik();
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.ADMIN_SISTEMA);
		return k;
	}
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(){
		// TODO: Ovo samo da se ne crveni na frontu
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	/*
	 * U viticastim zagradama se navodi promenljivi deo putanje.
	 * 
	 * url: /api/users/1 GET
	 */
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
	public ResponseEntity<Korisnik> updateUser(@RequestBody Korisnik user, @PathVariable int id)
			throws Exception {
		Korisnik userForUpdate = userService.findOne(id);

		Korisnik updatedUser = userService.update(userForUpdate);

		if (updatedUser == null) {
			return new ResponseEntity<Korisnik>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Korisnik>(updatedUser, HttpStatus.OK);
	}

	/*
	 * url: /api/users/1 DELETE
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return new ResponseEntity<String>("OK.", HttpStatus.OK);
	}

}
