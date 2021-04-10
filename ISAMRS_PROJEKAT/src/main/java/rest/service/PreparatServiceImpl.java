package rest.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.DostupanProizvod;
import rest.domain.Pacijent;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.domain.StatusRezervacije;
import rest.dto.KorisnikDTO;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.PacijentRepository;
import rest.repository.PreparatRepository;
import rest.repository.RezervacijaRepository;

@Service
public class PreparatServiceImpl implements PreparatService{

	private PreparatRepository preparatRepository;
	private ApotekeRepository apotekeRepository;
	private CenaRepository cenaRepository;
	private PacijentRepository pacijentRepository;
	private DostupanProizvodRepository dostupanRepo;
	private RezervacijaRepository rezervacijaRepository;
	
	private Environment env;
	private JavaMailSender javaMailSender;
	
	@Autowired
	public PreparatServiceImpl(PreparatRepository pr, ApotekeRepository ar, CenaRepository cr, PacijentRepository pacijentRepo, DostupanProizvodRepository d, RezervacijaRepository rr, Environment env, JavaMailSender jms) {
		this.preparatRepository = pr;
		this.apotekeRepository = ar;
		this.cenaRepository = cr;
		this.env = env;
		this.javaMailSender = jms;
		this.pacijentRepository = pacijentRepo;
		this.dostupanRepo = d;
		this.rezervacijaRepository = rr;
	}
	
	@Override
	public Collection<Preparat> getAll() {
		Collection<Preparat> preparati = preparatRepository.findAll();
		return preparati;
	}
	
	@Override
	public Preparat getOne(int id) {
		Preparat prep = preparatRepository.findById(id).get();
		return prep;
	}

	@Override
	public Collection<Preparat> getAllForPharmacy(int id) {
		return cenaRepository.drugsForPharmacy(id);
	}

	@Override
	@Async
	public void sendConfirmationEmail(KorisnikDTO user, Rezervacija p) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Uspesno rezervisan preparat.");
        mail.setText("Pozdrav " + user.getIme() + " " + user.getPrezime() + ",\n\nUspesno ste rezervisali preparat. Broj rezervacije je: " + p.getId() + ". Rezervaciju mozete preuzeti do: " + p.getDatumPreuzimanja() + ".\n\nSrdacan pozdrav,\nTim 06 :)");
        javaMailSender.send(mail);
		
	}

	@Override
	public Collection<Apoteka> getPharmaciesForDrug(int id) {
		return cenaRepository.getPharmaciesForDrug(id);
	}

	@Override
	@Transactional
	public void otkazi(int idr, int id) throws Exception {
		Rezervacija r = rezervacijaRepository.findById(idr).get();
		
		if(r.getPacijent().getId() != id) {
			throw new Exception("Ne mozeee :)");
		}
		
		Instant instant1 = r.getDatumPreuzimanja().atStartOfDay(ZoneId.systemDefault()).toInstant();	
		long timeInMillis1 = instant1.toEpochMilli();
		Instant instant2 = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();	
		long timeInMillis2 = instant2.toEpochMilli();
		
		if(timeInMillis1 <= timeInMillis2 + 86400000) {
			throw new Exception("Rezervacije je moguce otkazati najkasnije 24h pre preuzimanja.");
		}
		
		r.setStatus(StatusRezervacije.OTKAZANO);
		rezervacijaRepository.save(r);
	}

	@Override
	@Transactional
	public Rezervacija rezervisi(int idp, int idpa, int ida, LocalDate datum) throws Exception {
		DostupanProizvod dp = cenaRepository.getCount(idp, ida);
		
		int brojPenala = pacijentRepository.getNumOfPenalities(idpa);
		if(brojPenala >= 3) {
			throw new Exception("Imate " + brojPenala + " penala, rezervacije su vam onemogucene do 1. u sledecem mesecu.");
		}
		
		if(dp.getKolicina() == 0) {
			throw new Exception("Trenutno na stanju nema vise tog leka, molimo, pokusajte kasnije.");
		}
		
		if(datum.compareTo(LocalDate.now()) <= 0) {
			throw new Exception("Datum preuzimanja mora biti u buducnosti.");
		}
		
		dp.setKolicina(dp.getKolicina() - 1);
		Preparat p = preparatRepository.findById(idp).get();
		Pacijent pa = pacijentRepository.findById(idpa).get();
		Rezervacija rez = new Rezervacija(StatusRezervacije.REZERVISANO, datum, pa, p);
		
		rezervacijaRepository.save(rez);
		pa.addRezervacija(rez);
		pacijentRepository.save(pa);
		dostupanRepo.save(dp);
		return rez;
	}
}
