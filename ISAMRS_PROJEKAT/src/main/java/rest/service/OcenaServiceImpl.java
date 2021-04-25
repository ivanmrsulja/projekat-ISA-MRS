package rest.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.ERecept;
import rest.domain.OcenaApoteke;
import rest.domain.OcenaPreparata;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.repository.ApotekeRepository;
import rest.repository.EReceptRepository;
import rest.repository.OcenaApotekeRepository;
import rest.repository.OcenaPreparataRepository;
import rest.repository.PacijentRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.RezervacijaRepository;

@Service
@Transactional
public class OcenaServiceImpl implements OcenaService {
	
	private PregledRepository preglediRepo;
	private RezervacijaRepository rezervacijeRepo;
	private OcenaApotekeRepository oceneApotekeRepo;
	private OcenaPreparataRepository ocenePreparataRepo;
	private PacijentRepository pacijentiRepo;
	private ApotekeRepository apotekeRepo;
	private PreparatRepository preparatiRepo;
	private EReceptRepository eReceptRepo;
	
	@Autowired
	public OcenaServiceImpl(PregledRepository pr, RezervacijaRepository rr, OcenaApotekeRepository oar, OcenaPreparataRepository opr, PacijentRepository pacijentiRepo, ApotekeRepository apotekeRepo, PreparatRepository pre, EReceptRepository eReceptRepo) {
		preglediRepo = pr;
		rezervacijeRepo = rr;
		oceneApotekeRepo = oar;
		ocenePreparataRepo = opr;
		this.pacijentiRepo = pacijentiRepo;
		this.apotekeRepo = apotekeRepo;
		preparatiRepo = pre;
		this.eReceptRepo = eReceptRepo;
	}
	
	@Override
	public int ocenjivaApoteka(int idApoteke, int idPacijenta) {
		Collection<Pregled> pregledi = preglediRepo.preglediUApoteci(idApoteke, idPacijenta);
		Collection<Rezervacija> rezervacije = rezervacijeRepo.rezervacijaUApoteci(idApoteke, idPacijenta);
		Collection<ERecept> eRecepti = eReceptRepo.zaApotekuIKorisnika(idPacijenta, idApoteke);
		if (pregledi.size() == 0 && rezervacije.size() == 0 && eRecepti.size() == 0) {
			return -1;
		}
		OcenaApoteke ocena = oceneApotekeRepo.zaKorisnika(idPacijenta, idApoteke);
		if (ocena != null) {
			return ocena.getOcena();
		}
		return 0;
	}

	@Override
	public int ocenjivDermatolog(int idDermatologa, int idPacijenta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ocenjivFarmaceut(int idFarmaceuta, int idPacijenta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ocenjivPreparat(int idPreparata, int idPacijenta) {
		Collection<Rezervacija> rezervacije = rezervacijeRepo.rezervacijaLijeka(idPreparata, idPacijenta);
		Collection<ERecept> eRecepti = eReceptRepo.zaPreparatIKorisnika(idPacijenta, idPreparata);
		if(rezervacije.size() == 0 && eRecepti.size() == 0) {
			return -1;
		}
		OcenaPreparata ocena = ocenePreparataRepo.zaKorisnika(idPacijenta, idPreparata);
		if (ocena != null) {
			return ocena.getOcena();
		}
		return 0;
	}

	@Override
	public void oceniApoteku(int idApoteke, int ocena, int idPacijenta) throws Exception {
		Collection<Pregled> pregledi = preglediRepo.preglediUApoteci(idApoteke, idPacijenta);
		Collection<Rezervacija> rezervacije = rezervacijeRepo.rezervacijaUApoteci(idApoteke, idPacijenta);
		Collection<ERecept> eRecepti = eReceptRepo.zaApotekuIKorisnika(idPacijenta, idApoteke);
		if (pregledi.size() == 0 && rezervacije.size() == 0 && eRecepti.size() == 0) {
			throw new Exception("Nemate mogucnost ocenjivanja ove apoteke");
		}
		OcenaApoteke staraocena = oceneApotekeRepo.zaKorisnika(idPacijenta, idApoteke);
		if (staraocena != null) {
			staraocena.getApoteka().setSumaOcena(staraocena.getApoteka().getSumaOcena() - staraocena.getOcena() + ocena);
			staraocena.getApoteka().setOcena(staraocena.getApoteka().izracunajOcenu());
			staraocena.setOcena(ocena);
			oceneApotekeRepo.save(staraocena);
		} else {
			Apoteka a = apotekeRepo.findById(idApoteke).get();
			Pacijent p = pacijentiRepo.findById(idPacijenta).get();
			OcenaApoteke nova = new OcenaApoteke(ocena, p, a);
			a.setBrojOcena(a.getBrojOcena() + 1);
			a.setSumaOcena(a.getSumaOcena() + nova.getOcena());
			a.setOcena(a.izracunajOcenu());
			oceneApotekeRepo.save(nova);
			apotekeRepo.save(a);
		}
	}

	@Override
	public void oceniDermatologa(int idDermatologa, int idPacijenta, int ocena) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oceniFarmaceuta(int idFarmaceuta, int idPacijenta, int ocena) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oceniPreparat(int idPreparata, int idPacijenta, int ocena) throws Exception {
		Collection<Rezervacija> rezervacije = rezervacijeRepo.rezervacijaLijeka(idPreparata, idPacijenta);
		Collection<ERecept> eRecepti = eReceptRepo.zaPreparatIKorisnika(idPacijenta, idPreparata);
		if(rezervacije.size() == 0 && eRecepti.size() == 0) {
			throw new Exception("Nemate mogucnost ocenjivanja ovog preparata");
		}
		OcenaPreparata staraOcena = ocenePreparataRepo.zaKorisnika(idPacijenta, idPreparata);
		if (staraOcena != null) {
			staraOcena.getPreparat().setSumaOcena(staraOcena.getPreparat().getSumaOcena() - staraOcena.getOcena() + ocena);
			staraOcena.getPreparat().setOcena(staraOcena.getPreparat().izracunajOcenu());
			staraOcena.setOcena(ocena);
			ocenePreparataRepo.save(staraOcena);
		}else {
			Pacijent p = pacijentiRepo.findById(idPacijenta).get();
			Preparat pr = preparatiRepo.findById(idPreparata).get();
			OcenaPreparata nova = new OcenaPreparata(ocena, p, pr);
			pr.setBrojOcena(pr.getBrojOcena() + 1);
			pr.setSumaOcena(pr.getSumaOcena() + nova.getOcena());
			pr.setOcena(pr.izracunajOcenu());
			ocenePreparataRepo.save(nova);
			preparatiRepo.save(pr);
		}
	}

}
