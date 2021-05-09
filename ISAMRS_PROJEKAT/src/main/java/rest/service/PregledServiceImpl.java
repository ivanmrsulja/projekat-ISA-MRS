package rest.service;

import java.time.Instant;
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
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.repository.ApotekeRepository;
import rest.repository.KorisnikRepository;
import rest.repository.PacijentRepository;
import rest.repository.PregledRepository;

@Service
@Transactional
public class PregledServiceImpl implements PregledService {
	
	private ApotekaService apotekeServ;
	private PregledRepository preglediRepo;
	private PacijentRepository pacijentiRepo;
	private KorisnikRepository korisnikRepo;
	private ApotekeRepository apotekaRepo;
	
	private Environment env;
	private JavaMailSender javaMailSender;
	
	@Autowired
	public PregledServiceImpl(ApotekaService apotekeServ, PregledRepository preglediRepo, PacijentRepository pacijentiRepo, Environment env, JavaMailSender jms , KorisnikRepository kr,ApotekeRepository ar) {
		this.apotekeServ = apotekeServ;
		this.preglediRepo = preglediRepo;
		this.pacijentiRepo = pacijentiRepo;
		this.env = env;
		this.javaMailSender = jms;
		this.korisnikRepo=kr;
		this.apotekaRepo=ar;
	}

	@Override
	public Collection<PregledDTO> zakaziPregled(int idp, int idpa) throws Exception {
		Pregled p = preglediRepo.findOneById(idp);
		
		if(p.getStatus().equals(StatusPregleda.ZAKAZAN)) {
			throw new Exception("Termin je vec rezervisan.");
		}

		Collection<Pregled> svi = preglediRepo.rezervacijeZaKorisnika(idpa);
		int s2 = (p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000);
		int e2 = s2 + p.getTrajanje() * 60000;
		for(Pregled pr : svi){
			int s1 = (pr.getVrijeme().getHour() * 3600000 + pr.getVrijeme().getMinute() * 60000);
			int e1 = s1 + pr.getTrajanje() * 60000;
			if (pr.getDatum().equals(p.getDatum())){
				if ( ( s2 >= s1 && s2 <= e1 && e2 >= s1 && e2 <= e1 ) || ( s1 >= s2 && s1 <= e2 && e1 >= s2 && e1 <= e2 )
						|| ( s1 >= s2 && s1 <= e2 && e1 >= e2 ) || ( s2 >= s1 && s2 <= e1 && e2 >= e1 ) ){
					throw new Exception("U tom terminu imate vec rezervisan pregled/savetovanje.");
				}
			}
		}
		int brojPenala = pacijentiRepo.getNumOfPenalities(idpa);
		if(brojPenala >= 3) {
			throw new Exception("Imate " + brojPenala + " penala, rezervacije su vam onemogucene do 1. u sledecem mesecu.");
		}
		Pacijent pa = pacijentiRepo.findById(idpa).get();
		pa.addPregled(p);
		p.setStatus(StatusPregleda.ZAKAZAN);
		p.setCijena(p.getCijena() * pa.getTipKorisnika().getPopust());
		pacijentiRepo.save(pa);
		return apotekeServ.getPregledi(p.getApoteka().getId(), "none");
	}

	@Override
	public void otkaziPregled(int idp) throws Exception{
		Pregled p = preglediRepo.findById(idp).get();
		Instant instant = p.getDatum().atStartOfDay(ZoneId.systemDefault()).toInstant();	
		long timeInMillis = instant.toEpochMilli();
		long timeMillis = p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000;
		long time = timeInMillis + timeMillis;
		
		if(time <= System.currentTimeMillis() + 86400000) {
			throw new Exception("Isteklo je vreme za otkazivanje.");
		}
		Pacijent pa = pacijentiRepo.findById(p.getPacijent().getId()).get();
		pa.removePregled(p);
		if(p.getTip().equals(TipPregleda.PREGLED)) {
			p.setStatus(StatusPregleda.SLOBODAN);
			preglediRepo.save(p);
			pacijentiRepo.save(pa);
		}else {
			preglediRepo.delete(p);
		}
	}

	@Override
	@Async
	public void sendConfirmationEmail(KorisnikDTO user) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Uspesno zakazan pregled.");
        mail.setText("Pozdrav " + user.getIme() + " " + user.getPrezime() + ",\n\nUspesno ste zakazali pregled.\n\nSrdacan pozdrav,\nTim 06 :)");
        javaMailSender.send(mail);
		
	}
	

	@Override
	@Async
	public void sendConfirmationEmailAdv(KorisnikDTO user, Pregled p) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Uspesno zakazano savetovanje.");
        mail.setText("Pozdrav " + user.getIme() + " " + user.getPrezime() + ",\n\nUspesno ste zakazali savetovanje za " + p.getDatum() + " u " + p.getVrijeme() + ". Broj savetovanja je: " + p.getId() + "\n\nSrdacan pozdrav,\nTim 06 :)");
        javaMailSender.send(mail);
		
	}
	

	public Collection<Pregled> dobaviZaDermatologa(int id){
		return preglediRepo.preglediZaDermatologa(id);
	}
	
	public Pregled dobaviPregledZa(Integer id){
		return preglediRepo.findById(id).get();
	}
	
	public void makeNewExam (PregledDTO p,int apotekaId,int korisnikId,int pacijentId) throws Exception{
		Pregled pre=new Pregled(p.getIzvjestaj(), StatusPregleda.ZAKAZAN, TipPregleda.PREGLED, p.getDatum(), p.getVrijeme(), 45,
				p.getCijena(),korisnikRepo.findById(korisnikId).get(), pacijentiRepo.findById(pacijentId).get(),apotekaRepo.findById(apotekaId).get());
		
		
		Pacijent pacijent=pacijentiRepo.findById(pacijentId).get();
		pacijent.addPregled(pre);
		Apoteka apoteka=apotekaRepo.findById(apotekaId).get();
		apoteka.addPregled(pre);
		preglediRepo.save(pre);
		pacijentiRepo.save(pacijent);
		apotekaRepo.save(apoteka);
	}

}
