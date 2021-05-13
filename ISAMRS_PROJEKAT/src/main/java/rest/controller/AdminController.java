package rest.controller;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.AdminApoteke;
import rest.domain.AkcijaPromocija;
import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.DostupanProizvod;
import rest.domain.NaruceniProizvod;
import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.Preparat;
import rest.dto.KorisnikDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.domain.StatusNarudzbenice;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.NaruceniProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.AkcijaPromocijaRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.NarudzbenicaRepozitory;
import rest.repository.PacijentRepository;
import rest.repository.PreparatRepository;
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
	private PreparatRepository preparatRepository;
	private NarudzbenicaRepozitory narudzbenicaRepository;
	
	@Autowired
	public AdminController(AdminService as, AdminApotekeRepository aar, AkcijaPromocijaRepository apr, AkcijaPromocijaService aps, PacijentRepository pr,
			ApotekaController ac, CenaRepository cr7, ApotekeRepository ar, DostupanProizvodRepository dpr, PreparatRepository prepRep, NarudzbenicaRepozitory nr) {
		this.adminService = as;
		this.adminApotekeRepository = aar;
		this.akcijaPromocijaRepository = apr;
		this.akcijaPromocijaService = aps;
		this.pacijentRepository = pr;
		this.apotekaController = ac;
		this.cenaRepository = cr7;
		this.apotekeRepository = ar;
		this.dostupanProizvodRepository = dpr;
		this.preparatRepository = prepRep;
		this.narudzbenicaRepository = nr;
	}


	@GetMapping(value = "/searchPharmacy/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> searchPharmacyProducts(@PathVariable("id") int pharmacyId, @PathVariable("name") String name){
		Collection<DostupanProizvod> availablePharmacyProducts = dostupanProizvodRepository.getForPharmacy(pharmacyId);
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = new ArrayList<DostupanProizvodDTO>();
		
		for (DostupanProizvod dp : availablePharmacyProducts) {
			if (dp.getPreparat().getNaziv().contains(name))
				availablePharmacyProductsDTO.add(new DostupanProizvodDTO(dp));
		}

		return availablePharmacyProductsDTO;
	}

	@GetMapping(value = "/searchPharmacy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> getAllProductsOfPharmacy(@PathVariable("id") int pharmacyId){
		Collection<DostupanProizvod> availablePharmacyProducts = dostupanProizvodRepository.getForPharmacy(pharmacyId);
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = new ArrayList<DostupanProizvodDTO>();
		
		for (DostupanProizvod dp : availablePharmacyProducts) {
			availablePharmacyProductsDTO.add(new DostupanProizvodDTO(dp));
		}

		return availablePharmacyProductsDTO;
	}
	
	@GetMapping(value = "/productsOutsidePharmacy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getProductsOutsidePharmacy(@PathVariable("id") int pharmacyId){
		Collection<Preparat> preparati = dostupanProizvodRepository.getProductsOutsidePharmacy(pharmacyId);
		ArrayList<PreparatDTO> preparatiDTO = new ArrayList<>();
		for (Preparat p : preparati) {
			preparatiDTO.add(new PreparatDTO(p));
		}

		return preparatiDTO;
	}

	@PostMapping(value = "/registerOrder/{adminId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerOrder(@RequestBody NarudzbenicaDTO narudzbenicaDTO, @PathVariable("adminId") int adminId) {
		Narudzbenica narudzbenica = new Narudzbenica();
		AdminApoteke adminApoteke = adminApotekeRepository.findById(adminId).get();
		narudzbenica.setAdminApoteke(adminApoteke);
		narudzbenica.setStatus(StatusNarudzbenice.CEKA_PONUDE);
		narudzbenica.setRok(narudzbenicaDTO.getRok());
		Preparat p = null;
		for (NaruceniProizvodDTO npDTO : narudzbenicaDTO.getNaruceniProizvodi()) {
			p = preparatRepository.getPreparatByName(npDTO.getPreparat());
			narudzbenica.getNaruceniProizvodi().add(new NaruceniProizvod(npDTO.getKolicina(), p, narudzbenica));
		}

		narudzbenicaRepository.save(narudzbenica);

		return "OK";
	}

	@PutMapping(value = "/addProductToPharmacy/{pharmacyId}/{price}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerProductForPharmacy(@RequestBody PreparatDTO preparat, @PathVariable("pharmacyId") int pharmacyId, @PathVariable("price") double price) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(pharmacyId);
		if (cenovnik == null) {
			Apoteka apoteka = apotekeRepository.findById(pharmacyId).get();
			cenovnik = new Cena();
			cenovnik.setApoteka(apoteka);
			cenovnik.setPocetakVazenja(LocalDate.now());
		}
		Preparat p = preparatRepository.findById(preparat.getId()).get();
		DostupanProizvod dp = new DostupanProizvod(0, price, p);
		cenovnik.getDostupniProizvodi().add(dp);
		dostupanProizvodRepository.save(dp);
		cenaRepository.save(cenovnik);

		return "OK";
	}

	@DeleteMapping(value = "/deleteProduct/{productId}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> deleteProductFromPharmacy(@PathVariable("productId") int productId, @PathVariable("pharmacyId") int pharmacyId){
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(pharmacyId);
		DostupanProizvod dpToDelete = null;
		for (DostupanProizvod dp : cenovnik.getDostupniProizvodi()) {
			if (dp.getId().equals(productId)) {
				dpToDelete = dp;
			}
		}
		cenovnik.getDostupniProizvodi().remove(dpToDelete);
		cenaRepository.save(cenovnik);
		dostupanProizvodRepository.deleteById(productId);
		
		return getAllProductsOfPharmacy(pharmacyId);
	}
	
	@GetMapping(value = "cures/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getStatusPonuda(HttpServletRequest request, @PathVariable("status") String status) {
		KorisnikDTO u = (KorisnikDTO) request.getSession().getAttribute("user");
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			if(p.getDobavljac().getUsername().equals(u.getUsername())) {
				if(p.getStatus().toString().equals(status) || status.equals("SVI")) {
					ponude.add(new PonudaDTO(p));
				}

			}
		}
		return new ResponseEntity<Collection<PonudaDTO>>(ponude, HttpStatus.OK);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getOffers(HttpServletRequest request) {
		KorisnikDTO u = (KorisnikDTO) request.getSession().getAttribute("user");
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			if(p.getDobavljac().getUsername().equals(u.getUsername())) {
				ponude.add(new PonudaDTO(p));
			}
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

	@GetMapping(value = "/cenovnik/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CenovnikDTO getPricelistForPharmacy(@PathVariable("id") int idApoteke) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(idApoteke);
		CenovnikDTO cenovnikDTO = new CenovnikDTO(cenovnik);
		return cenovnikDTO;
	}

	@GetMapping(value = "/narudzbenice/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NarudzbenicaDTO> getOrdersForPharmacy(@PathVariable("id") int idAdmina){
		ArrayList<NarudzbenicaDTO> narudzbenice = adminService.findOrdersForPharmacy(idAdmina);
		
		return narudzbenice;
	}

	@PostMapping(value = "/registerCenovnik/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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