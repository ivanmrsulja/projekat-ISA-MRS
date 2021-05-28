package rest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.text.DateFormatSymbols;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rest.domain.AdminApoteke;
import rest.domain.AkcijaPromocija;
import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.Dermatolog;
import rest.domain.Dobavljac;
import rest.domain.DostupanProizvod;
import rest.domain.NaruceniProizvod;
import rest.domain.Narudzbenica;
import rest.domain.Notifikacija;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.StatusNarudzbenice;
import rest.domain.StatusPonude;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;
import rest.domain.Zahtjev;
import rest.domain.Zaposlenje;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DermatologDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.IzvestajValueDTO;
import rest.dto.NaruceniProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.NotifikacijaDTO;
import rest.dto.PonudaDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DermatologRepository;
import rest.repository.DobavljacRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.EReceptRepository;
import rest.repository.KorisnikRepository;
import rest.repository.NarudzbenicaRepozitory;
import rest.repository.NotifikacijaRepository;
import rest.repository.PacijentRepository;
import rest.repository.PonudaRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.RezervacijaRepository;
import rest.repository.ZahtevRepository;
import rest.repository.ZaposlenjeRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	private PonudaRepository ponudaRepository;
	private NarudzbenicaRepozitory narudzbenicaRepository;
	private DobavljacRepository dobavljacRepository;
	private ApotekeRepository apotekeRepository;
	private CenaRepository cenaRepository;
	private DostupanProizvodRepository dostupanProizvodRepository;
	private PreparatRepository preparatRepository;
	private AdminApotekeRepository adminApotekeRepository;
	private AkcijaPromocijaService akcijaPromocijaService;
	private PacijentRepository pacijentRepository;
	private PregledRepository pregledRepository;
	private RezervacijaRepository rezervacijaRepository;
	private NotifikacijaRepository notifikacijaRepository;
	private KorisnikRepository korisnikRepository;
	private ZaposlenjeRepository zaposlenjeRepository;
	private ZahtevRepository zahtevRepository;
	private EReceptRepository ereceptRepository;
	private DermatologRepository dermatologRepository;

	private Environment env;
	private JavaMailSender javaMailSender;
	
	@Autowired
	public AdminServiceImpl(PonudaRepository imar, Environment env, JavaMailSender jms, NarudzbenicaRepozitory nr, DobavljacRepository dr, ApotekeRepository ar, 
			CenaRepository cr, DostupanProizvodRepository dpr, PreparatRepository pr, AdminApotekeRepository aar, AkcijaPromocijaService aps, PacijentRepository pacRepo,
			PregledRepository pregledRepo, RezervacijaRepository rezervacijaRepo, NotifikacijaRepository notifikacijaRepo, KorisnikRepository korisnikRepo,
			ZaposlenjeRepository zaposlenjeRepo, ZahtevRepository zahtevRepo, EReceptRepository erecepRepo, DermatologRepository dermatologRepo) {
		this.ponudaRepository = imar;
		this.env = env;
		this.javaMailSender = jms;
		this.narudzbenicaRepository = nr;
		this.dobavljacRepository = dr;
		this.apotekeRepository = ar;
		this.cenaRepository = cr;
		this.dostupanProizvodRepository = dpr;
		this.preparatRepository = pr;
		this.adminApotekeRepository = aar;
		this.akcijaPromocijaService = aps;
		this.pacijentRepository = pacRepo;
		this.pregledRepository = pregledRepo;
		this.rezervacijaRepository = rezervacijaRepo;
		this.notifikacijaRepository = notifikacijaRepo;
		this.korisnikRepository = korisnikRepo;
		this.zaposlenjeRepository = zaposlenjeRepo;
		this.zahtevRepository = zahtevRepo;
		this.ereceptRepository = erecepRepo;
		this.dermatologRepository = dermatologRepo;
	}
	
	public String getMonthName(int month) {
		return new DateFormatSymbols().getMonths()[month - 1];
	}

	@Override
	public Collection<Ponuda> findAllOffers() {
		Collection<Ponuda> offers = ponudaRepository.findAll();
		return offers;
	}

	@Override
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, String text) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(pacijent.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Nove akcije i promocije apoteke " + apoteka.getNaziv());
        String teloString = text;
        mail.setText(teloString);
        javaMailSender.send(mail);
	}

	@Override
	public ArrayList<NarudzbenicaDTO> findOrdersForPharmacy(int idAdmina) {
		Collection<Narudzbenica> narudzbenice = narudzbenicaRepository.getAllForPharmacy(idAdmina);
		ArrayList<NarudzbenicaDTO> narudzbeniceDTO = new ArrayList<NarudzbenicaDTO>();
		for (Narudzbenica n : narudzbenice) {
			narudzbeniceDTO.add(new NarudzbenicaDTO(n));
		}

		return narudzbeniceDTO;
	}

	@Override
	public void notifySuppliersViaEmail(Collection<PonudaDTO> offers) {
		for (PonudaDTO offer : offers) {
			Dobavljac supplier = dobavljacRepository.getSupplierByUsername(offer.getDobavljac());
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(supplier.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Izmena statusa ponude " + Integer.toString(offer.getId()));
			String teloString = "Postovani,\n\nVasa ponuda je ";
			teloString += (offer.getStatus() == StatusPonude.ODBIJENA) ? "odbijena." : "prihvacena.";
			mail.setText(teloString);
			javaMailSender.send(mail);
		}
	}

	@Override
	public void registerPricelist(CenovnikDTO cenovnikDTO, int idApoteke) throws Exception {
		Apoteka apoteka = apotekeRepository.findById(idApoteke).get();
		Cena cenovnik = new Cena();
		cenovnik.setApoteka(apoteka);
		for (DostupanProizvodDTO dpDTO : cenovnikDTO.getDostupniProizvodi()) {
			Preparat p = preparatRepository.getPreparatByName(dpDTO.getPreparat());
			DostupanProizvod dp = new DostupanProizvod(dpDTO.getKolicina(), dpDTO.getCena(), p);
			cenovnik.getDostupniProizvodi().add(dp);
			this.dostupanProizvodRepository.save(dp);
		}

		cenovnik.setPocetakVazenja(cenovnikDTO.getPocetakVazenja());
		cenovnik.setKrajVazenja(cenovnikDTO.getKrajVazenja());
		cenaRepository.save(cenovnik);
	}

	@Override
	public void updateStocks(int orderId, int adminId) {
		Apoteka apoteka  = adminApotekeRepository.getApoteka(adminId);
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(apoteka.getId(), LocalDate.now());
		Collection<NaruceniProizvod> naruceniProizvodi = narudzbenicaRepository.getItemsOfOrder(orderId);
		System.out.println("velicina: " + naruceniProizvodi.size());
		for (DostupanProizvod dp : cenovnik.getDostupniProizvodi()) {
			for (NaruceniProizvod np : naruceniProizvodi) {
				if (dp.getPreparat().getId().equals(np.getPreparat().getId())) {
					System.out.println("DP kolicina + NP kolicina: " + dp.getKolicina() + " + " + np.getKolicina());
					dp.setKolicina(dp.getKolicina() + np.getKolicina());
					break;
				}
			}
		}

		cenaRepository.save(cenovnik);
	}

	@Override
	public CenovnikDTO findPricelistForPharmacy(int pharmacyId) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(pharmacyId, LocalDate.now());
		if (cenovnik == null) {
			cenovnik = new Cena();
			Apoteka apoteka = apotekeRepository.findById(pharmacyId).get();
			cenovnik.setApoteka(apoteka);
			cenovnik.setPocetakVazenja(LocalDate.now());
			cenaRepository.save(cenovnik);
		}
		CenovnikDTO cenovnikDTO = new CenovnikDTO(cenovnik);

		return cenovnikDTO;
	}

	@Override
	public void registerPromotion(CenovnikDTO pricelistDTO, int adminId, String text) throws Exception {
		AdminApoteke admin = adminApotekeRepository.findById(adminId).get();
		AkcijaPromocija ap = new AkcijaPromocija(text, admin);
		akcijaPromocijaService.create(ap);

		Apoteka apoteka = adminApotekeRepository.getApoteka(admin.getId());
		ApotekaDTO apotekaDTO = new ApotekaDTO(apoteka);

		pricelistDTO.setPocetakVazenja(LocalDate.now());
		if (pricelistDTO.getKrajVazenja().compareTo(pricelistDTO.getPocetakVazenja()) <= 0) {
			throw new Exception("Pogresna vrednost datuma isteka promocije.");
		}
		
		registerPricelist(pricelistDTO, apoteka.getId());

		Collection<Pacijent> pretplaceniPacijenti = pacijentRepository.getPatientsSubscribedToPharmacy(apotekaDTO.getId());
		for (Pacijent p : pretplaceniPacijenti) {
			notifyPatientViaEmail(apotekaDTO, p, text);
		}
	}

	@Override
	public ArrayList<PonudaDTO> getOffersForPharmacy(int orderId) {
		Collection<Ponuda> offers = ponudaRepository.getOffersForOrder(orderId);
		ArrayList<PonudaDTO> offersDTO = new ArrayList<PonudaDTO>();
		for (Ponuda p : offers) {
			offersDTO.add(new PonudaDTO(p));
		}

		return offersDTO;
	}

	@Override
	public void deleteProductFromPharmacy(int productId, int pharmacyId) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(pharmacyId, LocalDate.now());
		DostupanProizvod dpToDelete = null;
		for (DostupanProizvod dp : cenovnik.getDostupniProizvodi()) {
			if (dp.getId().equals(productId)) {
				dpToDelete = dp;
			}
		}
		cenovnik.getDostupniProizvodi().remove(dpToDelete);
		cenaRepository.save(cenovnik);
		dostupanProizvodRepository.deleteById(productId);
	}

	@Override
	public ArrayList<DostupanProizvodDTO> getProductsForPharmacy(int pharmacyId) {
		Collection<DostupanProizvod> availablePharmacyProducts = dostupanProizvodRepository.getForPharmacy(pharmacyId);
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = new ArrayList<DostupanProizvodDTO>();
		
		for (DostupanProizvod dp : availablePharmacyProducts) {
			availablePharmacyProductsDTO.add(new DostupanProizvodDTO(dp));
		}

		return availablePharmacyProductsDTO;
	}

	@Override
	public void updateStatusOfOrder(int orderId) {
		Narudzbenica n = narudzbenicaRepository.findById(orderId).get();
		n.setStatus(StatusNarudzbenice.OBRADJENA);
		narudzbenicaRepository.save(n);
	}

	@Override
	public void updateStatusOfOffers(Collection<PonudaDTO> offers, int orderId, int offerId) {
		ponudaRepository.updateOffersStatus(orderId);
		Ponuda p = ponudaRepository.findById(offerId).get();
		p.setStatus(StatusPonude.PRIHVACENA);
		ponudaRepository.save(p);

		notifySuppliersViaEmail(offers);
	}

	@Override
	public void addProductToPharmacy(PreparatDTO preparat, int pharmacyId, double price) {
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(pharmacyId, LocalDate.now());
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
	}

	@Override
	public void registerOrder(NarudzbenicaDTO narudzbenicaDTO, int adminId) {
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
	}

	@Override
	public ArrayList<PreparatDTO> getProductsOutsidePharmacy(int pharmacyId) {
		Collection<Preparat> preparati = dostupanProizvodRepository.getProductsOutsidePharmacy(pharmacyId);
		ArrayList<PreparatDTO> preparatiDTO = new ArrayList<>();
		for (Preparat p : preparati) {
			preparatiDTO.add(new PreparatDTO(p));
		}

		return preparatiDTO;
	}

	@Override
	public ArrayList<DostupanProizvodDTO> searhProductsOfPharmacy(int pharmacyId, String name) {
		Collection<DostupanProizvod> availablePharmacyProducts = dostupanProizvodRepository.getForPharmacy(pharmacyId);
		ArrayList<DostupanProizvodDTO> availablePharmacyProductsDTO = new ArrayList<DostupanProizvodDTO>();
		
		for (DostupanProizvod dp : availablePharmacyProducts) {
			if (dp.getPreparat().getNaziv().contains(name))
				availablePharmacyProductsDTO.add(new DostupanProizvodDTO(dp));
		}

		return availablePharmacyProductsDTO;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getYearlyExaminations(int year, int pharmacyId) {
		ArrayList<IzvestajValueDTO> yearlyExaminations = new ArrayList<>();
		LocalDate ld_low = LocalDate.ofYearDay(year, 1);
		LocalDate ld_high = LocalDate.ofYearDay(year, 365);
		ArrayList<Object[]> examinations = pregledRepository.getExaminationsPerMonth(ld_low, ld_high, pharmacyId);

		for (int currentMonth = 1; currentMonth <= 12; ++currentMonth) {
			boolean match = false;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					match = true;
					yearlyExaminations.add(new IzvestajValueDTO(getMonthName(currentMonth), Integer.parseInt(obj[1].toString())));
					break;
				}
			}
			if (!match) {
				yearlyExaminations.add(new IzvestajValueDTO(getMonthName(currentMonth), 0));
			}
		}

		return yearlyExaminations;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getQuarterlyExaminations(int year, int quarter, int pharmacyId) {
		ArrayList<IzvestajValueDTO> quarterlyExaminations = new ArrayList<>();
		LocalDate ld_low = null;
		LocalDate ld_high = null;

		switch (quarter) {
		case 1:
			ld_low = LocalDate.of(year, 1, 1);
			ld_high = LocalDate.of(year, 3, 31);
			break;
		case 2:
			ld_low = LocalDate.of(year, 4, 1);
			ld_high = LocalDate.of(year, 6, 30);
			break;
		case 3:
			ld_low = LocalDate.of(year, 7, 1);
			ld_high = LocalDate.of(year, 9, 30);
			break;
		case 4:
			ld_low = LocalDate.of(year, 10, 1);
			ld_high = LocalDate.of(year, 12, 31);
		}

		ArrayList<Object[]> examinations = pregledRepository.getExaminationsPerMonth(ld_low, ld_high, pharmacyId);

		int currentMonth = (quarter - 1) * 3 + 1;
		int lastMonth = quarter * 3;
		for (; currentMonth <= lastMonth; ++currentMonth) {
			boolean match = false;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					match = true;
					quarterlyExaminations.add(new IzvestajValueDTO(getMonthName(currentMonth), Integer.parseInt(obj[1].toString())));
					break;
				}
			}
			if (!match) {
				quarterlyExaminations.add(new IzvestajValueDTO(getMonthName(currentMonth), 0));
			}
		}

		return quarterlyExaminations;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getMonthlyExaminations(int year, int month, int pharmacyId) {
		ArrayList<IzvestajValueDTO> monthlyExaminations = new ArrayList<>();

		int days = 0;
		switch (month) {
		case 1:
			days = 31;
			break;
		case 2:
			days = 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		}

		LocalDate ld_low = LocalDate.of(year, month, 1);
		LocalDate ld_high = LocalDate.of(year, month, days);

		ArrayList<Object[]> examinations = pregledRepository.getExaminationsForMonth(ld_low, ld_high, pharmacyId);

		for (int i = 1; i <= days; ++i) {
			boolean match = false;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					match = true;
					monthlyExaminations.add(new IzvestajValueDTO(Integer.toString(i), Integer.parseInt(obj[1].toString())));
					break;
				}
			}
			if (!match) {
				monthlyExaminations.add(new IzvestajValueDTO(Integer.toString(i), 0));
			}
		}

		return monthlyExaminations;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getYearlyIncome(int year, int pharmacyId) {
		ArrayList<IzvestajValueDTO> yearlyIncomes = new ArrayList<>();
		LocalDate ld_low = LocalDate.ofYearDay(year, 1);
		LocalDate ld_high = LocalDate.ofYearDay(year, 365);
		ArrayList<Object[]> examinations = pregledRepository.getIncomeFromExaminationsPerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> reservations = rezervacijaRepository.getIncomeFromReservationsPerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti = ereceptRepository.getProcessedIncomePerMonth(ld_low, ld_high, pharmacyId, LocalDate.now());

		for (int currentMonth = 1; currentMonth <= 12; ++currentMonth) {
			int cena = 0;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena2 = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena3 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena3 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			yearlyIncomes.add(new IzvestajValueDTO(getMonthName(currentMonth), cena + cena2 + cena3));
		}

		return yearlyIncomes;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getQuarterlyIncome(int year, int quarter, int pharmacyId) {
		ArrayList<IzvestajValueDTO> quarterlyIncome = new ArrayList<>();
		LocalDate ld_low = null;
		LocalDate ld_high = null;

		switch (quarter) {
		case 1:
			ld_low = LocalDate.of(year, 1, 1);
			ld_high = LocalDate.of(year, 3, 31);
			break;
		case 2:
			ld_low = LocalDate.of(year, 4, 1);
			ld_high = LocalDate.of(year, 6, 30);
			break;
		case 3:
			ld_low = LocalDate.of(year, 7, 1);
			ld_high = LocalDate.of(year, 9, 30);
			break;
		case 4:
			ld_low = LocalDate.of(year, 10, 1);
			ld_high = LocalDate.of(year, 12, 31);
		}

		ArrayList<Object[]> examinations = pregledRepository.getIncomeFromExaminationsPerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> reservations = rezervacijaRepository.getIncomeFromReservationsPerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti = ereceptRepository.getProcessedIncomePerMonth(ld_low, ld_high, pharmacyId, LocalDate.now());

		int currentMonth = (quarter - 1) * 3 + 1;
		int lastMonth = quarter * 3;
		for (; currentMonth <= lastMonth; ++currentMonth) {
			int cena = 0;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena2 = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena3 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					cena3 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			quarterlyIncome.add(new IzvestajValueDTO(getMonthName(currentMonth), cena + cena2 + cena3));
		}

		return quarterlyIncome;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getMonthlyIncome(int year, int month, int pharmacyId) {
		ArrayList<IzvestajValueDTO> monthlyIncomes = new ArrayList<>();

		int days = 0;
		switch (month) {
		case 1:
			days = 31;
			break;
		case 2:
			days = 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		}

		LocalDate ld_low = LocalDate.of(year, month, 1);
		LocalDate ld_high = LocalDate.of(year, month, days);

		ArrayList<Object[]> examinations = pregledRepository.getIncomeFromExaminationsForMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> reservations = rezervacijaRepository.getIncomeFromReservationsForMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti = ereceptRepository.getProcessedIncomeForMonth(ld_low, ld_high, pharmacyId, LocalDate.now());

		for (int i = 1; i <= days; ++i) {
			int cena = 0;
			for (Object[] obj : examinations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					cena = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena2 = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					cena2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int cena3 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					cena3 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			monthlyIncomes.add(new IzvestajValueDTO(Integer.toString(i), cena + cena2 + cena3));
		}

		return monthlyIncomes;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getYearlyUsage(int year, int pharmacyId) {
		ArrayList<IzvestajValueDTO> yearlyUsage = new ArrayList<>();
		LocalDate ld_low = LocalDate.ofYearDay(year, 1);
		LocalDate ld_high = LocalDate.ofYearDay(year, 365);

		ArrayList<Object[]> reservations = rezervacijaRepository.getDrugsUsagePerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti = ereceptRepository.getProcessedUsagePerMonth(ld_low, ld_high, pharmacyId);

		for (int currentMonth = 1; currentMonth <= 12; ++currentMonth) {
			int kolicina = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					kolicina = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int kolicina2 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					kolicina2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			yearlyUsage.add(new IzvestajValueDTO(getMonthName(currentMonth), kolicina + kolicina2));
		}

		return yearlyUsage;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getQuarterlyUsage(int year, int quarter, int pharmacyId) {
		ArrayList<IzvestajValueDTO> quarterlyUsage = new ArrayList<>();
		LocalDate ld_low = null;
		LocalDate ld_high = null;

		switch (quarter) {
		case 1:
			ld_low = LocalDate.of(year, 1, 1);
			ld_high = LocalDate.of(year, 3, 31);
			break;
		case 2:
			ld_low = LocalDate.of(year, 4, 1);
			ld_high = LocalDate.of(year, 6, 30);
			break;
		case 3:
			ld_low = LocalDate.of(year, 7, 1);
			ld_high = LocalDate.of(year, 9, 30);
			break;
		case 4:
			ld_low = LocalDate.of(year, 10, 1);
			ld_high = LocalDate.of(year, 12, 31);
		}

		ArrayList<Object[]> reservations = rezervacijaRepository.getDrugsUsagePerMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti = ereceptRepository.getProcessedUsagePerMonth(ld_low, ld_high, pharmacyId);

		int currentMonth = (quarter - 1) * 3 + 1;
		int lastMonth = quarter * 3;
		for (; currentMonth <= lastMonth; ++currentMonth) {
			int kolicina = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					kolicina = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int kolicina2 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				int month = date.getMonthValue();
				if (currentMonth == month) {
					kolicina2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			quarterlyUsage.add(new IzvestajValueDTO(getMonthName(currentMonth), kolicina + kolicina2));
		}

		return quarterlyUsage;
	}

	@Override
	public ArrayList<IzvestajValueDTO> getMonthlyUsage(int year, int month, int pharmacyId) {
		ArrayList<IzvestajValueDTO> monthlyUsage = new ArrayList<>();

		int days = 0;
		switch (month) {
		case 1:
			days = 31;
			break;
		case 2:
			days = 28;
			break;
		case 3:
			days = 31;
			break;
		case 4:
			days = 30;
			break;
		case 5:
			days = 31;
			break;
		case 6:
			days = 30;
			break;
		case 7:
			days = 31;
			break;
		case 8:
			days = 31;
			break;
		case 9:
			days = 30;
			break;
		case 10:
			days = 31;
			break;
		case 11:
			days = 30;
			break;
		case 12:
			days = 31;
			break;
		}

		LocalDate ld_low = LocalDate.of(year, month, 1);
		LocalDate ld_high = LocalDate.of(year, month, days);

		ArrayList<Object[]> reservations = rezervacijaRepository.getDrugsUsageForMonth(ld_low, ld_high, pharmacyId);
		ArrayList<Object[]> erecepti=  ereceptRepository.getProcessedUsageForMonth(ld_low, ld_high, pharmacyId);

		for (int i = 1; i <= days; ++i) {
			int kolicina = 0;
			for (Object[] obj : reservations) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					kolicina = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			int kolicina2 = 0;
			for (Object[] obj : erecepti) {
				LocalDate date = LocalDate.parse(obj[0].toString().substring(0, 10));
				LocalDate temp_ld = LocalDate.of(year, month, i);
				if (date.equals(temp_ld)) {
					kolicina2 = (int) Double.parseDouble(obj[1].toString());
					break;
				}
			}
			monthlyUsage.add(new IzvestajValueDTO(Integer.toString(i), kolicina + kolicina2));
		}

		return monthlyUsage;
	}

	@Override
	public ArrayList<NotifikacijaDTO> getNotificationsForPharmacy(int pharmacyId) {
		ArrayList<Notifikacija> notifications = notifikacijaRepository.getAllForPharmacy(pharmacyId);
		ArrayList<NotifikacijaDTO> notificationsDTO = new ArrayList<>();

		for (Notifikacija n : notifications) {
			notificationsDTO.add(new NotifikacijaDTO(n));
		}

		return notificationsDTO;
	}

	@Override
	public void updatePharmacyNotifications(int pharmacyId) {
		notifikacijaRepository.updateNotificationsStatusesForPharmacy(pharmacyId);
	}

	@Override
	public ArrayList<PregledDTO> getOpenExaminationsForPharmacy(int pharmacyId) {
		Collection<Pregled> examinations = pregledRepository.getOpenExaminationsForPharmacy(pharmacyId);
		ArrayList<PregledDTO> examinationsDTO = new ArrayList<>();

		for (Pregled p : examinations) {
			examinationsDTO.add(new PregledDTO(p, 0));
		}

		return examinationsDTO;
	}

	@Override
	public void updateExaminationPrice(PregledDTO examinationDTO) {
		pregledRepository.updateExaminationPrice(examinationDTO.getId(), examinationDTO.getCijena());
	}

	@Override
	public void deleteExamination(int examinationId) {
		pregledRepository.deleteById(examinationId);
	}

	@Override
	public Pregled registerExamination(int dermatologistId, int pharmacyId, PregledDTO examinationDTO) {

		// provera poklapanja sa trenutnim datumom/vremenom
		{
		int dateDifference = LocalDate.now().compareTo(examinationDTO.getDatum());
		if (dateDifference > 0) {
			return null;
		} else if (dateDifference == 0) {
			int timeDifference = LocalTime.now().compareTo(examinationDTO.getVrijeme());
			if (timeDifference > 0) {
				return null;
			}
		}
		}

		// provera poklapanja vremena termina sa radnim vremenom
		{
		Zaposlenje zaposlenje = zaposlenjeRepository.zaposlenjeZaStrucnjaka(dermatologistId, pharmacyId);

		int startTimeDifference = zaposlenje.getPocetakRadnogVremena().compareTo(examinationDTO.getVrijeme());
		int endTimeDifference = zaposlenje.getKrajRadnogVremena().compareTo(examinationDTO.getVrijeme().plusMinutes(examinationDTO.getTrajanje()));

		if (startTimeDifference > 0 || endTimeDifference < 0) {
			return null;
		}
		}

		// provera poklapanja sa drugim zakazanim/slobodnim terminima
		{
		Collection<Pregled> pregledi = pregledRepository.getScheduledAndOpenExaminations(dermatologistId, pharmacyId);
		if (pregledi.size() != 0) {
			for (Pregled p : pregledi) {
				int dateDifference = p.getDatum().compareTo(examinationDTO.getDatum());
				if (dateDifference == 0) {
					// > 0 ako je pocetak naseg termina pre pocetka trenutnog
					int startStartDifference = p.getVrijeme().compareTo(examinationDTO.getVrijeme());
					// > 0 ako je kraj naseg termina pre kraja trenutnog
					int endEndDifference = p.getVrijeme().plusMinutes(p.getTrajanje()).compareTo(examinationDTO.getVrijeme().plusMinutes(examinationDTO.getTrajanje()));
					// > 0 ako je kraj naseg termina pre pocetka trenutnog
					int startEndDifference = p.getVrijeme().compareTo(examinationDTO.getVrijeme().plusMinutes(examinationDTO.getTrajanje()));
					// > 0 ako je pocetak naseg termina pre kraja trenutnog
					int endStartDifference = p.getVrijeme().plusMinutes(p.getTrajanje()).compareTo(examinationDTO.getVrijeme());
					boolean startInBetween = startStartDifference < 0 && endStartDifference > 0;
					boolean endInBetween = startEndDifference < 0 && endEndDifference > 0;
					boolean wrapping = startStartDifference > 0 && endEndDifference < 0;
					boolean sameStart = startStartDifference == 0;
					if (startInBetween || endInBetween || wrapping || sameStart) {
						return null;
					}
				}
			}
		}
		}
		
		// provera da dermatolog nije na godisnjem/odsustvu
		{
		Collection<Zahtjev> zahtevi = zahtevRepository.getAcceptedAndPendingForUser(dermatologistId);
		if (zahtevi.size() != 0) {
			for (Zahtjev z : zahtevi) {
				int startDateDifference = z.getPocetak().compareTo(examinationDTO.getDatum());
				int endDateDifference = z.getKraj().compareTo(examinationDTO.getDatum());
				if (startDateDifference < 0 && endDateDifference > 0) {
					return null;
				}
			}
		}
		}

		Pregled examination = new Pregled();
		examination.setIzvjestaj("");
		examination.setTip(TipPregleda.PREGLED);
		examination.setStatus(StatusPregleda.SLOBODAN);
		examination.setApoteka(apotekeRepository.findById(pharmacyId).get());
		examination.setZaposleni(korisnikRepository.findById(dermatologistId).get());
		examination.setDatum(examinationDTO.getDatum());
		examination.setVrijeme(examinationDTO.getVrijeme());
		examination.setTrajanje(examinationDTO.getTrajanje());
		examination.setCijena(examinationDTO.getCijena());

		pregledRepository.save(examination);

		return examination;
	}

	@Override
	public void deleteOutdatedPromotion() {
		cenaRepository.deleteOutdatedPromotion(LocalDate.now());
	}

	@Override
	public Zaposlenje employDermatologist(int pharmacyId, DermatologDTO dermatologistDTO) {
		Collection<Zaposlenje> employments = zaposlenjeRepository.getEmploymentsForDermatologist(dermatologistDTO.getId());

		for (Zaposlenje z : employments) {
			// > 0 ako je pocetak naseg termina pre pocetka trenutnog
			int startStartDifference = z.getPocetakRadnogVremena().compareTo(dermatologistDTO.getPocetakRadnogVremena());
			// > 0 ako je kraj naseg termina pre kraja trenutnog
			int endEndDifference = z.getKrajRadnogVremena().compareTo(dermatologistDTO.getKrajRadnogVremena());
			// > 0 ako je kraj naseg termina pre pocetka trenutnog
			int startEndDifference = z.getPocetakRadnogVremena().compareTo(dermatologistDTO.getKrajRadnogVremena());
			// > 0 ako je pocetak naseg termina pre kraja trenutnog
			int endStartDifference = z.getKrajRadnogVremena().compareTo(dermatologistDTO.getPocetakRadnogVremena());
			boolean startInBetween = startStartDifference < 0 && endStartDifference > 0;
			boolean endInBetween = startEndDifference < 0 && endEndDifference > 0;
			boolean wrapping = startStartDifference > 0 && endEndDifference < 0;
			if (startInBetween || endInBetween || wrapping) {
				return null;
			}
		}

		Apoteka a = apotekeRepository.findById(pharmacyId).get();
		Dermatolog d = dermatologRepository.findById(dermatologistDTO.getId()).get();
		Zaposlenje z = new Zaposlenje(dermatologistDTO.getPocetakRadnogVremena(), dermatologistDTO.getKrajRadnogVremena(), a, d);
		d.addZaposlenje(z);
		zaposlenjeRepository.save(z);
		dermatologRepository.save(d);

		return z;
	}

	@Override
	public ArrayList<DermatologDTO> getDermatologistsOutsidePharmacy(int pharmacyId) {
		ArrayList<Dermatolog> dermatologists = dermatologRepository.getDermatologistsOutsidePharmacy(pharmacyId);
		ArrayList<DermatologDTO> dermatologistsDTO = new ArrayList<>();

		for (Dermatolog d : dermatologists) {
			dermatologistsDTO.add(new DermatologDTO(d));
		}

		return dermatologistsDTO;
	}

	@Override
	public Collection<Pregled> scheduledAppointmentsForDermatologist(int pharmacyId, int dermatologistId) {
		Collection<Pregled> pregledi = pregledRepository.getScheduledAppointments(dermatologistId, pharmacyId, LocalDate.now());

		return pregledi;
	}

	@Override
	public void removeDermatologist(int pharmacyId, int dermatologistId) {
		notifikacijaRepository.deleteNotificationsOfUser(dermatologistId);
		Dermatolog d = dermatologRepository.getDermatologistWithEmployments(dermatologistId);
		Zaposlenje z = zaposlenjeRepository.getEmploymentForDermatologist(pharmacyId, dermatologistId);
		int zaposlenjeId = z.getId();
		d.removeZaposlenje(z);
		dermatologRepository.save(d);
		zaposlenjeRepository.save(z);
		zaposlenjeRepository.deleteForDermatologist(zaposlenjeId);
		pregledRepository.deleteOpenAppointmentsForDermatologistForPharmacy(pharmacyId, dermatologistId);
	}

}
