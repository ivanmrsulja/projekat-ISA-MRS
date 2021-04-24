package rest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.Farmaceut;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.StatusPregleda;
import rest.domain.TipPregleda;
import rest.dto.ApotekaDTO;
import rest.dto.PregledDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.FarmaceutRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.ZaposlenjeRepository;
import rest.util.ApotekaSearchParams;

@Service
@Transactional
public class ApotekaServiceImpl implements ApotekaService {

	private ApotekeRepository apoteke;
	private AdminApotekeRepository admin;
	private DermatologRepository dermatolozi;
	private ZaposlenjeRepository zaposlenja;
	private PregledRepository pregledi;
	private FarmaceutRepository farmaceuti;
	private PacijentRepository pacijenti;
	private PenalRepository penali;
	
	private static final int pageSize = 10;

	@Autowired
	public ApotekaServiceImpl(ApotekeRepository ar, AdminApotekeRepository are, DermatologRepository dr, ZaposlenjeRepository zaposlenja, PregledRepository pregledi, FarmaceutRepository farmaceuti, PacijentRepository pacijenti, PenalRepository penali) {
		apoteke = ar;
		admin = are;
		dermatolozi = dr;
		this.zaposlenja = zaposlenja;
		this.pregledi = pregledi;
		this.farmaceuti = farmaceuti;
		this.pacijenti = pacijenti;
		this.penali = penali;
	}
	
	@Override
	public Page<ApotekaDTO> getAllDrugStores(int stranica, ApotekaSearchParams params, double lat, double lon) {
		Direction dir;
		if(params.isOpadajuce()) {
			dir = Direction.DESC;
		}else {
			dir = Direction.ASC;
		}
		System.out.println(lat + "-" + lon);
		Page<ApotekaDTO> allStores = apoteke.findAll(new PageRequest(stranica, pageSize, dir, params.getKriterijumSortiranja().toLowerCase()), params.getNaziv().toUpperCase(), params.getAdresa().toUpperCase(), params.getdOcena(), params.getgOcena(), lat, lon, params.getRastojanje()).map(a -> new ApotekaDTO(a));
		return allStores;
	}

	@Override
	public Apoteka getByID(int id) {
		return this.apoteke.findById(id).get();
	}

	@Override
	public void update(ApotekaDTO apoteka) throws Exception {
		Apoteka apotekaToUpdate = getByID(apoteka.getId());
		if (apotekaToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		apotekaToUpdate.setNaziv(apoteka.getNaziv());
		apotekaToUpdate.setOpis(apoteka.getOpis());
		apotekaToUpdate.setLokacija(apoteka.getLokacija());
		apoteke.save(apotekaToUpdate);
	}

	@Override
	public Apoteka getForAdmin(int id) {
		return admin.getApoteka(id);
	}

	@Override
	public Collection<PregledDTO> getPregledi(int id, String criteria) {
		Collection<Pregled> pregledi = apoteke.getPreCreated(id);
		
		ArrayList<PregledDTO> ret = new ArrayList<PregledDTO>();
		for(Pregled p : pregledi) {
			Dermatolog d = dermatolozi.findById(p.getZaposleni().getId()).get();
			ret.add(new PregledDTO(p, d.getOcena()));
		}
		
		if(criteria.equals("cena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
							
							@Override
							public int compare(PregledDTO e1, PregledDTO e2) {
								return Double.compare(e1.getCijena(), e2.getCijena());
							}
						});
		}else if(criteria.equals("ocena")) {
			Collections.sort(ret, new Comparator<PregledDTO>() {
				
				@Override
				public int compare(PregledDTO e1, PregledDTO e2) {
					return Double.compare(e2.getOcena(), e1.getOcena());
				}
			});
		}
		
		return ret;
	}

