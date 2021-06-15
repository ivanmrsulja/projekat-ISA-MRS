package rest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;
import rest.domain.Zahtjev;
import rest.domain.Zaposlenje;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.KorisnikRepository;
import rest.repository.PacijentRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.ZahtevRepository;
import rest.repository.ZaposlenjeRepository;

@Service
@Transactional
public class DermatologServiceImpl implements DermatologService {

	private DermatologRepository dermatologRepository;
	private PregledRepository pregledRepository;
	private PreparatRepository preparatRepository;
	private PregledService pregledService;
	private PacijentRepository pacijentRepository;
	private ZahtevRepository zahtevRepository;
	private ZaposlenjeRepository zaposlenjeRepository;
	private ApotekeRepository apotekeRepository;
	private KorisnikRepository korisnikRepository;
	
	@Autowired
	public DermatologServiceImpl(KorisnikRepository kori,ApotekeRepository ko,PregledRepository pre,ZaposlenjeRepository za,ZahtevRepository z,DermatologRepository imdr,PreparatRepository pr,PregledRepository pp , PregledService ps, PacijentRepository ppp) {
		this.dermatologRepository = imdr;
		this.preparatRepository=pr;
		this.pregledRepository=pp;
		this.pregledService=ps;
		this.pacijentRepository=ppp;
		this.zahtevRepository=z;
		this.zaposlenjeRepository=za;
		this.pregledRepository=pre;
		this.apotekeRepository=ko;
		this.korisnikRepository=kori;
	}

	@Override
	public Collection<Dermatolog> findAll() {
		Collection<Dermatolog> users = dermatologRepository.findAll();
		return users;
	}

	@Override
	public Dermatolog findOne(int id) {
		Optional<Dermatolog> userOpt = dermatologRepository.findById(id);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
	}

	@Override
	public Korisnik create(Dermatolog user) throws Exception {
		if (user.getId() != 0) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Korisnik savedUser = dermatologRepository.save(user);
		return savedUser;
	}

	@Override
	public Dermatolog update(KorisnikDTO user) throws Exception {
		Dermatolog userToUpdate = findOne(user.getId());
		
		
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setTelefon(user.getTelefon());
		userToUpdate.setLokacija(user.getLokacija());
		
		Dermatolog updatedUSer = dermatologRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		dermatologRepository.deleteById(id);
	}

	@Override
	public Collection<Dermatolog> findAllForPharmacy(int id) {
		return dermatologRepository.getWithEmployments(id);
	}
	@Override
	public void zavrsi(PregledDTO pregled, int id){

		Optional<Pregled> pOpt = pregledRepository.findById(id);
		Pregled p = null;
		if (pOpt.isPresent()) {
			p = pOpt.get();
		}
		else {
			return;
		}

		p.setIzvjestaj(pregled.getIzvjestaj());
		p.setStatus(StatusPregleda.ZAVRSEN);
		
		for(PreparatDTO pr : pregled.getTerapija()) {
			Preparat tmp=preparatRepository.getOne(pr.getId());
			p.getTerapija().add(tmp);
		}
		pregledRepository.save(p);
	}
	
	@Override
	public void updateAlergije(int pid,int aid)
	{
		Optional<Pregled> pOpt = pregledRepository.findById(pid);
		Pregled p = null;
		if (pOpt.isPresent()) {
			p = pOpt.get();
		}
		else {
			return;
		}
		
		Optional<Preparat> prep = preparatRepository.findById(aid);
		Preparat dodaj=null;
		if (prep.isPresent()) {
			dodaj = prep.get();
		}
		else {
			return;
		}
		
		Set<Preparat> terapija = new HashSet<Preparat>();
		for(Preparat pp : p.getTerapija())
		{
			terapija.add(pp);
		}
		terapija.add(dodaj);
		
		
		p.setTerapija(terapija);		
		
		pregledRepository.save(p);
	}
	
