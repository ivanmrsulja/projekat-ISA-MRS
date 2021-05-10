package rest.controller;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.AdminApoteke;
import rest.domain.AkcijaPromocija;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.PonudaDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.AkcijaPromocijaRepository;
import rest.repository.PacijentRepository;
import rest.service.AdminService;
import rest.service.AkcijaPromocijaService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private AdminService adminService;
	private AdminApotekeRepository adminApotekeRepository;
	private AkcijaPromocijaRepository akcijaPromocijaRepository;
	private AkcijaPromocijaService akcijaPromocijaService;
	private PacijentRepository pacijentRepository;
	private ApotekaController apotekaController;
	
	@Autowired
	public AdminController(AdminService as, AdminApotekeRepository aar, AkcijaPromocijaRepository apr, AkcijaPromocijaService aps, PacijentRepository pr, ApotekaController ac) {
		this.adminService = as;
		this.adminApotekeRepository = aar;
		this.akcijaPromocijaRepository = apr;
		this.akcijaPromocijaService = aps;
		this.pacijentRepository = pr;
		this.apotekaController = ac;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getOffers() {
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			ponude.add(new PonudaDTO(p));
		}
		return new ResponseEntity<Collection<PonudaDTO>>(ponude, HttpStatus.OK);
	}

	@PostMapping(value="/registerPromo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerPromotion(@RequestBody TeloAkcijePromocije telo) throws Exception{
		AdminApoteke admin = adminApotekeRepository.findById(telo.getIdAdmina()).get();
		AkcijaPromocija ap = new AkcijaPromocija(telo.getTekst(), admin);
		akcijaPromocijaService.create(ap);

		ApotekaDTO apoteka = apotekaController.getOneForAdmin(admin.getId());
		Collection<Pacijent> pretplaceniPacijenti = pacijentRepository.getPatientsSubscribedToPharmacy(apoteka.getId());
		for (Pacijent p : pretplaceniPacijenti) {
			adminService.notifyPatientViaEmail(apoteka, p, telo);
		}

		return "OK";
	}
}