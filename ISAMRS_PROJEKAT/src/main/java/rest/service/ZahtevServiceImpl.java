package rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rest.domain.Korisnik;
import rest.domain.StatusZahtjeva;
import rest.domain.Zahtjev;
import rest.dto.KorisnikDTO;
import rest.dto.ZahtevDTO;
import rest.repository.KorisnikRepository;
import rest.repository.ZahtevRepository;

@Service
@Transactional
public class ZahtevServiceImpl implements ZahtevService {

	private ZahtevRepository zahtevRepository;
	private Environment env;
	private JavaMailSender javaMailSender;
	private KorisnikRepository korisnikRepo;
	
	@Autowired
	public ZahtevServiceImpl (ZahtevRepository zr, Environment env, JavaMailSender jms,KorisnikRepository kr) {
		this.zahtevRepository = zr;
		this.env = env;
		this.javaMailSender = jms;
		this.korisnikRepo=kr;
	}

	@Override
	public Collection<Zahtjev> findAllForPharmacy(Collection<Integer> pharmacistIds) {
		Collection<Zahtjev> zahtevi = zahtevRepository.findAll();
		ArrayList<Zahtjev> zahteviApoteke = new ArrayList<Zahtjev>();
		for (Zahtjev z : zahtevi) {
			for (Integer id : pharmacistIds) {
				if (z.getKorisnik().getId().equals(id))
					zahteviApoteke.add(z);
			}
		}
		return zahteviApoteke;
	}

	@Override
	public Zahtjev update(ZahtevDTO zahtev) throws Exception {
		Optional<Zahtjev> zahtevToUpdateOpt = zahtevRepository.findById(zahtev.getId());
		Zahtjev zahtevToUpdate = null;
		if (zahtevToUpdateOpt.isPresent()) {
			zahtevToUpdate = zahtevToUpdateOpt.get();
		} else {
			throw new Exception("Trazeni zahtev nije pronadjen.");
		}
		
		if (zahtevToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		zahtevToUpdate.setStatus(zahtev.getStatus());
		
		Zahtjev updatedZahtev = zahtevRepository.save(zahtevToUpdate);
		return updatedZahtev;
	}

	@Override
	public void notifyViaEmail(ZahtevDTO z, String obrazlozenje) {
		SimpleMailMessage mail = new SimpleMailMessage();
		KorisnikDTO f = z.getKorisnik();
        mail.setTo(f.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Promena statusa zahteva");
        String stanjeZahteva = (z.getStatus() == StatusZahtjeva.ODOBREN)? "odobren" : "odbijen";
        String telo = "Postovani " + f.getIme() + " " + f.getPrezime() + ",\n\nVas zahtev je " + stanjeZahteva + ".\n";
        if (z.getStatus() == StatusZahtjeva.ODBIJEN)
        	telo += "Obrazlozenje: " + obrazlozenje;
        mail.setText(telo);
        javaMailSender.send(mail);
	}

	@Override
	public void addZahtjev(Zahtjev p,int id) {
		Korisnik k=korisnikRepo.getOne(id);
		Zahtjev pp= new Zahtjev(p.getTip(),StatusZahtjeva.CEKANJE,k,p.getPocetak(), p.getKraj());		
		zahtevRepository.save(pp);
	}
}
