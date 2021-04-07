package rest.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;
import rest.dto.PregledDTO;
import rest.repository.PacijentRepository;
import rest.repository.PregledRepository;

@Service
public class PregledServiceImpl implements PregledService {
	
	private ApotekaService apotekeServ;
	private PregledRepository preglediRepo;
	private PacijentRepository pacijentiRepo;
	
	@Autowired
	public PregledServiceImpl(ApotekaService apotekeServ, PregledRepository preglediRepo, PacijentRepository pacijentiRepo) {
		this.apotekeServ = apotekeServ;
		this.preglediRepo = preglediRepo;
		this.pacijentiRepo = pacijentiRepo;
	}

	@Override
	@Transactional
	public Collection<PregledDTO> zakaziPregled(int idp, int idpa) throws Exception {
		Pregled p = preglediRepo.findById(idp).get();
		if(p.getStatus().equals(StatusPregleda.ZAKAZAN)) {
			throw new Exception("Termin je vec rezervisan.");
		}
		int brojPenala = pacijentiRepo.getNumOfPenalities(idpa);
		System.out.println(brojPenala + "AAAAAAAAAAAAAAAAAA");
		if(brojPenala >= 3) {
			throw new Exception("Imate " + brojPenala + " penala, rezervacije su vam onemogucene do 1. u sledecem mesecu.");
		}
		Pacijent pa = pacijentiRepo.findById(idpa).get();
		pa.addPregled(p);
		p.setStatus(StatusPregleda.ZAKAZAN);
		pacijentiRepo.save(pa);
		return apotekeServ.getPregledi(p.getApoteka().getId(), "none");
	}

	@Override
	@Transactional
	public void otkaziPregled(int idp) throws Exception{
		Pregled p = preglediRepo.findById(idp).get();
		Instant instant = p.getDatum().atStartOfDay(ZoneId.systemDefault()).toInstant();	
		long timeInMillis = instant.toEpochMilli();
		long timeMillis = p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000;
		long time = timeInMillis + timeMillis;
		
		if(time > System.currentTimeMillis() - 86400000) {
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
	
}
