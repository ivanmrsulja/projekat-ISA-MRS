package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.ERecept;
import rest.domain.Farmaceut;
import rest.domain.OcenaApoteke;
import rest.domain.OcenaZaposlenog;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.domain.Zalba;
import rest.dto.PreparatDTO;
import rest.dto.ZalbaDTO;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.EReceptRepository;
import rest.repository.FarmaceutRepository;
import rest.repository.KorisnikRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.RezervacijaRepository;
import rest.repository.ZalbaRepository;

@Service
@Transactional
public class PacijentServiceImpl implements PacijentService {
	
	private PacijentRepository pacijentRepository;
	private PreparatRepository preparatRepository;
	private PregledRepository pregledRepository;
	private PenalRepository penaliRepository;
	private DermatologRepository dermaRepository;
	private FarmaceutRepository farmaRepository;
	private ApotekeRepository apotekeRepository;
	private RezervacijaRepository rezervacijaRepository;
	private EReceptRepository eReceptRepository;
	private ZalbaRepository zalbaRepository;
	
	@Autowired
	public PacijentServiceImpl(ZalbaRepository zalre,EReceptRepository erepo,RezervacijaRepository rezeRepo,ApotekeRepository apore,FarmaceutRepository farrep,DermatologRepository dermrep,PacijentRepository pacijentRepository, PreparatRepository preparatRepository,PregledRepository pregledRepository, PenalRepository pr) {
		this.pacijentRepository = pacijentRepository;
		this.zalbaRepository = zalre;
		this.eReceptRepository = erepo;
		this.rezervacijaRepository = rezeRepo;
		this.apotekeRepository = apore;
		this.farmaRepository = farrep;
		this.dermaRepository = dermrep;
		this.preparatRepository = preparatRepository;
		this.pregledRepository=pregledRepository;
		this.penaliRepository = pr;
	}


	@Override
	public Collection<PreparatDTO> allergies(int id) {
		Collection<Preparat> alergije = pacijentRepository.getAllergiesForUser(id);
		ArrayList<PreparatDTO> ret = new ArrayList<PreparatDTO>();
		for(Preparat p : alergije) {
			ret.add(new PreparatDTO(p));
		}
		return ret;
	}

	@Override
	public Collection<PreparatDTO> addAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().add(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}


	@Override
	public Collection<PreparatDTO> removeAllergy(int id, int idPrep) {
		Pacijent p = pacijentRepository.findById(id).get();
		Preparat pr = preparatRepository.findById(idPrep).get();
		p.getAlergije().remove(pr);
		pacijentRepository.save(p);
		return allergies(id);
	}
	
	@Override
	public Collection<Pacijent> getAll(){
		Collection<Pacijent> users = pacijentRepository.findAll();
		return users;
	}
	
	@Override
	public Pacijent getOne(int id) {
		Pacijent prep = pacijentRepository.findById(id).get();
		return prep;
	}
	
