package rest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import rest.domain.Apoteka;
import rest.domain.Cena;
import rest.domain.Dermatolog;
import rest.domain.DostupanProizvod;
import rest.domain.ERecept;
import rest.domain.Farmaceut;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.StatusERecepta;
import rest.domain.StatusPregleda;
import rest.domain.StavkaRecepta;
import rest.domain.TipKorisnika;
import rest.domain.TipPregleda;
import rest.dto.ApotekaDTO;
import rest.dto.LekProdajaDTO;
import rest.dto.PregledDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DermatologRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.EReceptRepository;
import rest.repository.FarmaceutRepository;
import rest.repository.LokacijaRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.StavkaReceptaRepository;
import rest.repository.TipKorisnikaRepository;
import rest.repository.ZaposlenjeRepository;
import rest.util.ApotekaSearchParams;

@Service
@Transactional
public class ApotekaServiceImpl implements ApotekaService {

	private ApotekeRepository apoteke;
	private AdminApotekeRepository admin;
	private DermatologRepository dermatolozi;
	private ZaposlenjeRepository zaposlenja;
	private PregledRepository pregledi;
	private FarmaceutRepository farmaceuti;
	private PacijentRepository pacijenti;
	private PenalRepository penali;
	private LokacijaRepository lokacije;
	private CenaRepository cene;
	private DostupanProizvodRepository dostupniproizvodi;
	private PreparatRepository lekovi;
	private TipKorisnikaRepository tipovi;
	private JavaMailSender javaMailSender;
	private Environment env;
	private StavkaReceptaRepository stavkerecepta;
	private EReceptRepository erecepti;
	
	private static final int pageSize = 10;

	@Autowired
	public ApotekaServiceImpl(EReceptRepository ercrps,StavkaReceptaRepository stvrsa,Environment e,JavaMailSender jms,TipKorisnikaRepository tips,PreparatRepository prepires,DostupanProizvodRepository dpsra,CenaRepository c,LokacijaRepository lr,ApotekeRepository ar, AdminApotekeRepository are, DermatologRepository dr, ZaposlenjeRepository zaposlenja, PregledRepository pregledi, FarmaceutRepository farmaceuti, PacijentRepository pacijenti, PenalRepository penali) {
		apoteke = ar;
		env = e;
		javaMailSender = jms;
		tipovi = tips;
		lekovi = prepires;
		erecepti = ercrps;
		cene = c;
		stavkerecepta = stvrsa;
		lokacije = lr;
		admin = are;
		dostupniproizvodi = dpsra;
		dermatolozi = dr;
		this.zaposlenja = zaposlenja;
		this.pregledi = pregledi;
		this.farmaceuti = farmaceuti;
		this.pacijenti = pacijenti;
		this.penali = penali;
	}
	
	@Override
	public Page<ApotekaDTO> getAllDrugStores(int stranica, ApotekaSearchParams params, double lat, double lon) {
		Direction dir;
		if(params.isOpadajuce()) {
			dir = Direction.DESC;
		}else {
			dir = Direction.ASC;
		}
		System.out.println(lat + "-" + lon);
		Page<ApotekaDTO> allStores = apoteke.findAll(new PageRequest(stranica, pageSize, dir, params.getKriterijumSortiranja().toLowerCase()), params.getNaziv().toUpperCase(), params.getAdresa().toUpperCase(), params.getdOcena(), params.getgOcena(), lat, lon, params.getRastojanje()).map(a -> new ApotekaDTO(a));
		return allStores;
	}