	@Override
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediMesec(HttpSession s)
	{
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		HashMap<LocalDate, ArrayList<PregledDTO>> hmap = new HashMap<LocalDate, ArrayList<PregledDTO>>();
	
		for(Pregled d : pregledi) {
			 LocalDate za7=LocalDate.now().plusDays(30);
			 LocalDate sad=LocalDate.now();
		 	 LocalDate ll=d.getDatum();
		 	   
			if(sad.compareTo(ll)<0 && za7.compareTo(ll)>0)
			{
				preglediDTO.add(new PregledDTO(d,0));	
				if(hmap.containsKey(d.getDatum())) {
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
				else {
					hmap.put(d.getDatum(),new ArrayList<PregledDTO>());
					
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
			}
		}
		
		Map<LocalDate, ArrayList<PregledDTO>> sortedMap = new TreeMap<>(new Comparator<LocalDate>() {
		    @Override
		    public int compare(LocalDate o1, LocalDate o2) {
		        return o1.compareTo(o2);
		    }
		});
		
		for(LocalDate datumi:hmap.keySet()) {
			Collections.sort(hmap.get(datumi),new Comparator<PregledDTO>() {

				@Override
				public int compare(PregledDTO o1, PregledDTO o2) {
					
					return o1.getVrijeme().compareTo(o2.getVrijeme());
				}
			});
		}
			
		
		sortedMap.putAll(hmap);
			
		return sortedMap;
	}

	@Override
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediGodina(HttpSession s)
	{
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		HashMap<LocalDate, ArrayList<PregledDTO>> hmap = new HashMap<LocalDate, ArrayList<PregledDTO>>();
	
		for(Pregled d : pregledi) {
			 LocalDate za7=LocalDate.now().plusDays(365);
			 LocalDate sad=LocalDate.now();
		 	 LocalDate ll=d.getDatum();
		 	   
			if(sad.compareTo(ll)<0 && za7.compareTo(ll)>0)
			{
				preglediDTO.add(new PregledDTO(d,0));	
				if(hmap.containsKey(d.getDatum())) {
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
				else {
					hmap.put(d.getDatum(),new ArrayList<PregledDTO>());
					
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
			}
		}
		
		Map<LocalDate, ArrayList<PregledDTO>> sortedMap = new TreeMap<>(new Comparator<LocalDate>() {
		    @Override
		    public int compare(LocalDate o1, LocalDate o2) {
		        return o1.compareTo(o2);
		    }
		});
		
		for(LocalDate datumi:hmap.keySet()) {
			Collections.sort(hmap.get(datumi),new Comparator<PregledDTO>() {

				@Override
				public int compare(PregledDTO o1, PregledDTO o2) {
					
					return o1.getVrijeme().compareTo(o2.getVrijeme());
				}
			});
		}
			
		
		sortedMap.putAll(hmap);
			
		return sortedMap;
	}
	
	@Override
	public Map<LocalDate, ArrayList<PregledDTO>> PreglediSedmica(HttpSession s)
	{
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		HashMap<LocalDate, ArrayList<PregledDTO>> hmap = new HashMap<LocalDate, ArrayList<PregledDTO>>();
	
		for(Pregled d : pregledi) {
			 LocalDate za7=LocalDate.now().plusDays(7);
			 LocalDate sad=LocalDate.now();
		 	 LocalDate ll=d.getDatum();
		 	   
			if(sad.compareTo(ll)<0 && za7.compareTo(ll)>0)
			{
				preglediDTO.add(new PregledDTO(d,0));	
				if(hmap.containsKey(d.getDatum())) {
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
				else {
					hmap.put(d.getDatum(),new ArrayList<PregledDTO>());
					
					hmap.get(d.getDatum()).add(new PregledDTO(d,0));
				}
			}
		}
		
		Map<LocalDate, ArrayList<PregledDTO>> sortedMap = new TreeMap<>(new Comparator<LocalDate>() {
		    @Override
		    public int compare(LocalDate o1, LocalDate o2) {
		        return o1.compareTo(o2);
		    }
		});
		
		for(LocalDate datumi:hmap.keySet()) {
			Collections.sort(hmap.get(datumi),new Comparator<PregledDTO>() {

				@Override
				public int compare(PregledDTO o1, PregledDTO o2) {
					
					return o1.getVrijeme().compareTo(o2.getVrijeme());
				}
			});
		}
			
		
		sortedMap.putAll(hmap);
			
		return sortedMap;
	}
	
	@Override
	public Set<PreparatDTO> proveriAlergije(int pid)
	{
		Set<PreparatDTO> alergije= new HashSet<PreparatDTO>();
		
		Optional<Pregled> pOpt = pregledRepository.findById(pid);
		Pregled pregled = null;
		if (pOpt.isPresent()) {
			pregled = pOpt.get();
		}
		else {
			return alergije;
		}
				
		
		Pacijent pacijent= pacijentRepository.getOne(pregled.getPacijent().getId());
				
		Set<Preparat> terapija = new HashSet<Preparat>();
				
		for(Preparat p:pregled.getTerapija())
		{
			terapija.add(p);
		}
		
		for(Preparat t : pacijent.getAlergije())
		{
			for(Preparat tt : terapija) {
				if(tt.getId()==t.getId())
				{
					alergije.add(new PreparatDTO(t));
				}
			}
		}
		
		
		for(PreparatDTO pr : alergije) {
			Preparat tmp=preparatRepository.getOne(pr.getId());
			pregled.getTerapija().remove(tmp);
		}
		pregledRepository.save(pregled);

		return alergije;
	}
	
	@Override
	public Pregled registerExamination(PregledDTO examinationDTO, Integer  dermatologistId, Integer  pharmacyId) throws Exception{

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
		examination.setStatus(StatusPregleda.ZAKAZAN);

		Apoteka a = null;
		Korisnik k = null;
		Optional<Apoteka> aOptional = apotekeRepository.findById(pharmacyId);
		Optional<Korisnik> kOptional = korisnikRepository.findById(dermatologistId);

		if (!aOptional.isPresent() || !kOptional.isPresent())
			return null;

		a = aOptional.get();
		k = kOptional.get();

		examination.setApoteka(a);
		examination.setZaposleni(k);
		examination.setDatum(examinationDTO.getDatum());
		examination.setVrijeme(examinationDTO.getVrijeme());
		examination.setTrajanje(examinationDTO.getTrajanje());
		examination.setCijena(examinationDTO.getCijena());

		pregledRepository.save(examination);

		return examination;
	}
}