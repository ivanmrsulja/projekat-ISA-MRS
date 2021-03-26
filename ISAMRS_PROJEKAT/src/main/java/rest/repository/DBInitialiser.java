package rest.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.Dobavljac;
import rest.domain.ERecept;
import rest.domain.Farmaceut;

import rest.domain.Lokacija;
import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Penal;
import rest.domain.Ponuda;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.domain.RezimIzdavanja;
import rest.domain.StatusERecepta;
import rest.domain.StatusNaloga;
import rest.domain.StatusPonude;
import rest.domain.StatusPregleda;
import rest.domain.StatusRezervacije;
import rest.domain.StavkaRecepta;
import rest.domain.TipKorisnika;
import rest.domain.TipLeka;
import rest.domain.TipPregleda;
import rest.domain.Zaposlenje;
import rest.domain.ZaposlenjeKorisnika;

@Component
public class DBInitialiser implements ApplicationRunner {
	
	@Autowired
	private KorisnikRepository korisnici;
	@Autowired
	private PonudaRepository adminRepo;
	@Autowired
	private NarudzbenicaRepozitory narudzbenicaRepo;
	@Autowired
	private LokacijaRepository lokacijaRepo;
	@Autowired
	private ApotekeRepository apotekaRepo;
	@Autowired
	private ZaposlenjeRepository zaposlenjeRepo;
	@Autowired
	private TipKorisnikaRepository tipKorisnikaRepo;
	@Autowired
	private PenalRepository penaliRepo;
	@Autowired
	private PreparatRepository preparatRepo;
	@Autowired
	private StavkaReceptaRepository stavkaReceptaRepo;
	@Autowired
	private EReceptRepository eaReceptiRepo;
	@Autowired
	private RezervacijaRepository rezervacijaRepo;
	@Autowired
	private PregledRepository pregledRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		TipKorisnika tk1 = new TipKorisnika("REGULAR", 0, 1);
		tipKorisnikaRepo.save(tk1);
		
		Lokacija l1 = new Lokacija(45.2474, 19.85112, "Bulevar Cara Lazara 3, Novi Sad");
		Lokacija l2 = new Lokacija(45.2474, 19.85112, "Bulevar Cara Lazara 4, Novi Sad");
		lokacijaRepo.save(l1);
		lokacijaRepo.save(l2);
		
