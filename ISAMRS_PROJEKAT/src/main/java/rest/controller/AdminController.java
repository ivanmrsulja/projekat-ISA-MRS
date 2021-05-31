package rest.controller;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DermatologDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.IzvestajValueDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.NotifikacijaDTO;
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

	@Scheduled(cron = "${akcije.cron}")
	public void cronJob() {
		adminService.deleteOutdatedPromotion();
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
		ArrayList<PreparatDTO> productsDTO = adminService.getProductsOutsidePharmacy(pharmacyId);

		return productsDTO;
	}

	@AsAdminApoteke
	@GetMapping(value = "/openExaminations/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PregledDTO> getOpenExaminations(@PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<PregledDTO> openExaminations = adminService.getOpenExaminationsForPharmacy(pharmacyId);

		return openExaminations;
	}

	@AsAdminApoteke
	@GetMapping(value = "/notifications/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<NotifikacijaDTO> getNotificationsForPharmacy(@PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<NotifikacijaDTO> notifications = adminService.getNotificationsForPharmacy(pharmacyId);

		return notifications;
	}

	@AsAdminApoteke
	@PutMapping(value = "/updateNotifications/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateNotifications(@PathVariable("pharmacyId") int pharmacyId) {
		adminService.updatePharmacyNotifications(pharmacyId);
		
		return "OK";
	}

	@AsAdminApoteke
	@GetMapping(value = "/yearlyExaminations/{year}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getExaminationsForYear(@PathVariable("year") int year, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> examinations = adminService.getYearlyExaminations(year, pharmacyId);

		return examinations;
	}

	@AsAdminApoteke
	@GetMapping(value = "/yearlyIncome/{year}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getIncomeForYear(@PathVariable("year") int year, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> incomes = adminService.getYearlyIncome(year, pharmacyId);

		return incomes;
	}
	
	@AsAdminApoteke
	@GetMapping(value = "/yearlyDrugsUsage/{year}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getDrugsUsageForYear(@PathVariable("year") int year, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> usage = adminService.getYearlyUsage(year, pharmacyId);

		return usage;
	}

	@AsAdminApoteke
	@GetMapping(value = "/quarterlyExaminations/{year}/{quarter}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getExaminationsForQuarter(@PathVariable("year") int year, @PathVariable("quarter") int quarter, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> examinations = adminService.getQuarterlyExaminations(year, quarter, pharmacyId);

		return examinations;
	}
	
	@AsAdminApoteke
	@GetMapping(value = "/quarterlyIncome/{year}/{quarter}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getIncomeForQuarter(@PathVariable("year") int year, @PathVariable("quarter") int quarter, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> incomes = adminService.getQuarterlyIncome(year, quarter, pharmacyId);

		return incomes;
	}

	@AsAdminApoteke
	@GetMapping(value = "/quarterlyDrugsUsage/{year}/{quarter}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getDrugsUsageForQuarter(@PathVariable("year") int year, @PathVariable("quarter") int quarter, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> usage = adminService.getQuarterlyUsage(year, quarter, pharmacyId);

		return usage;
	}

	@AsAdminApoteke
	@GetMapping(value = "/monthlyExaminations/{year}/{month}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getExaminationsForMonth(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> examinations = adminService.getMonthlyExaminations(year, month, pharmacyId);

		return examinations;
	}

	@AsAdminApoteke
	@GetMapping(value = "/monthlyIncome/{year}/{month}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getIncomeForMonth(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> incomes = adminService.getMonthlyIncome(year, month, pharmacyId);

		return incomes;
	}

	@AsAdminApoteke
	@GetMapping(value = "/monthlyDrugsUsage/{year}/{month}/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<IzvestajValueDTO> getDrugsUsageForMonth(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("pharmacyId") int pharmacyId) {
		ArrayList<IzvestajValueDTO> usage = adminService.getMonthlyUsage(year, month, pharmacyId);

		return usage;
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
	@PutMapping(value = "/updateExaminationPrice", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String updateExaminationprice(@RequestBody PregledDTO examinationDTO) {
		adminService.updateExaminationPrice(examinationDTO);
		
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
		Optional<Narudzbenica> orderOptional = narudzbenicaRepository.findById(orderId);
		Narudzbenica order;
		if (orderOptional.isPresent()) {
			order = orderOptional.get();
		}
		else {
			return "ERR";
		}
		int numberOfOffers = ponudaRepository.getNumberOfOffersForOrder(orderId);
		if (numberOfOffers != 0 || order.getAdminApoteke().getId() != adminId) {
			return "ERR";
		}
		narudzbenicaRepository.deleteById(orderId);
		
		return "OK";
	}

	@AsAdminApoteke
	@DeleteMapping(value = "/deleteExamination/{examinationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteExamination(@PathVariable("examinationId") int examinationId) {
		adminService.deleteExamination(examinationId);
		
		return "OK";
	}

	@AsAdminApoteke
	@PostMapping(value = "/registerExamination/{dermatologistId}/{pharmacyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerExamination(@RequestBody PregledDTO examinationDTO, @PathVariable("dermatologistId") int dermatologistId, @PathVariable("pharmacyId") int pharmacyId) {
		if (adminService.registerExamination(dermatologistId, pharmacyId, examinationDTO) == null) {
			return "ERR";
		}
		
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
	@PostMapping(value="/registerPromo/{adminId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerPromotion(@RequestBody CenovnikDTO cenovnik, @PathVariable("adminId") int adminId) throws Exception{
		adminService.registerPromotion(cenovnik, adminId, cenovnik.getPromoTekst());

		return "OK";
	}

	@AsAdminApoteke
	@PostMapping(value = "/employDermatologist/{pharmacyId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String employDermatologist(@RequestBody DermatologDTO dermatologistDTO, @PathVariable("pharmacyId") int pharmacyId) {
		if (adminService.employDermatologist(pharmacyId, dermatologistDTO) == null) {
			return "ERR";
		}

		return "OK";
	}

	@AsAdminApoteke
	@GetMapping(value = "/cenovnik/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CenovnikDTO getPricelistForPharmacy(@PathVariable("id") int pharmacyId) {
		CenovnikDTO cenovnikDTO = adminService.findPricelistForPharmacy(pharmacyId);

		return cenovnikDTO;
	}
	
	@AsAdminApoteke
	@GetMapping(value = "/dermatologistsOutsidePharmacy/{pharmacyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> getDermatologistsOutsidePharmacy(@PathVariable("pharmacyId") int pharmacyId) {
		return adminService.getDermatologistsOutsidePharmacy(pharmacyId);
	}

	@AsAdminApoteke
	@DeleteMapping(value = "/removeDermatologist/{pharmacyId}/{dermatologistId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String removeDermatologist(@PathVariable("pharmacyId") int pharmacyId, @PathVariable("dermatologistId") int dermatologistId) {
		if (adminService.scheduledAppointmentsForDermatologist(pharmacyId, dermatologistId).size() != 0) {
			return "ERR";
		}
		adminService.removeDermatologist(pharmacyId, dermatologistId);

		return "OK";
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