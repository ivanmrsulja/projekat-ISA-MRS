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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import rest.domain.AdminApoteke;
import rest.domain.AkcijaPromocija;
import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.Dermatolog;
import rest.domain.DostupanProizvod;
import rest.domain.NaruceniProizvod;
import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.Preparat;
import rest.domain.StatusNaloga;
import rest.dto.KorisnikDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.dto.TipKorisnikaDTO;
import rest.dto.ZalbaDTO;
import rest.domain.StatusNarudzbenice;
import rest.domain.StatusPonude;
import rest.domain.TeloAkcijePromocije;
import rest.domain.ZaposlenjeKorisnika;
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
import rest.repository.PonudaRepository;
import rest.repository.PreparatRepository;
import rest.service.AdminService;
import rest.service.AkcijaPromocijaService;
import rest.service.PacijentService;


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
	private PonudaRepository ponudaRepository;
	private PacijentService pacijentService;
	
	
	@Autowired
	public AdminController(PacijentService pacser,AdminService as, AdminApotekeRepository aar, AkcijaPromocijaRepository apr, AkcijaPromocijaService aps, PacijentRepository pr,
			ApotekaController ac, CenaRepository cr7, ApotekeRepository ar, DostupanProizvodRepository dpr, PreparatRepository prepRep, NarudzbenicaRepozitory nr,
			PonudaRepository pRepo) {
		this.adminService = as;
		this.pacijentService = pacser;
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
		this.ponudaRepository = pRepo;
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
	
	@GetMapping(value = "/availableNar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NarudzbenicaDTO> availableNar(@PathVariable("id") int id){
		Collection<NarudzbenicaDTO> abc = adminService.getAvailableNarudzbenice(id);

		return (ArrayList<NarudzbenicaDTO>) abc;
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
	
	@PostMapping(value = "/registerType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody TipKorisnikaDTO tip) throws Exception {
		System.out.println(tip.getNaziv() + tip.getBodovi() + tip.getPopust());
		adminService.createType(tip);
		return "OK";
	}
	
	@GetMapping(value= "/getZalba/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ZalbaDTO getZalba(@PathVariable("id") int id) {
		ZalbaDTO users = pacijentService.getOneZalba(id);
		
		return users;
	}
	
	@PutMapping(value = "/ZalbeUpdate/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateZalba(@PathVariable("id") int id) {
		adminService.updateZalba(id);
		return "OK";
	}
	
	@GetMapping(value = "/Zalbe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<ZalbaDTO> getZalbeForAdmin(@PathVariable("id") int id){
		Collection<ZalbaDTO> zdtos = pacijentService.getZalbeForAdmin(id);

		return (ArrayList<ZalbaDTO>) zdtos;
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

	@PutMapping(value = "/updateOrderStatus/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOrderStatus(@PathVariable("orderId") int orderId) {
		Narudzbenica n = narudzbenicaRepository.findById(orderId).get();
		n.setStatus(StatusNarudzbenice.OBRADJENA);
		narudzbenicaRepository.save(n);

		return "OK";
	}

	@PutMapping(value = "/updateOffersStatus/{orderId}/{offerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOffersStatus(@RequestBody Collection<PonudaDTO> offers, @PathVariable("orderId") int orderId, @PathVariable("offerId") int offerId) {
		ponudaRepository.updateOffersStatus(orderId);
		Ponuda p = ponudaRepository.findById(offerId).get();
		p.setStatus(StatusPonude.PRIHVACENA);
		ponudaRepository.save(p);

		adminService.notifySuppliersViaEmail(offers);

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
	
	@DeleteMapping(value = "/deleteOrder/{orderId}/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteOrder(@PathVariable("orderId") int orderId, @PathVariable("adminId") int adminId) {
		int numberOfOffers = ponudaRepository.getNumberOfOffersForOrder(orderId);
		if (numberOfOffers != 0) {
			return "ERR";
		}

		narudzbenicaRepository.deleteById(orderId);
		
		return "OK";
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

	@GetMapping(value = "/getOrder/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NarudzbenicaDTO getOrderById(@PathVariable("orderId") int orderId) {
		Narudzbenica narudzbenica = narudzbenicaRepository.getOrderById(orderId);
		NarudzbenicaDTO narudzbenicaDTO = new NarudzbenicaDTO(narudzbenica);

		return narudzbenicaDTO;
	}
	
	@GetMapping(value = "/orderOffers/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PonudaDTO> getOffersForPharmacy(@PathVariable("orderId") int orderId) {
		Collection<Ponuda> offers = ponudaRepository.getOffersForOrder(orderId);
		ArrayList<PonudaDTO> offersDTO = new ArrayList<PonudaDTO>();
		for (Ponuda p : offers) {
			offersDTO.add(new PonudaDTO(p));
		}

		return offersDTO;
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
		if (cenovnik == null) {
			cenovnik = new Cena();
			Apoteka apoteka = apotekeRepository.findById(idApoteke).get();
			cenovnik.setApoteka(apoteka);
			cenovnik.setPocetakVazenja(LocalDate.now());
			cenaRepository.save(cenovnik);
		}
		CenovnikDTO cenovnikDTO = new CenovnikDTO(cenovnik);
		return cenovnikDTO;
	}

	@PutMapping(value = "/updateStocks/{orderId}/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateStocksForOrder(@PathVariable("orderId") int orderId, @PathVariable("adminId") int adminId) {
		adminService.updateStocks(orderId, adminId);

		return "OK";
	}

	@GetMapping(value = "/narudzbenice/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NarudzbenicaDTO> getOrdersForPharmacy(@PathVariable("id") int idAdmina){
		ArrayList<NarudzbenicaDTO> narudzbenice = adminService.findOrdersForPharmacy(idAdmina);
		
		return narudzbenice;
	}

	@PostMapping(value = "/registerCenovnik/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerCenovnik(@RequestBody CenovnikDTO cenovnikDTO, @PathVariable("id") int idApoteke) throws Exception{
		adminService.registerPricelist(cenovnikDTO, idApoteke);

		return "OK";
	}
}