		Apoteka a1 = new Apoteka("Benu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 3, 8, l1);
		Apoteka a2 = new Apoteka("Lilly", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 0, 0, l2);
		apotekaRepo.save(a1);
		apotekaRepo.save(a2);
		
		Pacijent p1 =  new Pacijent("Ivan", "Mrsulja", "ivan", "ivan","email@gmail.com",true,"069069069",ZaposlenjeKorisnika.PACIJENT, l1, StatusNaloga.AKTIVAN, 0, tk1);
		Pacijent p2 =  new Pacijent("Ivan", "Ivanovic", "ivan1", "ivan1","email@gmail.com",true,"069887557",ZaposlenjeKorisnika.PACIJENT, l2, StatusNaloga.AKTIVAN, 0, tk1);
		korisnici.save(p1);
		korisnici.save(p2);
		
		Penal pe1 = new Penal(LocalDate.now(), p1);
		Penal pe2 = new Penal(LocalDate.now(), p1);
		Penal pe3 = new Penal(LocalDate.now(), p1);
		Penal pe4 = new Penal(LocalDate.now(), p2);
		penaliRepo.save(pe1);
		penaliRepo.save(pe2);
		penaliRepo.save(pe3);
		penaliRepo.save(pe4);
		
		p1.addPenal(pe1);
		p1.addPenal(pe2);
		p1.addPenal(pe3);
		p2.addPenal(pe4);
		korisnici.save(p1);
		korisnici.save(p2);
		
		
		Dobavljac d = new Dobavljac("Pera", "Peric", "perica99", "perhan123", "pera@gmail.com", true, "069655655", null, ZaposlenjeKorisnika.DOBAVLJAC);
		Narudzbenica n = new Narudzbenica(LocalDate.parse("2020-04-07"), null);
		korisnici.save(d);
		narudzbenicaRepo.save(n);
		adminRepo.save(new Ponuda(StatusPonude.CEKA_NA_ODGOVOR, 400.23, LocalDate.parse("2020-04-04"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.PRIHVACENA, 500.23, LocalDate.parse("2020-04-07"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.ODBIJENA, 500.23, LocalDate.parse("2020-04-06"), n, d));
		
		Dermatolog d1=new Dermatolog("Dusan", "Antic", "dusan123", "dusan123","email",true,"telefon",l1,ZaposlenjeKorisnika.DERMATOLOG,0,0);		
		Dermatolog d2=new Dermatolog("Pera", "Peric", "pera123", "pera123","email",true,"telefon",l1,ZaposlenjeKorisnika.DERMATOLOG,0,0);		
		korisnici.save(d1);
		korisnici.save(d2);
		

		Zaposlenje z1 = new Zaposlenje(9, 5, a1, d1);
		Zaposlenje z2 = new Zaposlenje(9, 5, a1, d2);
		Zaposlenje z3 = new Zaposlenje(5, 22, a2, d2);
		zaposlenjeRepo.save(z1);
		zaposlenjeRepo.save(z2);
		zaposlenjeRepo.save(z3);
		d1.addZaposlenje(z1);
		d2.addZaposlenje(z2);
		d2.addZaposlenje(z3);
		
		korisnici.save(d1);
		korisnici.save(d2);

		Farmaceut f1=new Farmaceut("Marko", "Markovic", "marko123", "marko123","email",true,"telefon",l1,ZaposlenjeKorisnika.FARMACEUT,0,0,new Zaposlenje());		
		Farmaceut f2=new Farmaceut("Pera", "Petrovic", "pera123", "pera123","email",true,"telefon",l1,ZaposlenjeKorisnika.FARMACEUT,0,0,new Zaposlenje());		
		korisnici.save(f1);
		korisnici.save(f2);
		
		Preparat pr1 = new Preparat("Alirex", TipLeka.ANTIHISTAMINIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 2, 200, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 0, 0);
		Preparat pr2 = new Preparat("Andol", TipLeka.ANESTETIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 3, 300, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 3, 15);
		Preparat pr3 = new Preparat("Block Max", TipLeka.ANESTETIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 3, 400, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 2, 4);
		preparatRepo.save(pr1);
		preparatRepo.save(pr2);
		pr3.addZamenskiPreparat(pr2);
		preparatRepo.save(pr3);
		
		ERecept er1 = new ERecept(LocalDate.parse("2020-03-07"), p1, StatusERecepta.NOV);
		ERecept er2 = new ERecept(LocalDate.parse("2020-03-03"), p1, StatusERecepta.OBRADJEN);
		eaReceptiRepo.save(er1);
		eaReceptiRepo.save(er2);
		
		StavkaRecepta sr1R1 = new StavkaRecepta(2, pr1);
		StavkaRecepta sr2R1 = new StavkaRecepta(3, pr2);
		StavkaRecepta sr1R2 = new StavkaRecepta(1, pr1);
		stavkaReceptaRepo.save(sr1R1);
		stavkaReceptaRepo.save(sr2R1);
		stavkaReceptaRepo.save(sr1R2);
		
		er1.getStavkaRecepata().add(sr1R1);
		er1.getStavkaRecepata().add(sr2R1);
		er2.getStavkaRecepata().add(sr1R2);
		eaReceptiRepo.save(er1);
		eaReceptiRepo.save(er2);
		
		Rezervacija rez1 = new Rezervacija(StatusRezervacije.REZERVISANO, LocalDate.parse("2020-04-07"), p1, pr1);
		Rezervacija rez2 = new Rezervacija(StatusRezervacije.PODIGNUTO, LocalDate.parse("2020-03-07"), p2, pr1);
		rezervacijaRepo.save(rez1);
		rezervacijaRepo.save(rez2);
		p1.addRezervacija(rez1);
		p1.addRezervacija(rez2);
		korisnici.save(p1);
		
		Pregled pre1 = new Pregled("", StatusPregleda.ZAKAZAN, TipPregleda.PREGLED, LocalDate.parse("2020-04-07"), LocalTime.parse("13:00"), 45, 5000, d1, p1, a1);
		pregledRepo.save(pre1);
	}

}
