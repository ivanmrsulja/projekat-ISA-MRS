package rest.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.ERecept;
import rest.domain.Farmaceut;
import rest.domain.OcenaApoteke;
import rest.domain.OcenaPreparata;
import rest.domain.OcenaZaposlenog;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.EReceptRepository;
import rest.repository.FarmaceutRepository;
import rest.repository.OcenaApotekeRepository;
import rest.repository.OcenaPreparataRepository;
import rest.repository.OcenaZaposlenogRepository;
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
	private DermatologRepository dermatologRepo;
	private FarmaceutRepository farmaceutRepo;
	private OcenaZaposlenogRepository ocenaZaposlenogRepo;
	
	@Autowired
	public OcenaServiceImpl(PregledRepository pr, RezervacijaRepository rr, OcenaApotekeRepository oar, OcenaPreparataRepository opr, PacijentRepository pacijentiRepo, ApotekeRepository apotekeRepo, PreparatRepository pre, EReceptRepository eReceptRepo, DermatologRepository dermatologRepo, FarmaceutRepository farmaceutRepo, OcenaZaposlenogRepository oz) {
		this.preglediRepo = pr;
		this.rezervacijeRepo = rr;
		this.oceneApotekeRepo = oar;
		this.ocenePreparataRepo = opr;
		this.pacijentiRepo = pacijentiRepo;
		this.apotekeRepo = apotekeRepo;
		this.preparatiRepo = pre;
		this.eReceptRepo = eReceptRepo;
		this.dermatologRepo = dermatologRepo;
		this.farmaceutRepo = farmaceutRepo;
		this.ocenaZaposlenogRepo = oz;
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
		Collection<Pregled> pregledi = dermatologRepo.getExaminationsForPatientAndDermatologist(idDermatologa, idPacijenta);
		System.out.println("AAAAAAAAAA" + pregledi.size());
		if (pregledi.size() == 0) {
			return -1;
		}
		OcenaZaposlenog ocena = ocenaZaposlenogRepo.ocenaZaKorisnika(idPacijenta, idDermatologa);
		if (ocena != null) {
			return ocena.getOcena();
		}
		return 0;
	}

	@Override
	public int ocenjivFarmaceut(int idFarmaceuta, int idPacijenta) {
		Collection<Pregled> pregledi = farmaceutRepo.getConsultmentsForPatientAndPharmacist(idFarmaceuta, idPacijenta);
		if (pregledi.size() == 0) {
			return -1;
		}
		OcenaZaposlenog ocena = ocenaZaposlenogRepo.ocenaZaKorisnika(idPacijenta, idFarmaceuta);
		if (ocena != null) {
			return ocena.getOcena();
		}
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
			Apoteka a = null;
			Pacijent p = null;
			Optional<Apoteka> aOptional = apotekeRepo.findById(idApoteke);
			Optional<Pacijent> pOptional = pacijentiRepo.findById(idPacijenta);

			if (!aOptional.isPresent() || !pOptional.isPresent())
				return;

			a = aOptional.get();
			p = pOptional.get();

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
		Collection<Pregled> pregledi = dermatologRepo.getExaminationsForPatientAndDermatologist(idDermatologa, idPacijenta);
		//System.out.println(pregledi.size());

		if (pregledi.size() == 0) {
			throw new Exception("Nemate mogucnost ocenjivanja ovog dermatologa.");
		}

		OcenaZaposlenog staraOcena = ocenaZaposlenogRepo.ocenaZaKorisnika(idPacijenta, idDermatologa);
		Dermatolog d = null;
		Optional<Dermatolog> dOptional = dermatologRepo.findById(idDermatologa);

		if (dOptional.isPresent())
			d = dOptional.get();
		else
			return;

		if (staraOcena != null) {
			d.setSumaOcena(d.getSumaOcena() - staraOcena.getOcena() + ocena);
			d.setOcena(d.izracunajOcenu());
			staraOcena.setOcena(ocena);
			ocenaZaposlenogRepo.save(staraOcena);
			dermatologRepo.save(d);
		} else {
			Pacijent p = null;
			Optional<Pacijent> pOptional = pacijentiRepo.findById(idPacijenta);

			if (pOptional.isPresent())
				p = pOptional.get();
			else
				return;

			OcenaZaposlenog ocenaNova = new OcenaZaposlenog(ocena, p, d);

			d.setBrojOcena(d.getBrojOcena() + 1);
			d.setSumaOcena(d.getSumaOcena() + ocena);
			d.setOcena(d.izracunajOcenu());

			ocenaZaposlenogRepo.save(ocenaNova);
			dermatologRepo.save(d);
		}
	}

	@Override
	public void oceniFarmaceuta(int idFarmaceuta, int idPacijenta, int ocena) throws Exception {
		Collection<Pregled> pregledi = farmaceutRepo.getConsultmentsForPatientAndPharmacist(idFarmaceuta, idPacijenta);

		if (pregledi.size() == 0) {
			throw new Exception("Nemate mogucnost ocenjivanja ovog dermatologa.");
		}

		OcenaZaposlenog staraOcena = ocenaZaposlenogRepo.ocenaZaKorisnika(idPacijenta, idFarmaceuta);
		Farmaceut f = null;
		Optional<Farmaceut> fOptional = farmaceutRepo.findById(idFarmaceuta);

		if (fOptional.isPresent())
			f = fOptional.get();
		else
			return;

		if (staraOcena != null) {
			f.setSumaOcena(f.getSumaOcena() - staraOcena.getOcena() + ocena);
			f.setOcena(f.izracunajOcenu());
			staraOcena.setOcena(ocena);
			ocenaZaposlenogRepo.save(staraOcena);
			farmaceutRepo.save(f);
		} else {
			Pacijent p = null;
			Optional<Pacijent> pOptional = pacijentiRepo.findById(idPacijenta);

			if (pOptional.isPresent())
				p = pOptional.get();
			else
				return;

			OcenaZaposlenog ocenaNova = new OcenaZaposlenog(ocena, p, f);

			f.setBrojOcena(f.getBrojOcena() + 1);
			f.setSumaOcena(f.getSumaOcena() + ocena);
			f.setOcena(f.izracunajOcenu());

			ocenaZaposlenogRepo.save(ocenaNova);
			farmaceutRepo.save(f);
		}
		
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
		} else {
			Pacijent p = null;
			Preparat pr = null;

			Optional<Pacijent> pOptional = pacijentiRepo.findById(idPacijenta);
			Optional<Preparat> prOptional = preparatiRepo.findById(idPreparata);

			if (!pOptional.isPresent() || !prOptional.isPresent())
				return;

			p = pOptional.get();
			pr = prOptional.get();

			OcenaPreparata nova = new OcenaPreparata(ocena, p, pr);

			pr.setBrojOcena(pr.getBrojOcena() + 1);
			pr.setSumaOcena(pr.getSumaOcena() + nova.getOcena());
			pr.setOcena(pr.izracunajOcenu());

			ocenePreparataRepo.save(nova);
			preparatiRepo.save(pr);
		}
	}

}
