package rest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.aspect.AsPacijent;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.dto.CenaDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PreparatDTO;
import rest.service.KorisnikService;
import rest.service.PreparatService;

@RestController
@RequestMapping("/api/preparat")
public class PreparatController {
	
	private KorisnikService korisnikService;
	private PreparatService preparatService;
	
	@Autowired
	public PreparatController(KorisnikService ks, PreparatService er) {
		this.korisnikService = ks;
		this.preparatService = er;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getPreparat() {
		Collection<Preparat> lekovi = preparatService.getAll();
		ArrayList<PreparatDTO> preparati=new ArrayList<PreparatDTO>();
		for(Preparat p : lekovi)
			preparati.add(new PreparatDTO(p));
		return preparati;
	}
	
	
	@GetMapping(value = "search/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getSearchPreparat(@PathVariable("name") String name) {
		Collection<Preparat> lekovi = preparatService.getAll();
		ArrayList<PreparatDTO> preparati=new ArrayList<PreparatDTO>();
		for(Preparat p : lekovi)
			if(p.getNaziv().toLowerCase().startsWith(name.toLowerCase())) {
				preparati.add(new PreparatDTO(p));
			}
			
		return preparati;
	}
	
	@GetMapping(value = "spec/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PreparatDTO getSpec(@PathVariable("id") int id){
		return new PreparatDTO(preparatService.getOne(id));
	}
	
	@AsPacijent
	@GetMapping(value = "specifikacija/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PreparatDTO getSpecPacijent(@PathVariable("id") int id){
		return new PreparatDTO(preparatService.getOne(id));
	}

	@GetMapping(value = "apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getPreparatiForApoteka(@PathVariable("id") int id)
	{
		Collection<Preparat> preparati = preparatService.getAllForPharmacy(id);
		ArrayList<PreparatDTO> ret = new ArrayList<PreparatDTO>();
		for(Preparat p : preparati) {
			ret.add(new PreparatDTO(p));
		}
		return ret;
	}
	
	@AsPacijent
	@GetMapping(value = "dostupnost/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<CenaDTO> getApoteke(@PathVariable("id") int id){
		return preparatService.getPharmaciesForDrug(id);
	}
	
	@AsPacijent
	@GetMapping(value="rezervisi/{idPreparat}/{idApoteka}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String rezervisi(@PathVariable int idPreparat, @PathVariable int idApoteka, @RequestParam String datum) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		String[] tokens = datum.split("-");
		LocalDate d = LocalDate.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
		try {
			Rezervacija rez = preparatService.rezervisi(idPreparat, currentUser.getId(), idApoteka, d);
			preparatService.sendConfirmationEmail(currentUser, rez);
			return "Uspesno rezervisano.";
		}catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	@AsPacijent
	@PatchMapping(value="otkazi/{idPreparat}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String unscheduleExamination(@PathVariable int idPreparat) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			preparatService.otkazi(idPreparat, currentUser.getId());
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}