	@Override
	public Page<ApotekaDTO> apotekeZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, String criteria, int pageNum) throws Exception {
		if(datum.isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(datum.isEqual(LocalDate.now())) {
			if(vrijeme.isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		Collection<Integer> kandidati = zaposlenja.slobodniFarmaceuti(vrijeme);
		ArrayList<Apoteka> apotekeRet = new ArrayList<Apoteka>();
		long trajanjeSavetovanja = 30 * 60000;
		long mid = vrijeme.getHour() * 3600000 + vrijeme.getMinute() * 60000;
		for(int id : kandidati) {
			Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(datum, id);
			if(zauzeca.size() == 0) {
				Apoteka a = zaposlenja.apotekaZaFarmaceuta(id);
				if(!apotekeRet.contains(a)) {
					apotekeRet.add(a);
				}
			}
			for(Pregled p: zauzeca) {
				long start = p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000;
				long end = start + p.getTrajanje() * 60000;
				if(mid > end || mid < start - trajanjeSavetovanja) {
					Apoteka a = zaposlenja.apotekaZaFarmaceuta(id);
					if(!apotekeRet.contains(a)) {
						apotekeRet.add(a);
					}
				}
			}
		}
		
		switch (criteria) {
		case "OCENA":
			Page<ApotekaDTO> ocenaSort = ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, 1, Direction.DESC, "ocena"), apotekeRet).map(a -> new ApotekaDTO(a));
			return ocenaSort;

		case "CENA":
			Page<ApotekaDTO> cenaSort = ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, 1, Direction.ASC, "cenaSavetovanja"), apotekeRet).map(a -> new ApotekaDTO(a));
			return cenaSort;
		}
		
		return ((ApotekeRepository) apoteke).slobodneApoteke(new PageRequest(pageNum, 1), apotekeRet).map(a -> new ApotekaDTO(a));
	}

	@Override
	public Collection<Farmaceut> farmaceutiZaTerminSavetovanja(LocalDate datum, LocalTime vrijeme, int id, String criteria) throws Exception {
		if(datum.isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(datum.isEqual(LocalDate.now())) {
			if(vrijeme.isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		Collection<Integer> kandidati = zaposlenja.slobodniFarmaceutiApoteka(vrijeme, id);
		ArrayList<Farmaceut> ret = new ArrayList<Farmaceut>();
		long trajanjeSavetovanja = 30 * 60000;
		long mid = vrijeme.getHour() * 3600000 + vrijeme.getMinute() * 60000;
		for(int i : kandidati) {
			Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(datum, i);
			if(zauzeca.size() == 0) {
				Farmaceut a = farmaceuti.findById(i).get();
				if(!ret.contains(a)) {
					ret.add(a);
				}
			}
			for(Pregled p: zauzeca) {
				long start = p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000;
				long end = start + p.getTrajanje() * 60000;
				if(mid > end || mid < start - trajanjeSavetovanja) {
					Farmaceut a = farmaceuti.findById(i).get();
					if(!ret.contains(a)) {
						ret.add(a);
					}
				}
			}
		}
		switch (criteria) {
		case "OCENA":
			Collections.sort(ret, new Comparator<Farmaceut>() {

				@Override
				public int compare(Farmaceut a0, Farmaceut a1) {
					return Double.compare(a0.getOcena(), a1.getOcena());
				}
			});
			break;
		}
		return ret;
	}

	@Override
	public Pregled zakaziSavetovanje(PregledDTO podaci, int idApoteke, int idFarmaceuta, int idPacijenta) throws Exception {
		if(podaci.getDatum().isBefore(LocalDate.now())) {
			throw new Exception("Savetovanje mora biti u buducnosti");
		}
		else if(podaci.getDatum().isEqual(LocalDate.now())) {
			if(podaci.getVrijeme().isBefore(LocalTime.now())) {
				throw new Exception("Savetovanje mora biti u buducnosti");
			}
		}
		
		Collection<Pregled> zauzeca = pregledi.zauzetiFarmaceutiNaDan(podaci.getDatum(), idFarmaceuta);
		long trajanjeSavetovanja = 30 * 60000;
		long mid = podaci.getVrijeme().getHour() * 3600000 + podaci.getVrijeme().getMinute() * 60000;
		for(Pregled p: zauzeca) {
			long start = p.getVrijeme().getHour() * 3600000 + p.getVrijeme().getMinute() * 60000;
			long end = start + p.getTrajanje() * 60000;
			if(mid <= end && mid >= start - trajanjeSavetovanja) {
				throw new Exception("Farmaceut je zauzet u tom terminu.");
			}
		}
		
		Pacijent p = pacijenti.findById(idPacijenta).get();
		int brojPenala = penali.penalForUser(p.getId()).size();
		if(brojPenala >= 3) {
			throw new Exception("Imate " + brojPenala + " penala, zakazivanja su vam onemogucena do 1. u sledecem mesecu.");
		}
		Farmaceut f = farmaceuti.findById(idFarmaceuta).get();
		Apoteka a = apoteke.findById(idApoteke).get();
		Pregled novi = new Pregled("", StatusPregleda.ZAKAZAN, TipPregleda.SAVJETOVANJE, podaci.getDatum(), podaci.getVrijeme(), 30, a.getCenaSavetovanja() * p.getTipKorisnika().getPopust(), f, p, a);
		pregledi.save(novi);
		a.addPregled(novi);
		f.addPregled(novi);
		p.addPregled(novi);
		
		apoteke.save(a);
		farmaceuti.save(f);
		pacijenti.save(p);
		return novi;
	}

}