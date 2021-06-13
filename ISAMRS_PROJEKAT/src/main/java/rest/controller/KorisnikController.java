package rest.controller;

import java.net.URLDecoder;
import java.util.Base64;
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

import rest.domain.AdminApoteke;
import rest.domain.AdminSistema;
import rest.domain.Dermatolog;
import rest.domain.Dobavljac;
import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.aspect.AsAdminApoteke;
import rest.aspect.AsAdminSistema;
import rest.aspect.AsDobavljac;
import rest.aspect.AsPacijent;
import rest.domain.StatusNaloga;
import rest.domain.Zaposlenje;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.AdminApotekeDTO;
import rest.dto.ApotekaDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PacijentDTO;
import rest.dto.PenalDTO;
import rest.dto.PharmacyAdminDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.dto.QRCodeReaderDTO;
import rest.dto.RezervacijaDTO;
import rest.dto.ZalbaDTO;
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
	public ResponseEntity<Collection<KorisnikDTO>> getUsers() {
		@SuppressWarnings("unchecked")
		Collection<KorisnikDTO> users = (Collection<KorisnikDTO>) userService.findAll().stream().map(u -> new KorisnikDTO(u));
		return new ResponseEntity<Collection<KorisnikDTO>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/currentUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public KorisnikDTO currentUser(HttpServletRequest request){
		KorisnikDTO u = null;
		u = (KorisnikDTO) request.getSession().getAttribute("user");
		return u;
	}
	
	@AsPacijent
	@GetMapping(value= "/getZalbe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Collection<ZalbaDTO>> getZalbe(@PathVariable("id") int id) {
			Collection<ZalbaDTO> users = pacijentService.getZalbeForPatient(id);
			//pacijentService.getAllAppealable(id);
			return new ResponseEntity<Collection<ZalbaDTO>>(users, HttpStatus.OK);
		}
	
	@AsPacijent
	@GetMapping(value= "/getZalbe/{id}/{zalId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ZalbaDTO getZalba(@PathVariable("id") int id, @PathVariable("zalId") int zalId) {
		ZalbaDTO users = pacijentService.getZalbaForPatient(id, zalId);
		return users;
	}
	
	@GetMapping(value= "/getZaljivo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<String>> getZaljivo(@PathVariable("id") int id) {
		Collection<String> users = pacijentService.getAllAppealable(id);
		return new ResponseEntity<Collection<String>>(users, HttpStatus.OK);
	}

	
	@GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(HttpServletRequest request, @RequestParam String user, @RequestParam String pass){
		Collection<Korisnik> users = userService.findAll();
		for (Korisnik korisnik : users) {
			if(korisnik.getUsername().equals(user) && korisnik.getPassword().equals(pass)) {
				KorisnikDTO k = new KorisnikDTO(korisnik);
				if(k.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.PACIJENT) && k.isLoggedBefore() == false) {
					return new ResponseEntity<String>("Niste verifikovali nalog!", HttpStatus.OK);
				}
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
	
	@PostMapping(value = "/changePass", produces = MediaType.APPLICATION_JSON_VALUE)
	public String changePass(HttpServletRequest request, @RequestBody KorisnikDTO user) throws Exception {
		Korisnik k = userService.changePass(user);
		KorisnikDTO updateUser = new KorisnikDTO(k);
		request.getSession().setAttribute("user", updateUser);
		return "OK";
	}
	
	
	@AsDobavljac
	@PostMapping(value = "/updateSupp", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateSupp(HttpServletRequest request, @RequestBody KorisnikDTO user) throws Exception {
		KorisnikDTO u = null;
		u = (KorisnikDTO) request.getSession().getAttribute("user");
		Korisnik k = userService.updateSupp(u, user);
		if(k == null) {
			return "Not OK";
		}
		KorisnikDTO updateUser = new KorisnikDTO(k);
		request.getSession().setAttribute("user", updateUser);
		return "OK";
	}
	
//	public ResponseEntity<Collection<Korisnik>> getUsers() {
//		Collection<Korisnik> users = userService.findAll();
//		return new ResponseEntity<Collection<Korisnik>>(users, HttpStatus.OK);
//	}
	


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
		Korisnik t = userService.create(k);
		if(t == null) {
			return "Not OK";
		}
		userService.sendRegistrationMail(k);
		return "OK";
	}
	
	@AsPacijent
	@PostMapping(value = "/sendQr", produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendQr(@RequestBody String q) throws Exception {
		String s = URLDecoder.decode(q, "UTF-8").trim();
		s=s.replace(' ', '+');
		System.out.println("NA POCETKU DUZINA JE " + s.length());
		while(s.length() % 4 != 0) {
			System.out.println("MODUO JE SADA "  + s.length() % 4);
			s+="=";
			System.out.println("POVECALI SMO ZA JEDAN " + s.length());
		}
//		s = s.replace("==", "=");
//		q=q.replace("\\%2F", "\\/");
//		q=q.replace("\\%2B", "\\+");
		QRCodeReaderDTO qrc = new QRCodeReaderDTO();
		
		System.out.println("DUZINA JE " + s.length());
		s = s.replace("=","");
		System.out.println("KADA OBRISEMO = ONDA JE DUZINA" + s.length());
		while(s.length() % 4 != 0) {
			System.out.println("MODUO JE SADA "  + s.length() % 4);
			s+="=";
			System.out.println("POVECALI SMO ZA JEDAN " + s.length());
		}
		System.out.println(s);
		return qrc.readQRCode(s);
	}
	
	@AsAdminSistema
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
		Korisnik t = userService.create(k);
		if(t == null) {
			return "Not OK";
		}
		return "OK";
	}
	
	@AsAdminSistema
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
		k.setZaposlenja(p);
		Korisnik t = userService.create(k);
		if(t == null) {
			return "Not OK";
		}
		return "OK";
	}
	
	@AsAdminSistema
	@PostMapping(value = "/registerAdminSys", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerAdminSys(@RequestBody KorisnikDTO user) throws Exception {
		AdminSistema k = new AdminSistema();
		k.setIme(user.getIme());
		k.setPrezime(user.getPrezime());
		k.setUsername(user.getUsername());
		k.setEmail(user.getEmail());
		k.setTelefon(user.getTelefon());
		k.setLokacija(user.getLokacija());
		k.setPassword(user.getNoviPassw());
		k.setLoggedBefore(false);
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.ADMIN_SISTEMA);
		Korisnik t = userService.create(k);
		if(t == null) {
			return "Not OK";
		}
		return "OK";
	}
	
	@AsAdminSistema
	@PostMapping(value = "/registerAdminPharm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerAdminPharm(@RequestBody PharmacyAdminDTO user) throws Exception {
		
		AdminApoteke ap =  userService.createAdminPharm(user); //DHASJKHDSAJKHDAKHDJKDHHDJAKHKDHASKJD
		if(ap == null) {
			return "Not OK";
		}
		return "OK";
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

	@AsAdminApoteke
	@PutMapping(value = "updateAdmin/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateAdmin(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
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

	@GetMapping(value = "/admin_apoteke/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AdminApotekeDTO getAdminApoteke(@PathVariable("id") int id){
		return userService.findAdminApotekeById(id);
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
	@PutMapping(value = "/unsub/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String unsubActionsPromotions(@PathVariable("id") int id){
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		akcijaService.removeForUser(currentUser.getId(), id);
		return "OK";
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
