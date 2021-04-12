package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Rezervacija;
import rest.domain.TipKorisnika;
import rest.dto.KorisnikDTO;
import rest.dto.PacijentDTO;
import rest.dto.PregledDTO;
import rest.dto.RezervacijaDTO;
import rest.repository.KorisnikRepository;
import rest.repository.LokacijaRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.RezervacijaRepository;
import rest.repository.TipKorisnikaRepository;

@Service
@Transactional
public class KorisnikServiceImpl implements KorisnikService {

	private static final int defaultPageSize = 10;
	private KorisnikRepository korisnikRepository;
	private PacijentRepository pacijentRepository;
	private PenalRepository penalRepository;
	private PregledRepository pregledRepository;
	private RezervacijaRepository rezervacijeRepository;
	private LokacijaRepository lokacijaRepository;
	private TipKorisnikaRepository tipRepo;
	private Environment env;
	private JavaMailSender javaMailSender;
	
	
	@Autowired
	public KorisnikServiceImpl(KorisnikRepository imkr, PenalRepository pr, PregledRepository prer, RezervacijaRepository rr, PacijentRepository pacr, LokacijaRepository locr, TipKorisnikaRepository rt, Environment e, JavaMailSender jms) {
		this.korisnikRepository = imkr;
		this.penalRepository = pr;
		this.rezervacijeRepository = rr;
		this.pregledRepository = prer;
		this.pacijentRepository = pacr;
		this.lokacijaRepository = locr;
		this.tipRepo = rt;
		this.env = e;
		this.javaMailSender = jms;
	}

	@Override
	public Collection<Korisnik> findAll() {
		Collection<Korisnik> users = korisnikRepository.findAll();
		return users;
	}

	@Override
	public Korisnik findOne(int id) {
		Korisnik user = korisnikRepository.findById(id).get();
		return user;
	}

	@Override
	public Korisnik create(Korisnik user) throws Exception {
		lokacijaRepository.save(user.getLokacija());
		Korisnik savedUser = korisnikRepository.save(user);
		return savedUser;
	}

	@Override
	public Korisnik update(KorisnikDTO user) throws Exception {
		Korisnik userToUpdate = findOne(user.getId());
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		
		if(user.getStariPassw() != null && !user.getStariPassw().equals(userToUpdate.getPassword())) {
			throw new Exception("Stari password je neispravan.");
		}
		
		if(user.getStariPassw() != null && user.getStariPassw().equals(userToUpdate.getPassword())) {
			if(user.getNoviPassw() == null || user.getNoviPassw().trim().equals("")) {
				throw new Exception("Novi password je u losem formatu.");
			}
			userToUpdate.setPassword(user.getNoviPassw());
		}
		
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setTelefon(user.getTelefon());
		lokacijaRepository.save(user.getLokacija());
		userToUpdate.setLokacija(user.getLokacija());
		
		Korisnik updatedUSer = korisnikRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		korisnikRepository.deleteById(id);
	}

	@Override
	public Collection<Penal> getPenali(int id) {
		return penalRepository.penalForUser(id);
	}

	@Override
	@SuppressWarnings("deprecation")
	public Page<PregledDTO> preglediZaKorisnika(int id, int page, String criteria) {
		switch(criteria) {
		case "PLAIN":
			return pregledRepository.istorijaPregledaZaKorisnika(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p, 0));
		case "DATUM":
			return pregledRepository.istorijaPregledaZaKorisnikaDatumSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p, 0));
		case "CENA":
			return pregledRepository.istorijaPregledaZaKorisnikaCenaSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p, 0));
		case "TRAJANJE":
			return pregledRepository.istorijaPregledaZaKorisnikaTrajanjeSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p, 0));
		}
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Page<PregledDTO> zakazivanjaZaKorisnika(int id, int page) {
		return pregledRepository.rezervacijePregledaZaKorisnika(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p, 0));
	}

	@Override
	public Collection<RezervacijaDTO> rezervacijeZaKorisnika(int id) {
		Collection<Rezervacija> rezervacije = rezervacijeRepository.rezervacijeZaKorisnika(id);
		ArrayList<RezervacijaDTO> retVal = new ArrayList<RezervacijaDTO>();
		for(Rezervacija r : rezervacije){
			retVal.add(new RezervacijaDTO(r));
		}
		return retVal;
	}

	@Override
	public PacijentDTO findPacijentById(int id) {
		Pacijent p = pacijentRepository.findById(id).get();
		return new PacijentDTO(p);
	}

	@Override
	public TipKorisnika pocetniTip() {
		return tipRepo.findById(1).get();
	}

	@Override
	@Async
	public void sendRegistrationMail(Pacijent p) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(p.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Hvala sto ste se prijavili na nasu aplikaciju!");
        mail.setText("Pozdrav " + p.getIme() + " " + p.getPrezime() + ",\n\nhvala što koristite nasu aplikaciju, kliknite na link ispod kako biste verifikovali nalog\nLorem ipsum dolor sit amet.");
        javaMailSender.send(mail);
	}
	
	
}
