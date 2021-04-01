package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import rest.domain.Korisnik;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Rezervacija;
import rest.dto.PacijentDTO;
import rest.dto.PregledDTO;
import rest.dto.RezervacijaDTO;
import rest.repository.KorisnikRepository;
import rest.repository.LokacijaRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.RezervacijaRepository;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	private static final int defaultPageSize = 1;
	private KorisnikRepository korisnikRepository;
	private PacijentRepository pacijentRepository;
	private PenalRepository penalRepository;
	private PregledRepository pregledRepository;
	private RezervacijaRepository rezervacijeRepository;
	private LokacijaRepository lokacijaRepository;
	
	@Autowired
	public KorisnikServiceImpl(KorisnikRepository imkr, PenalRepository pr, PregledRepository prer, RezervacijaRepository rr, PacijentRepository pacr, LokacijaRepository locr) {
		this.korisnikRepository = imkr;
		this.penalRepository = pr;
		this.rezervacijeRepository = rr;
		this.pregledRepository = prer;
		this.pacijentRepository = pacr;
		this.lokacijaRepository = locr;
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
	public Korisnik update(Korisnik user) throws Exception {
		Korisnik userToUpdate = findOne(user.getId());
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
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
			return pregledRepository.istorijaPregledaZaKorisnika(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p));
		case "DATUM":
			return pregledRepository.istorijaPregledaZaKorisnikaDatumSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p));
		case "CENA":
			return pregledRepository.istorijaPregledaZaKorisnikaCenaSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p));
		case "TRAJANJE":
			return pregledRepository.istorijaPregledaZaKorisnikaTrajanjeSort(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p));
		}
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Page<PregledDTO> zakazivanjaZaKorisnika(int id, int page) {
		return pregledRepository.rezervacijePregledaZaKorisnika(id, new PageRequest(page, defaultPageSize)).map(p -> new PregledDTO(p));
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
	
}
