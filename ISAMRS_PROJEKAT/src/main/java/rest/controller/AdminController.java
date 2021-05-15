package rest.controller;



import java.util.ArrayList;
import java.util.Collection;

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
import org.springframework.web.bind.annotation.RestController;

import rest.aspect.AsAdminApoteke;
import rest.domain.Narudzbenica;
import rest.domain.Ponuda;
import rest.dto.KorisnikDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.domain.TeloAkcijePromocije;
import rest.dto.CenovnikDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.repository.NarudzbenicaRepozitory;
import rest.repository.PonudaRepository;
import rest.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private AdminService adminService;
	private NarudzbenicaRepozitory narudzbenicaRepository;
	private PonudaRepository ponudaRepository;
	
	@Autowired
	public AdminController(AdminService as, NarudzbenicaRepozitory nr, PonudaRepository pr) {
		this.adminService = as;
		this.narudzbenicaRepository = nr;
		this.ponudaRepository = pr;
	}


	@AsAdminApoteke
	@GetMapping(value = "/searchPharmacy/{id}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> searchPharmacyProducts(@PathVariable("id") int pharmacyId, @PathVariable("name") String name) {
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = adminService.searhProductsOfPharmacy(pharmacyId, name);

		return availablePharmacyProductsDTO;
	}

	@AsAdminApoteke
	@GetMapping(value = "/searchPharmacy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> getAllProductsOfPharmacy(@PathVariable("id") int pharmacyId){
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = adminService.getProductsForPharmacy(pharmacyId);

		return availablePharmacyProductsDTO;
	}
	
	@AsAdminApoteke
	@GetMapping(value = "/productsOutsidePharmacy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PreparatDTO> getProductsOutsidePharmacy(@PathVariable("id") int pharmacyId) {
		ArrayList<PreparatDTO> preparatiDTO = adminService.getProductsOutsidePharmacy(pharmacyId);

		return preparatiDTO;
	}

	@AsAdminApoteke
	@PostMapping(value = "/registerOrder/{adminId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerOrder(@RequestBody NarudzbenicaDTO narudzbenicaDTO, @PathVariable("adminId") int adminId) {
		adminService.registerOrder(narudzbenicaDTO, adminId);

		return "OK";
	}

	@AsAdminApoteke
	@PutMapping(value = "/addProductToPharmacy/{pharmacyId}/{price}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerProductForPharmacy(@RequestBody PreparatDTO preparat, @PathVariable("pharmacyId") int pharmacyId, @PathVariable("price") double price) {
		adminService.addProductToPharmacy(preparat, pharmacyId, price);

		return "OK";
	}

	@AsAdminApoteke
	@PutMapping(value = "/updateOrderStatus/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOrderStatus(@PathVariable("orderId") int orderId) {
		adminService.updateStatusOfOrder(orderId);

		return "OK";
	}

	@AsAdminApoteke
	@PutMapping(value = "/updateOffersStatus/{orderId}/{offerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateOffersStatus(@RequestBody Collection<PonudaDTO> offers, @PathVariable("orderId") int orderId, @PathVariable("offerId") int offerId) {
		adminService.updateStatusOfOffers(offers, orderId, offerId);

		return "OK";
	}

	@AsAdminApoteke
	@DeleteMapping(value = "/deleteProduct/{productId}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DostupanProizvodDTO> deleteProductFromPharmacy(@PathVariable("productId") int productId, @PathVariable("pharmacyId") int pharmacyId){
		adminService.deleteProductFromPharmacy(productId, pharmacyId);
		ArrayList<DostupanProizvodDTO> productsOfPharmacy = adminService.getProductsForPharmacy(pharmacyId);

		return productsOfPharmacy;
	}
	
	@AsAdminApoteke
	@DeleteMapping(value = "/deleteOrder/{orderId}/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteOrder(@PathVariable("orderId") int orderId, @PathVariable("adminId") int adminId) {
		Narudzbenica order = narudzbenicaRepository.findById(orderId).get();
		int numberOfOffers = ponudaRepository.getNumberOfOffersForOrder(orderId);
		if (numberOfOffers != 0 || order.getAdminApoteke().getId() != adminId) {
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

	@AsAdminApoteke
	@GetMapping(value = "/getOrder/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NarudzbenicaDTO getOrderById(@PathVariable("orderId") int orderId) {
		Narudzbenica narudzbenica = narudzbenicaRepository.getOrderById(orderId);
		NarudzbenicaDTO narudzbenicaDTO = new NarudzbenicaDTO(narudzbenica);

		return narudzbenicaDTO;
	}
	
	@AsAdminApoteke
	@GetMapping(value = "/orderOffers/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PonudaDTO> getOffersForPharmacy(@PathVariable("orderId") int orderId) {
		ArrayList<PonudaDTO> offersDTO = adminService.getOffersForPharmacy(orderId);

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

	@AsAdminApoteke
	@PostMapping(value="/registerPromo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerPromotion(@RequestBody TeloAkcijePromocije telo) throws Exception{
		adminService.registerPromotion(telo);

		return "OK";
	}

	@AsAdminApoteke
	@GetMapping(value = "/cenovnik/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CenovnikDTO getPricelistForPharmacy(@PathVariable("id") int pharmacyId) {
		CenovnikDTO cenovnikDTO = adminService.findPricelistForPharmacy(pharmacyId);

		return cenovnikDTO;
	}

	@PutMapping(value = "/updateStocks/{orderId}/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateStocksForOrder(@PathVariable("orderId") int orderId, @PathVariable("adminId") int adminId) {
		adminService.updateStocks(orderId, adminId);

		return "OK";
	}

	@AsAdminApoteke
	@GetMapping(value = "/narudzbenice/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NarudzbenicaDTO> getOrdersForPharmacy(@PathVariable("id") int idAdmina){
		ArrayList<NarudzbenicaDTO> narudzbenice = adminService.findOrdersForPharmacy(idAdmina);
		
		return narudzbenice;
	}

	@AsAdminApoteke
	@PostMapping(value = "/registerCenovnik/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerCenovnik(@RequestBody CenovnikDTO cenovnikDTO, @PathVariable("id") int idApoteke) throws Exception{
		adminService.registerPricelist(cenovnikDTO, idApoteke);

		return "OK";
	}
}