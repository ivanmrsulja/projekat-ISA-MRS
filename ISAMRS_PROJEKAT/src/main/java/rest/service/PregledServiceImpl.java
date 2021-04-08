package rest.service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
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
