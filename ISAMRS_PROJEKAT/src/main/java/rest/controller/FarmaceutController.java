package rest.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.aspect.AsAdminApoteke;
import rest.aspect.AsPacijent;
import rest.domain.Farmaceut;
import rest.domain.Pregled;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.service.ApotekaService;
import rest.service.FarmaceutService;
import rest.service.PregledService;


@RestController
@RequestMapping("/api/farmaceut")
public class FarmaceutController {

	private FarmaceutService farmaceutService;
	private ApotekaService apotekaService;
	private PregledService pregledService;
	
	@Autowired
	public FarmaceutController(FarmaceutService farmaceut, ApotekaService as, PregledService pregledService) {
		this.farmaceutService = farmaceut;
		this.apotekaService = as;
		this.pregledService = pregledService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsers() {
		Collection<Farmaceut> users = farmaceutService.findAll();
		ArrayList<KorisnikDTO> farmaceuti=new ArrayList<KorisnikDTO>();
		for(Farmaceut d : users)
			farmaceuti.add(new KorisnikDTO(d));
		return farmaceuti;
	}
	
	@GetMapping(value="/apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsersForPharmacy(@PathVariable("id") int id) {
		Collection<Farmaceut> users = farmaceutService.findAllForPharmacy(id);
		ArrayList<KorisnikDTO> farmaceuti = new ArrayList<KorisnikDTO>();
		for(Farmaceut f : users)
			farmaceuti.add(new KorisnikDTO(f));
		return farmaceuti;
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		System.out.println(user);
		Farmaceut updatedFarmaceut = farmaceutService.update(user);

		if (updatedFarmaceut == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(updatedFarmaceut), HttpStatus.OK);
	}

	@AsAdminApoteke
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody FarmaceutDTO farmaceut) throws Exception {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		farmaceutService.create(farmaceut, currentUser.getId());
		return "OK";
	}
	

	@AsPacijent
	@PostMapping(value = "/zakaziSavetovanje/{id}/{ida}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getFarmaceutiSavetovanje(@RequestBody PregledDTO novi, @PathVariable("id") int idFarmaceuta, @PathVariable("ida") int idApoteke) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		try {
			Pregled p = apotekaService.zakaziSavetovanje(novi, idApoteke, idFarmaceuta, currentUser.getId());
			pregledService.sendConfirmationEmailAdv(currentUser, p);
			return "Pregled uspesno zakazan.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}	

	@GetMapping(value ="/savetovanja", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PregledDTO> getPregledi(HttpSession s) {
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		for(Pregled d : pregledi)
			preglediDTO.add(new PregledDTO(d,0));
		return preglediDTO;
	}
	
	@GetMapping(value ="/savetovanja/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PregledDTO getPregled(@PathVariable Integer id) {
		return new PregledDTO(pregledService.dobaviPregledZa(id),0);

	}
}