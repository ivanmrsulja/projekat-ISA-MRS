package rest.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.domain.Dermatolog;
import rest.domain.Dobavljac;
import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.aspect.AsPacijent;
import rest.domain.StatusNaloga;
import rest.domain.Zaposlenje;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PacijentDTO;
import rest.dto.PenalDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.dto.RezervacijaDTO;
import rest.service.AkcijaPromocijaService;
import rest.service.KorisnikService;
import rest.service.PacijentService;

@RestController
@RequestMapping("/api/users")
public class KorisnikController {

	private KorisnikService userService;
	private AkcijaPromocijaService akcijaService;
	private PacijentService pacijentService;
	
	@Autowired
	public KorisnikController(KorisnikService us, AkcijaPromocijaService aps, PacijentService pacijentService) {
		this.userService = us;
		this.akcijaService = aps;
		this.pacijentService = pacijentService;
	}
	
	@Scheduled(cron = "${penali.cron}")
	public void cronJob() {
		pacijentService.removeAllPenalities();
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
	
	@PostMapping(value = "/changePass", produces = MediaType.APPLICATION_JSON_VALUE)
	public String changePass(HttpServletRequest request, @RequestBody KorisnikDTO user) throws Exception {
		Korisnik k = userService.changePass(user);
		KorisnikDTO updateUser = new KorisnikDTO(k);
		request.getSession().setAttribute("user", updateUser);
		return "OK";
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
		userService.sendRegistrationMail(k);
		return "OK";
	}
	
	@PostMapping(value = "/registerSupp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerSupp(@RequestBody KorisnikDTO user) throws Exception {
		Dobavljac k = new Dobavljac();
		k.setIme(user.getIme());
		k.setPrezime(user.getPrezime());
		k.setUsername(user.getUsername());
		k.setEmail(user.getEmail());
		k.setTelefon(user.getTelefon());
		k.setLokacija(user.getLokacija());
		k.setPassword(user.getNoviPassw());
		k.setLoggedBefore(false);
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.DOBAVLJAC);
		Set<Ponuda> p = new HashSet<Ponuda>();
		k.setPonude(p);
		userService.create(k);
		return "OK";
	}
	
	@PostMapping(value = "/registerDerm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerDerm(@RequestBody KorisnikDTO user) throws Exception {
		Dermatolog k = new Dermatolog();
		k.setIme(user.getIme());
		k.setPrezime(user.getPrezime());
		k.setUsername(user.getUsername());
		k.setEmail(user.getEmail());
		k.setTelefon(user.getTelefon());
		k.setLokacija(user.getLokacija());
		k.setPassword(user.getNoviPassw());
		k.setLoggedBefore(false);
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.DERMATOLOG);
		Set<Zaposlenje> p = new HashSet<Zaposlenje>();
		k.setZaposlenja(p);;
		userService.create(k);
		return "OK";
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> createUser(@RequestBody Korisnik user) throws Exception {
		Korisnik savedUser = userService.create(user);
		return new ResponseEntity<Korisnik>(savedUser, HttpStatus.CREATED);
	}

	@AsPacijent
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		
		if(user.getUsername().trim().equals("") || user.getPrezime().trim().equals("") || user.getIme().trim().equals("") || user.getLokacija() == null || user.getTelefon().trim().equals("")) {
			return "Unesite sve podatke.";
		}
		
		Korisnik updatedUser = null;
		try {
			updatedUser = userService.update(user);
		}catch(Exception e){
			return e.getMessage();
		}

		if (updatedUser == null) {
			return "Neuspelo azuriranje, pokusajte ponovo.";
		}
		
		return "Azuriranje uspesno";
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return new ResponseEntity<String>("OK.", HttpStatus.OK);
	}
	
	@AsPacijent
	@GetMapping(value = "/penali/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object[] getPenali(@PathVariable("id") int id){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return userService.getPenali(id).stream().map(p -> new PenalDTO(p)).toArray();
	}
	
	@AsPacijent
	@GetMapping(value = "/istorijaPregleda/{id}/{page}/{criteria}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PregledDTO> getIstorijaPregleda(@PathVariable("id") int id, @PathVariable("page") int pageNum, @PathVariable("criteria") String criteria){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return userService.preglediZaKorisnika(id, pageNum, criteria);
	}
	
	@AsPacijent
	@GetMapping(value = "/pregledi/{id}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<PregledDTO> getIstorijaPregleda(@PathVariable("id") int id, @PathVariable("page") int pageNum){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return userService.zakazivanjaZaKorisnika(id, pageNum);
	}
	
	@AsPacijent
	@GetMapping(value = "/rezervacije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<RezervacijaDTO> getIstorijaRezervacijaa(@PathVariable("id") int id){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return userService.rezervacijeZaKorisnika(id);
	}
	
	@GetMapping(value = "/pacijent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PacijentDTO getPacijent(@PathVariable("id") int id){
		return userService.findPacijentById(id);
	}
	
	@AsPacijent
	@GetMapping(value = "/akcije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<ApotekaDTO> getActionsPromotions(@PathVariable("id") int id){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return akcijaService.getForUser(id);
	}
	
	@AsPacijent
	@GetMapping(value = "/alergije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PreparatDTO> getAllergies(@PathVariable("id") int id){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return pacijentService.allergies(id);
	}
	
	@AsPacijent
	@GetMapping(value = "/dodajAlergije/{id}/{idprep}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PreparatDTO> addAllergies(@PathVariable("id") int id, @PathVariable("idprep") int idPrep){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return pacijentService.addAllergy(id, idPrep);
	}
	
	@AsPacijent
	@DeleteMapping(value = "/alergije/{id}/{idprep}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PreparatDTO> removeAllergies(@PathVariable("id") int id, @PathVariable("idprep") int idPrep){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != id) {
			return null;
		}
		return pacijentService.removeAllergy(id, idPrep);
	}
}
