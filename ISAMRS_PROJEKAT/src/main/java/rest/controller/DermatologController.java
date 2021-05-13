package rest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.aspect.AsPacijent;
import rest.domain.Dermatolog;
import rest.domain.Pregled;
import rest.dto.DermatologDTO;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.domain.Ponuda;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.ApotekaDTO;
import rest.dto.DermatologDTO;
import rest.dto.FarmaceutDTO;
import rest.dto.KorisnikDTO;
import rest.repository.DermatologRepository;
import rest.service.AdminService;
import rest.service.DermatologService;
import rest.service.PregledService;


@RestController
@RequestMapping("/api/dermatolog")
public class DermatologController {

	private DermatologService dermatologService;
	private PregledService pregledService;
	private DermatologRepository dermatologRepository;
	
	@Autowired
	public DermatologController(DermatologService dermatolog,PregledService pregled, DermatologRepository dr) {
		this.dermatologService = dermatolog;
		this.pregledService = pregled;
		this.dermatologRepository = dr;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsers() {
		Collection<Dermatolog> users = dermatologService.findAll();
		ArrayList<KorisnikDTO> dermatolozi=new ArrayList<KorisnikDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new KorisnikDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> getAllDermatologists() {
		Collection<Dermatolog> users = dermatologRepository.getAllDermatologists();
		ArrayList<DermatologDTO> dermatolozi=new ArrayList<DermatologDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new DermatologDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/searchUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> userSearchPharmacists(@RequestParam String ime, @RequestParam String prezime, @RequestParam int startOcena, @RequestParam int endOcena, @RequestParam String kriterijumSortiranja, @RequestParam boolean opadajuce){
		Collection<Dermatolog> users = dermatologRepository.getAllDermatologists();
		ArrayList<DermatologDTO> farmaceuti = new ArrayList<DermatologDTO>();
		for (Dermatolog f : users) {
			if (f.getIme().contains(ime) && f.getPrezime().contains(prezime) && (f.getOcena() <= endOcena && f.getOcena() >= startOcena)) {
				farmaceuti.add(new DermatologDTO(f, kriterijumSortiranja));
			}
		}
		Collections.sort(farmaceuti);
		if (opadajuce)
			Collections.reverse(farmaceuti);

		return farmaceuti;
	}
	
	@GetMapping(value="/apoteka/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<KorisnikDTO> getUsersForPharmacy(@PathVariable("id") int id) {
		Collection<Dermatolog> users = dermatologService.findAllForPharmacy(id);
		ArrayList<KorisnikDTO> dermatolozi = new ArrayList<KorisnikDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new KorisnikDTO(d));
		return dermatolozi;
	}

	@GetMapping(value="/apoteka/admin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<DermatologDTO> getDermatologistsForPharmacy(@PathVariable("id") int id) {
		Collection<Dermatolog> users = dermatologService.findAllForPharmacy(id);
		ArrayList<DermatologDTO> dermatolozi = new ArrayList<DermatologDTO>();
		for(Dermatolog d : users)
			dermatolozi.add(new DermatologDTO(d));
		return dermatolozi;
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KorisnikDTO> updateUser(@RequestBody KorisnikDTO user, @PathVariable("id") int id)
			throws Exception {
		//System.out.println(user);
		Dermatolog updatedDermatolog = dermatologService.update(user);

		if (updatedDermatolog == null) {
			return new ResponseEntity<KorisnikDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(updatedDermatolog), HttpStatus.OK);
	}
	
	@GetMapping(value ="/pregledi", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<LocalDate, ArrayList<PregledDTO>> getPregledi(HttpSession s) {
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
		HashMap<LocalDate, ArrayList<PregledDTO>> hmap = new HashMap<LocalDate, ArrayList<PregledDTO>>();
	
		for(Pregled d : pregledi) {
			preglediDTO.add(new PregledDTO(d,0));	
			if(hmap.containsKey(d.getDatum())) {
				hmap.get(d.getDatum()).add(new PregledDTO(d,0));
			}
			else {
				hmap.put(d.getDatum(),new ArrayList<PregledDTO>());
				
				hmap.get(d.getDatum()).add(new PregledDTO(d,0));
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
	
	@GetMapping(value ="/listePregleda", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<PregledDTO> getListaPregleda(HttpSession s) {
		KorisnikDTO korisnik = (KorisnikDTO)s.getAttribute("user");
		Collection<Pregled> pregledi= pregledService.dobaviZaDermatologa(korisnik.getId());
		ArrayList<PregledDTO> preglediDTO = new ArrayList<PregledDTO>();
			
		for(Pregled d : pregledi) {
			preglediDTO.add(new PregledDTO(d,0));			
		}			
		
		return preglediDTO;
	}
	
	@GetMapping(value ="/pregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PregledDTO getPregled(@PathVariable Integer id) {
		return new PregledDTO(pregledService.dobaviPregledZa(id),0);
	}
	
	@PutMapping(value = "/zavrsi/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String zavrsiPregled(@RequestBody PregledDTO pregled, @PathVariable("id") int id)
			throws Exception {
		System.out.println(pregled);
		try
		{
			dermatologService.zavrsi(pregled,id);
		}
		catch(Exception e){
			return e.getMessage();
		}
		return ("OK");
	}
	
	@PostMapping(value = "/zakaziNovi/{aid}/{kid}/{pid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String zakaziNoviPregled(@RequestBody PregledDTO pregled,@PathVariable Integer aid,@PathVariable Integer kid,@PathVariable Integer pid) {
		try {
			pregledService.makeNewExam(pregled,aid,kid,pid);
			return ("Uspesno zakazano");
		}catch(OptimisticLockingFailureException p) {
			return "Doslo je do greske prilikom zakazivanja. Osvezite stranicu i pokusajte ponovo.";
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@AsPacijent
	@GetMapping(value ="/ocenjivanje/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public DermatologDTO zaOcenjivanje(@PathVariable int id) {
		return new DermatologDTO(dermatologService.findOne(id));
	}
}