package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.aspect.AsPacijent;
import rest.dto.KorisnikDTO;
import rest.service.OcenaService;

@RestController
@RequestMapping("/api/ocene")
public class OceneController {
	
	private OcenaService oceneService;
	
	@Autowired
	public OceneController(OcenaService os) {
		oceneService = os;
	}
	
	@AsPacijent
	@GetMapping(value="apoteka/{ida}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getOcenaApoteka(@PathVariable("ida") int idApoteke) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		return oceneService.ocenjivaApoteka(idApoteke, currentUser.getId());
	}
	
	@AsPacijent
	@GetMapping(value="preparat/{idp}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getOcenaPreparata(@PathVariable("idp") int idPreparata) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		return oceneService.ocenjivPreparat(idPreparata, currentUser.getId());
	}
	
	@AsPacijent
	@GetMapping(value="dermatolog/{idp}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getOcenaDermatologa(@PathVariable("idp") int idDermatologa) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		return oceneService.ocenjivDermatolog(idDermatologa, currentUser.getId());
	}
	
	@AsPacijent
	@GetMapping(value="farmaceut/{idp}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getOcenaFarmaceuta(@PathVariable("idp") int idFarmaceuta) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		return oceneService.ocenjivFarmaceut(idFarmaceuta, currentUser.getId());
	}
	
	@AsPacijent
	@PutMapping(value="oceniApoteku/{ida}/{ocena}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String oceniApoteku(@PathVariable("ida") int idApoteke, @PathVariable("ocena") int ocena) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			oceneService.oceniApoteku(idApoteke, ocena, currentUser.getId());
			return "Uspesno ocenjeno.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@PutMapping(value="oceniPreparat/{idp}/{ocena}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String oceniPreparat(@PathVariable("idp") int idPreparata, @PathVariable("ocena") int ocena) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			oceneService.oceniPreparat(idPreparata, currentUser.getId(), ocena);
			return "Uspesno ocenjeno.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@PutMapping(value="oceniFarmaceuta/{idf}/{ocena}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String oceniFarmaceuta(@PathVariable("idf") int idFarmaceuta, @PathVariable("ocena") int ocena) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			oceneService.oceniFarmaceuta(idFarmaceuta, currentUser.getId(), ocena);
			return "Uspesno ocenjeno.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@PutMapping(value="oceniDermatologa/{idd}/{ocena}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String oceniDermatologa(@PathVariable("idd") int idDermatologa, @PathVariable("ocena") int ocena) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			oceneService.oceniDermatologa(idDermatologa, currentUser.getId(), ocena);
			return "Uspesno ocenjeno.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
