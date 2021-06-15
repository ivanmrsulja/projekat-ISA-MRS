package rest.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Farmaceut;
import rest.domain.Pregled;
import rest.domain.Zaposlenje;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.repository.FarmaceutRepository;
import rest.repository.LokacijaRepository;
import rest.repository.NotifikacijaRepository;
import rest.repository.PregledRepository;
import rest.repository.ZaposlenjeRepository;

@Service
@Transactional
public class FarmaceutServiceImpl implements FarmaceutService {

	private FarmaceutRepository farmaceutRepository;
	private ApotekaService apotekaService;
	private ZaposlenjeRepository zaposlenjeRepository;
	private LokacijaRepository lokacijaRepository;
	private PregledRepository pregledRepository;
	private NotifikacijaRepository notifikacijaRepository;
	private PregledService pregledService;
	
	@Autowired
	public FarmaceutServiceImpl(FarmaceutRepository imfr, ApotekaService as, ZaposlenjeRepository zr, LokacijaRepository lr, PregledRepository pr, NotifikacijaRepository nr,PregledService ps) {
		this.farmaceutRepository = imfr;
		this.apotekaService = as;
		this.zaposlenjeRepository = zr;
		this.lokacijaRepository = lr;
		this.pregledRepository = pr;
		this.notifikacijaRepository = nr;
		this.pregledService=ps;
	}

	@Override
	public Collection<Farmaceut> findAll() {
		Collection<Farmaceut> users = farmaceutRepository.findAll();
		return users;
	}

	@Override
	public Farmaceut findOne(int id) {
		Optional<Farmaceut> user = farmaceutRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	@Transactional
	public Farmaceut create(FarmaceutDTO farmaceut, int idApoteke) throws Exception {
		lokacijaRepository.save(farmaceut.getLokacija());
		Farmaceut f = new Farmaceut();
		f.setIme(farmaceut.getIme());
		f.setPrezime(farmaceut.getPrezime());
		f.setUsername(farmaceut.getUsername());
		f.setEmail(farmaceut.getEmail());
		f.setTelefon(farmaceut.getTelefon());
		f.setLokacija(farmaceut.getLokacija());
		f.setZaposlenjeKorisnika(farmaceut.getZaposlenjeKorisnika());
		f.setPassword(farmaceut.getNoviPassw());
		f.setLoggedBefore(false);
		f.setZaposlenjeKorisnika(ZaposlenjeKorisnika.FARMACEUT);
		f.setBrojOcena(0);
		f.setSumaOcena(0);
		f.setOcena(0);


		Zaposlenje zaposlenje = new Zaposlenje(farmaceut.getPocetakRadnogVremena(), farmaceut.getKrajRadnogVremena(), apotekaService.getForAdmin(idApoteke), f);
		zaposlenjeRepository.save(zaposlenje);
		f.setZaposlenje(zaposlenje);
		Farmaceut savedUser = farmaceutRepository.save(f);
		return savedUser;
	}

	@Override
	public Farmaceut update(KorisnikDTO user) throws Exception {
		Farmaceut userToUpdate = findOne(user.getId());
		
		
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setTelefon(user.getTelefon());
		userToUpdate.setLokacija(user.getLokacija());
		
		Farmaceut updatedUSer = farmaceutRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		farmaceutRepository.deleteById(id);
	}

	@Override
	public Collection<Farmaceut> findAllForPharmacy(int id) {
		return farmaceutRepository.getWithEmployments(id);
	}

	@Override
	public boolean checkIfPharmacistHasAppointments(int pharmacistId, int pharmacyId) {
		Collection<Pregled> appointments = pregledRepository.getScheduledAppointments(pharmacistId, pharmacyId);
		if (appointments.size() == 0) {
			return false;
		}

		return true;
	}

	@Override
	public void deletePharmacist(int pharmacistId) {
		notifikacijaRepository.deleteNotificationsOfUser(pharmacistId);
		farmaceutRepository.deleteById(pharmacistId);
		zaposlenjeRepository.deleteForPharmacist(pharmacistId);
	}

	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaSedmica(HttpSession s) {
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
	
	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaMesec(HttpSession s) {
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
	
	public Map<LocalDate, ArrayList<PregledDTO>> getSavetovanjaGodina(HttpSession s)
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
}