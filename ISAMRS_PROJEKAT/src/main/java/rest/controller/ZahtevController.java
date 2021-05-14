package rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

import rest.domain.Farmaceut;
import rest.domain.Zahtjev;
import rest.dto.KorisnikDTO;
import rest.dto.ZahtevDTO;
import rest.repository.FarmaceutRepository;
import rest.repository.ZahtevRepository;
import rest.service.FarmaceutService;
import rest.service.ZahtevService;

@RestController
@RequestMapping("/api/zahtev")
public class ZahtevController {

	private ZahtevRepository zahtevRepository;
	private FarmaceutRepository farmaceutRepository;
	private FarmaceutService farmaceutService;
	private ZahtevService zahtevService;

	@Autowired
	public ZahtevController (ZahtevRepository zr, FarmaceutRepository fr, FarmaceutService fs, ZahtevService zs) {
		this.zahtevRepository = zr;
		this.farmaceutRepository = fr;
		this.farmaceutService = fs;
		this.zahtevService = zs;
	}

	@GetMapping(value="/apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<ZahtevDTO> getZahteviZaFarmaceute(@PathVariable("id") int id){
		Collection<Farmaceut> farmaceutiApoteke = farmaceutRepository.getWithEmployments(id);
		Collection<Integer> ids = farmaceutiApoteke.stream().map(f -> f.getId()).collect(Collectors.toList());
		Collection<Zahtjev> zahtevi = zahtevRepository.getAllForPharmacy(ids);
		ArrayList<ZahtevDTO> zahteviDTO = new ArrayList<ZahtevDTO>();
		for (Zahtjev z : zahtevi)
			zahteviDTO.add(new ZahtevDTO(z));
		return zahteviDTO;
	}
	
	@PutMapping(value = "/update/{text}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ZahtevDTO> updateZahtev(@RequestBody ZahtevDTO zahtev, @PathVariable("text") String text)
			throws Exception {
		Zahtjev updatedZahtev = zahtevService.update(zahtev);

		if (updatedZahtev == null) {
			return new ResponseEntity<ZahtevDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
		zahtevService.notifyViaEmail(zahtev, text);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return new ResponseEntity<ZahtevDTO>(new ZahtevDTO(updatedZahtev), HttpStatus.OK);
	}

	@PostMapping(value = "/zahtjev", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addZahtjev(HttpSession session, @RequestBody Zahtjev p){
		KorisnikDTO user = (KorisnikDTO) session.getAttribute("user");
		zahtevService.addZahtjev(p,user.getId());
		return "OK";
	}
}