	@Override
	public Apoteka getByID(int id) {
		Optional<Apoteka> opt = this.apoteke.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			return null;
		}
	}

	@Override
	public void update(ApotekaDTO apoteka) throws Exception {
		Apoteka apotekaToUpdate = getByID(apoteka.getId());
		if (apotekaToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		apotekaToUpdate.setNaziv(apoteka.getNaziv());
		apotekaToUpdate.setOpis(apoteka.getOpis());
		apotekaToUpdate.setLokacija(apoteka.getLokacija());
		apotekaToUpdate.setCenaSavetovanja(apoteka.getCena());
		apoteke.save(apotekaToUpdate);
	}

	@Override
	public Apoteka getForAdmin(int id) {
		return admin.getApoteka(id);
	}

	@Override
	public Collection<PregledDTO> getPregledi(int id, String criteria) {
		Collection<Pregled> pregledi = apoteke.getPreCreated(id);
		
		ArrayList<PregledDTO> ret = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
			Optional<Dermatolog> dOpt = dermatolozi.findById(p.getZaposleni().getId());
			if(dOpt.isPresent()) {
				ret.add(new PregledDTO(p, dOpt.get().getOcena()));
			}
		}
		
		if(criteria.equals("cena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
							
							@Override
							public int compare(PregledDTO e1, PregledDTO e2) {
								return Double.compare(e1.getCijena(), e2.getCijena());
							}
						});
		}else if(criteria.equals("ocena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
				
				@Override
				public int compare(PregledDTO e1, PregledDTO e2) {
					return Double.compare(e2.getOcena(), e1.getOcena());
				}
			});
		}
		
		return ret;
	}

	@Override
	public Page<ApotekaDTO> apotekeZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, String criteria, int pageNum) throws Exception {
		if(datum.isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(datum.isEqual(LocalDate.now())) {
			if(vrijeme.isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		Collection<Integer> kandidati = zaposlenja.slobodniFarmaceuti(vrijeme);
		ArrayList<Apoteka> apotekeRet = new ArrayList<Apoteka>();
		long trajanjeSavetovanja = 30L * 60000L;
		long mid = vrijeme.getHour() * 3600000L + vrijeme.getMinute() * 60000L;
		for(int id : kandidati) {
			Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(datum, id);
			if(zauzeca.size() == 0) {
				Apoteka a = zaposlenja.apotekaZaFarmaceuta(id);
				if(!apotekeRet.contains(a)) {
					apotekeRet.add(a);
				}
			}
			for(Pregled p: zauzeca) {
				long start = p.getVrijeme().getHour() * 3600000L + p.getVrijeme().getMinute() * 60000L;
				long end = start + p.getTrajanje() * 60000L;
				if(mid > end || mid < start - trajanjeSavetovanja) {
					Apoteka a = zaposlenja.apotekaZaFarmaceuta(id);
					if(!apotekeRet.contains(a)) {
						apotekeRet.add(a);
					}
				}
			}
		}
		
		switch (criteria) {
		case "OCENA":
			Page<ApotekaDTO> ocenaSort = ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, pageSize, Direction.DESC, "ocena"), apotekeRet).map(a -> new ApotekaDTO(a));
			return ocenaSort;

		case "CENA":
			Page<ApotekaDTO> cenaSort = ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, pageSize, Direction.ASC, "cenaSavetovanja"), apotekeRet).map(a -> new ApotekaDTO(a));
			return cenaSort;
		}
		
		return ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, pageSize), apotekeRet).map(a -> new ApotekaDTO(a));
	}

	@Override
	public Collection<Farmaceut> farmaceutiZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, int id, String criteria) throws Exception {
		if(datum.isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(datum.isEqual(LocalDate.now())) {
			if(vrijeme.isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		Collection<Integer> kandidati = zaposlenja.slobodniFarmaceutiApoteka(vrijeme, id);
		ArrayList<Farmaceut> ret = new ArrayList<Farmaceut>();
		long trajanjeSavetovanja = 30L * 60000L;
		long mid = vrijeme.getHour() * 3600000L + vrijeme.getMinute() * 60000L;
		for(int i : kandidati) {
			Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(datum, i);
			if(zauzeca.size() == 0) {
				Optional<Farmaceut> aOpt = farmaceuti.findById(i);
				if(aOpt.isPresent()) {
					Farmaceut a = aOpt.get();
					if(!ret.contains(a)) {
						ret.add(a);
					}
				}
				
			}
			for(Pregled p: zauzeca) {
				long start = p.getVrijeme().getHour() * 3600000L + p.getVrijeme().getMinute() * 60000L;
				long end = start + p.getTrajanje() * 60000L;
				if(mid > end || mid < start - trajanjeSavetovanja) {
					Optional<Farmaceut> aOpt = farmaceuti.findById(i);
					if (aOpt.isPresent()) {
						Farmaceut a = aOpt.get();
						if(!ret.contains(a)) {
							ret.add(a);
						}
					}
				}
			}
		}
		switch (criteria) {
		case "OCENA":
			Collections.sort(ret, new Comparator<Farmaceut>() {

				@Override
				public int compare(Farmaceut a0, Farmaceut a1) {
					return Double.compare(a0.getOcena(), a1.getOcena());
				}
			});
			break;
		}
		return ret;
	}

	@Override
	public Pregled zakaziSavetovanje(PregledDTO podaci, int idApoteke, int idFarmaceuta, int idPacijenta) throws Exception {
		if(podaci.getDatum().isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(podaci.getDatum().isEqual(LocalDate.now())) {
			if(podaci.getVrijeme().isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		Farmaceut f = farmaceuti.findOneById(idFarmaceuta);
		
		Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(podaci.getDatum(), idFarmaceuta);
		long trajanjeSavetovanja = 30L * 60000L;
		long mid = podaci.getVrijeme().getHour() * 3600000L + podaci.getVrijeme().getMinute() * 60000L;
		for(Pregled p: zauzeca) {
			long start = p.getVrijeme().getHour() * 3600000L + p.getVrijeme().getMinute() * 60000L;
			long end = start + p.getTrajanje() * 60000L;
			if(mid <= end && mid >= start - trajanjeSavetovanja) {
				throw new Exception("Farmaceut je zauzet u tom terminu.");
			}
		}
		
		Optional<Pacijent> pOpt = pacijenti.findById(idPacijenta);
		Pacijent p = null;
		if (pOpt.isPresent()) {
			p = pOpt.get();
		}
		else {
			throw new Exception("Pacijent ne postoji.");
		}
		int brojPenala = penali.penalForUser(p.getId()).size();
		if(brojPenala >= 3) {
			throw new Exception("Imate " + brojPenala + " penala, zakazivanja su vam onemogucena do 1. u sledecem mesecu.");
		}
		
		Optional<Apoteka> aOpt = apoteke.findById(idApoteke);
		Apoteka a = null;
		if(aOpt.isPresent()) {
			a= aOpt.get();
		}else {
			throw new Exception("Zeljena apoteka ne postoji.");
		}
		
		Pregled novi = new Pregled("", StatusPregleda.ZAKAZAN, TipPregleda.SAVJETOVANJE, podaci.getDatum(), podaci.getVrijeme(), 30, a.getCenaSavetovanja() * p.getTipKorisnika().getPopust(), f, p, a);
		pregledi.save(novi);
		a.addPregled(novi);
		f.addPregled(novi);
		p.addPregled(novi);
		
		apoteke.save(a);
		farmaceuti.save(f);
		pacijenti.save(p);
		return novi;
	}

	@Override
	public Collection<ApotekaDTO> getAllPharmacies() {
		Collection<Apoteka> pharms = apoteke.getAll();
		Collection<ApotekaDTO> apos = new ArrayList<ApotekaDTO>();
		for (Apoteka a : pharms) {
			ApotekaDTO p = new ApotekaDTO(a);
			apos.add(p);
		}
		return apos;
	}

	@Override
	public Apoteka getByName(String name) {
		Collection<Apoteka> pharms = apoteke.getAll();
		for (Apoteka apoteka : pharms) {
			System.out.println("APOTEKU " + apoteka.getNaziv() + " POREDIMO SA APOTEKOM " + name);
			if(apoteka.getNaziv().equals(name)) {
				System.out.println("PRONASLI SMO IDENTICNE APOTEKE");
				return apoteka;
			}
		}
		System.out.println("NISAM PRONASAODKJSAL:DJKASL:DASKLJDASKLD");
		return null;
	}

	@Override
	public Apoteka create(Apoteka user) throws Exception {
		lokacije.save(user.getLokacija());
		Apoteka savedUser = apoteke.save(user);
		return savedUser;
	}

	@Override
	public Collection<LekProdajaDTO> lekovi(String[] cures) {
		Collection<Cena> sveCene = cene.getAll();
		Collection<LekProdajaDTO> lista = new ArrayList<LekProdajaDTO>();
		for (Cena cena : sveCene) {
			ArrayList<String> nazivi = new ArrayList<String>();
			if(LocalDate.now().isAfter(cena.getPocetakVazenja()) && LocalDate.now().isBefore(cena.getKrajVazenja())) {
				int foundCures = 0;
				double price = 0;
				for (DostupanProizvod dp : cena.getDostupniProizvodi()) {
					for (String i : cures) {
						if(Integer.parseInt(i.split("\\:")[0]) == dp.getPreparat().getId()) {
							if(Integer.parseInt(i.split("\\:")[1]) <= dp.getKolicina()) {
								foundCures++;
								price += dp.getCena()*Integer.parseInt(i.split("\\:")[1]);
								nazivi.add(dp.getPreparat().getNaziv()+": "+Integer.parseInt(i.split("\\:")[1]));
							}
							
						}
					}
				}
				if(foundCures == cures.length) {
					ApotekaDTO a = new ApotekaDTO(cena.getApoteka());
					LekProdajaDTO lpdto = new LekProdajaDTO(cena.getId(),a, price);
					lpdto.setNazivLekova(String.join(", ", nazivi));
					lista.add(lpdto);
				}
			}
		}
		for (LekProdajaDTO lekProdajaDTO : lista) {
			System.out.println(lekProdajaDTO.getApoteka().getNaziv() + " " + lekProdajaDTO.getCena());
		}
		return lista;
	}

	@Override
	public Collection<LekProdajaDTO> sortLekovi(String[] cures, String crit) {
		ArrayList<LekProdajaDTO> lekovi = (ArrayList<LekProdajaDTO>) lekovi(cures);
		if(crit.equals("naziv")) {
			Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

				@Override
				public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
					return o1.getApoteka().getNaziv().compareTo(o2.getApoteka().getNaziv());
				}
			});
		}
		
		if(crit.equals("mesto")) {
			Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

				@Override
				public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
					return o1.getApoteka().getLokacija().getUlica().compareTo(o2.getApoteka().getLokacija().getUlica());
				}
			});
		}
		
		if(crit.equals("ocena")) {
			Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

				@Override
				public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
					return (int) (o1.getApoteka().getOcena() - o2.getApoteka().getOcena());
				}
			});
		}
		
		if(crit.equals("cena")) {
			Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

				@Override
				public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
					return (int) (o1.getApoteka().getCena() - o2.getApoteka().getCena());
				}
			});
		}
		return lekovi;
	}

	@Transactional
	@Override
	public void kupiLekove(String[] cures, int id, int pacId) {
		Cena c = null;
		Optional<Cena> cOptional = cene.findById(id);

		if (cOptional.isPresent())
			c = cOptional.get();

		ArrayList<DostupanProizvod> zaBrisanje = new ArrayList<DostupanProizvod>();
		ArrayList<Preparat> preparati = new ArrayList<Preparat>();

		int price = 0;
		int bodovi = 0;

		for (Iterator<DostupanProizvod> iterator = c.getDostupniProizvodi().iterator(); iterator.hasNext();) {
		    DostupanProizvod dp = iterator.next();

		    for (String cid : cures) { 
				if(Integer.parseInt(cid.split("\\:")[0]) == dp.getPreparat().getId()) {
					dp.setKolicina(dp.getKolicina() - Integer.parseInt(cid.split("\\:")[1]));
					if(dp.getKolicina() == 0) {
						zaBrisanje.add(dp);
					}
					preparati.add(dp.getPreparat());
					price += dp.getCena() * Integer.parseInt(cid.split("\\:")[1]);
					//poeni += dp.getPreparat().getPoeni() * Integer.parseInt(cid.split("\\:")[1]);
					//sr.add(new StavkaRecepta(Integer.parseInt(cid.split("\\:")[1]), dp.getPreparat()));
				}
		    }
		}
		dostupniproizvodi.saveAll(c.getDostupniProizvodi());
		//stavkerecepta.saveAll(sr);
		String naziviLekova = "";
		Set<StavkaRecepta> sveStavke = new HashSet<StavkaRecepta>();

		for (String prepId : cures) {
			int sifra = Integer.parseInt(prepId.split("\\:")[0]);
			int kol = Integer.parseInt(prepId.split("\\:")[1]);
			Preparat p = null;
			Optional<Preparat> pOptional = lekovi.findById(sifra);

			if (pOptional.isPresent())
				p = pOptional.get();
			else
				return;

			bodovi += p.getPoeni() * kol;
			naziviLekova += p.getNaziv() +"\n";
			StavkaRecepta ss = new StavkaRecepta(kol, p);
			sveStavke.add(ss);
		}
		Pacijent pac = null;
		Optional<Pacijent> pacOptional = pacijenti.findById(pacId);

		if (pacOptional.isPresent())
			pac = pacOptional.get();
		else
			return;

		stavkerecepta.saveAll(sveStavke);
		ERecept erec = new ERecept(LocalDate.now(), pac, StatusERecepta.OBRADJEN, c.getApoteka());
		erec.setStavkaRecepata(sveStavke);
		//Set<DostupanProizvod> set = new HashSet<DostupanProizvod>(zaBrisanje);
		//erec.setStavkaRecepata(set);
		erecepti.save(erec);
		pac.setBrojPoena(pac.getBrojPoena() + bodovi);
		pac.addERecept(erec);
		pacijenti.save(pac);
		//erecepti.save(erec);
		Collection<TipKorisnika> redomTipovi = tipovi.getAllOrdered();
		for (TipKorisnika tipKorisnika : redomTipovi) {
			if(pac.getBrojPoena() > tipKorisnika.getBodovi()) {
				pac.setTipKorisnika(tipKorisnika);
				break;
			}
		}
		pacijenti.save(pac);
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(pac.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Kupovina lekova");
        mail.setText("Pozdrav " + pac.getIme() + " " + pac.getPrezime() + ",\n\nLista lekova koje ste kupili:\n"+naziviLekova+"Vas racun (ukljucujuci popust je): " + (double) (price * (100-pac.getTipKorisnika().getPopust())/100) + "\nOsvojili ste " + bodovi + " bodova!");
        javaMailSender.send(mail);
		
	}

	@Override
	public Collection<LekProdajaDTO> sortLekoviasc(String[] cures, String crit, String asc) {
				//ArrayList<LekProdajaDTO> sortirano = (ArrayList<LekProdajaDTO>) lekovi;
				ArrayList<LekProdajaDTO> lekovi = (ArrayList<LekProdajaDTO>) lekovi(cures);
				if(crit.equals("naziv")) {
					Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

						@Override
						public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
							if(asc.equals("value2")) {
								
								return o1.getApoteka().getNaziv().compareTo(o2.getApoteka().getNaziv());
							} else {
								return o2.getApoteka().getNaziv().compareTo(o1.getApoteka().getNaziv());
							}
							
						}
					});
				}
				
				if(crit.equals("mesto")) {
					Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

						@Override
						public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
							if(asc.equals("value2")) {
								
								return o1.getApoteka().getLokacija().getUlica().compareTo(o2.getApoteka().getLokacija().getUlica());
							} else {
								return o2.getApoteka().getLokacija().getUlica().compareTo(o1.getApoteka().getLokacija().getUlica());
							}
							
						}
					});
				}
				
				if(crit.equals("ocena")) {
					Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

						@Override
						public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
							if(asc.equals("value2")) {
								
								return (int) (o1.getApoteka().getOcena() - o2.getApoteka().getOcena());							
							} else {
								return (int) (o2.getApoteka().getOcena() - o1.getApoteka().getOcena());
							}
							
							
						}
					});
				}
				
				if(crit.equals("cena")) {
					Collections.sort(lekovi, new Comparator<LekProdajaDTO>() {

						@Override
						public int compare(LekProdajaDTO o1, LekProdajaDTO o2) {
							if(asc.equals("value2")) {		
								return (int) (o1.getApoteka().getCena() - o2.getApoteka().getCena());							
							} else {
								return (int) (o2.getApoteka().getCena() - o1.getApoteka().getCena());
							}
							
						}
					});
				}

				return lekovi;
	}

}