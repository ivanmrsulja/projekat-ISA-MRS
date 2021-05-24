package rest.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.aspect.AsPacijent;
import rest.domain.AdminApoteke;
import rest.domain.Apoteka;
import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.StatusNaloga;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.service.ApotekaService;
import rest.service.KorisnikService;
import rest.service.PregledService;
import rest.util.ApotekaSearchParams;

@RestController
@RequestMapping("/api/apoteke")
public class ApotekaController {
	
	private ApotekaService apotekaService;
	private PregledService pregledService;
	private KorisnikService userService;
	
	@Autowired
	public ApotekaController(ApotekaService as, PregledService ps, KorisnikService us) {
		apotekaService = as;
		pregledService = ps;
		userService = us;
	}
	
	
	@GetMapping(value="/all/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ApotekaDTO> getAll(HttpSession sess, @PathVariable("page") int page, @RequestParam String naziv, @RequestParam String adresa, @RequestParam double dOcena, @RequestParam double gOcena, @RequestParam double rastojanje, @RequestParam String kriterijum, @RequestParam boolean smer) {
		ApotekaSearchParams params = new ApotekaSearchParams(naziv, adresa, dOcena, gOcena, rastojanje, kriterijum, smer);
		KorisnikDTO user = (KorisnikDTO) sess.getAttribute("user");
		Page<ApotekaDTO> apoteke = null;
		if(user == null) {
			params.setRastojanje(50000);
			apoteke = apotekaService.getAllDrugStores(page, params, 0.0, 0.0);
		}else {
			Korisnik k = userService.findOne(user.getId());
			apoteke = apotekaService.getAllDrugStores(page, params, k.getLokacija().getSirina(), k.getLokacija().getDuzina());
		}
		ArrayList<ApotekaDTO> retVals = new ArrayList<ApotekaDTO>();
		for(ApotekaDTO a : apoteke) {
			retVals.add(a);
		}
		return apoteke;
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody ApotekaDTO user) throws Exception {
		Apoteka k = new Apoteka();
		k.setNaziv(user.getNaziv());
		k.setOpis(user.getOpis());
		k.setOcena(user.getOcena());
		k.setLokacija(user.getLokacija());
		k.setCenaSavetovanja(user.getCena());
		apotekaService.create(k);
		return "OK";
	}
	
	@GetMapping(value="/every", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<ApotekaDTO> getAll() {
		return apotekaService.getAllPharmacies();
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApotekaDTO getOne(@PathVariable int id) {
		return new ApotekaDTO(this.apotekaService.getByID(id));
	}
	
	@GetMapping(value="admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApotekaDTO getOneForAdmin(@PathVariable int id) {
		AdminApoteke a = (AdminApoteke) userService.findOne(id);
		return new ApotekaDTO(this.apotekaService.getForAdmin(a.getApoteka().getId()));
	}
	
	@GetMapping(value="pregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<PregledDTO> getPreCreatedExaminations(@PathVariable int id, @RequestParam String criteria) {
		return apotekaService.getPregledi(id, criteria);
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
	
	@AsPacijent
	@PutMapping(value="zakaziPregled/{idp}/{idpa}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String scheduleExamination(@PathVariable int idp, @PathVariable int idpa) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		if (currentUser.getId() != idpa) {
			return null;
		}
			
		try {
			pregledService.zakaziPregled(idp, idpa);
			pregledService.sendConfirmationEmail(currentUser);
			return "Uspesno zakazan pregled.";
		} catch (OptimisticLockingFailureException ex) {
			return "Doslo je do greske prilikom zakazivanja pregleda, molimo osvezite stranicu i pokusajte ponovo.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@PatchMapping(value="otkazi/{idp}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String unscheduleExamination(@PathVariable int idp) {
		try {
			pregledService.otkaziPregled(idp);
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@GetMapping(value = "kandidati/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ApotekaDTO> getKandidatiSavetovanje(@PathVariable("page") int page, @RequestParam("datum") String datum, @RequestParam("vreme") String vreme, @RequestParam("criteria") String criteria) {
		try {
			return apotekaService.apotekeZaTerminSavetovanja(LocalDate.parse(datum), LocalTime.parse(vreme), criteria, page);
		} catch (Exception e) {
			return null;
		}
	}
	
	@AsPacijent
	@GetMapping(value = "slobodniFarmaceuti/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<FarmaceutDTO> getFarmaceutiSavetovanje(@PathVariable("id") int id, @RequestParam("datum") String datum, @RequestParam("vreme") String vreme, @RequestParam("criteria") String criteria) {
		Collection<Farmaceut> farmaceuti;
		try {
			farmaceuti = apotekaService.farmaceutiZaTerminSavetovanja(LocalDate.parse(datum), LocalTime.parse(vreme), id, criteria);
			ArrayList<FarmaceutDTO> ret = new ArrayList<FarmaceutDTO>();
			for(Farmaceut f : farmaceuti) {
				ret.add(new FarmaceutDTO(f));
			}
			return ret;
		} catch (Exception e) {
			return null;
		}
	}
	
}