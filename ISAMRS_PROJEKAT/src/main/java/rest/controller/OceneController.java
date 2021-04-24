package rest.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.aspect.AsPacijent;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.service.OcenaService;

@RestController
@RequestMapping("/api/ocene")
public class OceneController {
	
	private OcenaService oceneService;
	
	@Autowired
	public OceneController(OcenaService os) {
		oceneService = os;
	}
	
	@GetMapping(value="apoteka/{ida}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getOcenaApoteka(@PathVariable("ida") int idApoteke) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		return oceneService.ocenjivaApoteka(idApoteke, currentUser.getId());
	}
	
	@GetMapping(value="oceniApoteku/{ida}/{ocena}", produces = MediaType.APPLICATION_JSON_VALUE)
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
}
