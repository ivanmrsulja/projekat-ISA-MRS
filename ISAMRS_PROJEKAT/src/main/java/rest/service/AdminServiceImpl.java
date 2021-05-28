package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.Dobavljac;
import rest.domain.DostupanProizvod;
import rest.domain.NaruceniProizvod;
import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.Preparat;
import rest.domain.StatusNarudzbenice;
import rest.domain.StatusPonude;
import rest.domain.TeloAkcijePromocije;
import rest.domain.TipKorisnika;
import rest.domain.Zalba;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.dto.TipKorisnikaDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DobavljacRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.NarudzbenicaRepozitory;
import rest.repository.PonudaRepository;
import rest.repository.PreparatRepository;
import rest.repository.TipKorisnikaRepository;
import rest.repository.ZalbaRepository;

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
	private ZalbaRepository zalbaRepository;
	private TipKorisnikaRepository tipRepository;

	private Environment env;
	private JavaMailSender javaMailSender;
	
	@Autowired
	public AdminServiceImpl(TipKorisnikaRepository tkre,ZalbaRepository zalre,PonudaRepository imar, Environment env, JavaMailSender jms, NarudzbenicaRepozitory nr, DobavljacRepository dr, ApotekeRepository ar, 
			CenaRepository cr, DostupanProizvodRepository dpr, PreparatRepository pr, AdminApotekeRepository aar) {
		this.ponudaRepository = imar;
		this.tipRepository = tkre;
		this.zalbaRepository = zalre;
		this.env = env;
		this.javaMailSender = jms;
		this.narudzbenicaRepository = nr;
		this.dobavljacRepository = dr;
		this.apotekeRepository = ar;
		this.cenaRepository = cr;
		this.dostupanProizvodRepository = dpr;
		this.preparatRepository = pr;
		this.adminApotekeRepository = aar;
	}

	@Override
	public Collection<Ponuda> findAllOffers() {
		Collection<Ponuda> offers = ponudaRepository.findAll();
		return offers;
	}

	@Override
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(pacijent.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Nove akcije i promocije apoteke " + apoteka.getNaziv());
        String teloString = telo.getTekst();
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
		cenaRepository.save(cenovnik);
	}

	@Override
	public void updateStocks(int orderId, int adminId) {
		Apoteka apoteka  = adminApotekeRepository.getApoteka(adminId);
		Cena cenovnik = cenaRepository.getLatestPricelistForPharmacy(apoteka.getId());
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
	public void updateZalba(int id) {
		Zalba z = zalbaRepository.findById(id).get();
		z.setAnswered(true);
		zalbaRepository.save(z);
		
	}

	@Override
	public void createType(TipKorisnikaDTO t) {
		TipKorisnika tk = new TipKorisnika();
		tk.setBodovi(t.getBodovi());
		tk.setNaziv(t.getNaziv());
		tk.setPopust(t.getPopust());
		tipRepository.save(tk);
		
	}

	@Override
	public Collection<NarudzbenicaDTO> getAvailableNarudzbenice(int id) {
		 Collection<Narudzbenica> nars = narudzbenicaRepository.getAllWithProizvodi();
		 System.out.println("THE DUZINA OVDE JE " + nars.size());
		 Collection<NarudzbenicaDTO> newnar = new ArrayList<NarudzbenicaDTO>();
		 for (Narudzbenica narudzbenica : nars) {
			System.out.println("OVO JE NARUDZBENICA SA ID " +narudzbenica.getId());
			boolean foundDob = false;
			if(narudzbenica.getStatus().equals(StatusNarudzbenice.CEKA_PONUDE)) {
				for (Ponuda pon : narudzbenica.getPonude()) {
					if(pon.getDobavljac().getId() == id) {
						foundDob = true;
						break;
					}
				}
				if(!foundDob) {
					NarudzbenicaDTO nar = new NarudzbenicaDTO(narudzbenica);
					newnar.add(nar);
					
				}
				
			}
		}
		 return newnar;
		
	}

		
}