	@Override
	public Collection<Pacijent> getMine(int id, String param, String criteria){
		Collection<Pregled> pregledi = pregledRepository.getMine(id);
		ArrayList<Pacijent> pacijentiRet = new ArrayList<Pacijent>();
		ArrayList<Pregled> preglediRet = new ArrayList<Pregled>();
		
		for(Pregled p : pregledi) {
			if(p.getPacijent() != null) {
				String imePrezime = p.getPacijent().getIme() + " " + p.getPacijent().getPrezime();
				if(imePrezime.toLowerCase().contains(param.toLowerCase())) {
					preglediRet.add(p);
				}
			}
		}
		
		switch(criteria) {
			case "IME":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getPacijent().getIme().compareTo(arg1.getPacijent().getIme());
					}
				});
				break;
			case "PREZIME":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getPacijent().getPrezime().compareTo(arg1.getPacijent().getPrezime());
					}
				});
				break;
			case "DATUM":
				Collections.sort(preglediRet, new Comparator<Pregled>() {

					@Override
					public int compare(Pregled arg0, Pregled arg1) {
						return arg0.getDatum().compareTo(arg1.getDatum());
					}
				});
				break;
		}
		
		for(Pregled p : preglediRet) {
			pacijentiRet.add(p.getPacijent());
		}
		
		return pacijentiRet;
	}

	@Override
	public void removeAllPenalities() {
		Collection<Pacijent> patients = pacijentRepository.getWithPenalities();
		Collection<Penal> penali = penaliRepository.findAll();
		for(Pacijent pa : patients) {
			pa.getPenali().clear();
			pacijentRepository.save(pa);
		}
		for(Penal p : penali) {
			penaliRepository.delete(p);
		}
	}
	
	
	@Override
	public void addPenal(int id, Penal p) {
		Penal pp= new Penal(p.getDatum(),p.getPacijent());
		Pacijent pacijent = pacijentRepository.findById(id).get();
		pacijent.addPenal(pp);
		penaliRepository.save(pp);
		pacijentRepository.save(pacijent);
	}


	@Override
	public Collection<ZalbaDTO> getZalbeForPatient(int id) {
		// TODO Auto-generated method stub
		Collection<Zalba> zalbe = pacijentRepository.getPatientZalbe(id);
		Collection<ZalbaDTO> zdto = new ArrayList<ZalbaDTO>();
		for (Zalba z : zalbe) {
			ZalbaDTO zt = new ZalbaDTO(z);
			zdto.add(zt);
		}
		return zdto;
	}


	@Override
	public ZalbaDTO getZalbaForPatient(int id, int zalId) {
		// TODO Auto-generated method stub
		Collection<Zalba> zalbe = pacijentRepository.getPatientZalbe(id);
		Collection<ZalbaDTO> zdto = new ArrayList<ZalbaDTO>();
		for (Zalba z : zalbe) {
			if(z.getId().equals(zalId)) {
				ZalbaDTO zald = new ZalbaDTO(z);
				return zald;
			}
		}
		return null;
	}


	@Override
	public Collection<String> getAllAppealable(int id) {
		Collection<String> zaljivi = new ArrayList<String>();
		Collection<Dermatolog> dermatolozi = dermaRepository.getAllDerme();
		for (Dermatolog dermatolog : dermatolozi) {
			Collection<Pregled> pregledi = dermaRepository.getExaminationsForPatientAndDermatologist(dermatolog.getId(), id);
			if (pregledi.size() == 0) {
				continue;
			}
			zaljivi.add("Dermatolog: "+ dermatolog.getUsername());
			
		}
		System.out.println("BROJ DERMATOLOGA JE    " + dermatolozi.size());
		Collection<Farmaceut> farmaceuti = farmaRepository.getAllPharmacist();
		for (Farmaceut farmaceut : farmaceuti) {
			Collection<Pregled> pregledi = farmaRepository.getConsultmentsForPatientAndPharmacist(farmaceut.getId(), id);
			if (pregledi.size() == 0) {
				continue;
			}
			zaljivi.add("Farmaceut: " + farmaceut.getUsername());
		}
		
		Collection<Apoteka> apoteke = apotekeRepository.getAll();
		for (Apoteka apoteka : apoteke) {
			Collection<Pregled> pregledi = pregledRepository.preglediUApoteci(apoteka.getId(), id);
			Collection<Rezervacija> rezervacije = rezervacijaRepository.rezervacijaUApoteci(apoteka.getId(), id);
			Collection<ERecept> eRecepti = eReceptRepository.zaApotekuIKorisnika(apoteka.getId(), id);
			if (pregledi.size() == 0 && rezervacije.size() == 0 && eRecepti.size() == 0) {
				continue;
			}
			zaljivi.add("Apoteka: " + apoteka.getNaziv());
		}
		return zaljivi;
		
		
	}


	@Override
	public void sendZalba(ZalbaDTO zdto) {
		Pacijent p = pacijentRepository.getPatientByUser(zdto.getNazivKorisnika());
		Zalba z = new Zalba(zdto.getTekst(), null, p);
		zalbaRepository.save(z);
		p.addZalba(z);
		pacijentRepository.save(p);
		
		
		
	}
	
}
