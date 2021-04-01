package rest.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.StatusNaloga;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.KorisnikDTO;
import rest.dto.PacijentDTO;
import rest.dto.PenalDTO;
import rest.dto.PregledDTO;
import rest.dto.RezervacijaDTO;
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
	public KorisnikDTO currentUser(HttpServletRequest request){
		KorisnikDTO u = null;
		u = (KorisnikDTO) request.getSession().getAttribute("user");
		return u;
	}
	
	@GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestParam String user, @RequestParam String pass){
		Collection<Korisnik> users = userService.findAll();
		for (Korisnik korisnik : users) {
			if(korisnik.getUsername().equals(user) && korisnik.getPassword().equals(pass)) {
				KorisnikDTO k = new KorisnikDTO(korisnik);
				request.getSession().setAttribute("user", k);
				return new ResponseEntity<String>("OK", HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>("neispravan username/password", HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		KorisnikDTO u = null;
		u = (KorisnikDTO) request.getSession().getAttribute("user");
		if(u != null) {
			request.getSession().invalidate();
		}
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

	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody KorisnikDTO user) throws Exception {
		Pacijent k = new Pacijent();
		k.setIme(user.getIme());
		k.setPrezime(user.getPrezime());
		k.setUsername(user.getUsername());
		k.setEmail(user.getEmail());
		k.setTelefon(user.getTelefon());
		k.setLokacija(user.getLokacija());
		k.setZaposlenjeKorisnika(user.getZaposlenjeKorisnika());
		k.setPassword(user.getNoviPassw());
		k.setLoggedBefore(false);
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.PACIJENT);
		k.setBrojPoena(0);
		k.setTipKorisnika(userService.pocetniTip());
		k.setStatusNaloga(StatusNaloga.AKTIVAN); //ovo se menja
		userService.create(k);
		return "OK";
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
	
	@GetMapping(value = "/istorijaPregleda/{id}/{page}/{criteria}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PregledDTO> getIstorijaPregleda(@PathVariable("id") int id, @PathVariable("page") int pageNum, @PathVariable("criteria") String criteria){
		return userService.preglediZaKorisnika(id, pageNum, criteria);
	}
	
	@GetMapping(value = "/pregledi/{id}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PregledDTO> getIstorijaPregleda(@PathVariable("id") int id, @PathVariable("page") int pageNum){
		return userService.zakazivanjaZaKorisnika(id, pageNum);
	}
	
	@GetMapping(value = "/rezervacije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<RezervacijaDTO> getIstorijaPregleda(@PathVariable("id") int id){
		return userService.rezervacijeZaKorisnika(id);
	}
	
	@GetMapping(value = "/pacijent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PacijentDTO getPacijent(@PathVariable("id") int id){
		return userService.findPacijentById(id);
	}
	
}
