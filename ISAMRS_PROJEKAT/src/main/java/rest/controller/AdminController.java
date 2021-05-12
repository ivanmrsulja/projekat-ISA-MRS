package rest.controller;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.DostupanProizvod;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.AkcijaPromocijaRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DostupanProizvodRepository;
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
	private CenaRepository cenaRepository;
	private ApotekeRepository apotekeRepository;
	private DostupanProizvodRepository dostupanProizvodRepository;
	
	@Autowired
	public AdminController(AdminService as, AdminApotekeRepository aar, AkcijaPromocijaRepository apr, AkcijaPromocijaService aps, PacijentRepository pr, ApotekaController ac, CenaRepository cr7, ApotekeRepository ar, DostupanProizvodRepository dpr) {
		this.adminService = as;
		this.adminApotekeRepository = aar;
		this.akcijaPromocijaRepository = apr;
		this.akcijaPromocijaService = aps;
		this.pacijentRepository = pr;
		this.apotekaController = ac;
		this.cenaRepository = cr7;
		this.apotekeRepository = ar;
		this.dostupanProizvodRepository = dpr;
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

	@GetMapping(value="/cenovnik/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CenovnikDTO getPricelistForPharmacy(@PathVariable("id") int idApoteke) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(idApoteke);
		CenovnikDTO cenovnikDTO = new CenovnikDTO(cenovnik);
		return cenovnikDTO;
	}

	@GetMapping(value="/narudzbenice/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NarudzbenicaDTO> getOrdersForPharmacy(@PathVariable("id") int idAdmina){
		ArrayList<NarudzbenicaDTO> narudzbenice = adminService.findOrdersForPharmacy(idAdmina);
		
		return narudzbenice;
	}

	@PostMapping(value="/registerCenovnik/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerCenovnik(@RequestBody CenovnikDTO cenovnikDTO, @PathVariable("id") int idApoteke) throws Exception{
		Apoteka apoteka = apotekeRepository.findById(idApoteke).get();
		Cena cenovnik = new Cena();
		cenovnik.setApoteka(apoteka);
		Collection<DostupanProizvod> dostupniProizvodi = cenaRepository.getDostupniProizvodiZaApoteku(idApoteke);
		for (DostupanProizvod dp : dostupniProizvodi) {
			for (DostupanProizvodDTO dpDTO : cenovnikDTO.getDostupniProizvodi()) {
				if (dp.getId().equals(dpDTO.getId())) {
					dp.setCena(dpDTO.getCena());
					dp.setId(null);
					this.dostupanProizvodRepository.save(dp);
					break;
				}
			}
		}
		Set<DostupanProizvod> proizvodi = new HashSet<DostupanProizvod>();
		for (DostupanProizvod dp : dostupniProizvodi) {
			proizvodi.add(dp);
		}
		cenovnik.setDostupniProizvodi(proizvodi);
		cenovnik.setPocetakVazenja(cenovnikDTO.getPocetakVazenja());
		cenaRepository.save(cenovnik);

		return "OK";
	}
}