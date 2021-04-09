package rest.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Component;

import rest.domain.*;

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
	@Autowired
	private AkcijaPromocijaRepository akcijaRepo;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		TipKorisnika tk1 = new TipKorisnika("REGULAR", 0, 1);
		tipKorisnikaRepo.save(tk1);
		
		Lokacija l1 = new Lokacija(45.253836, 19.807212, "Vladike Cirica 27, Novi Sad");
		Lokacija l2 = new Lokacija(45.245143, 19.812051, "Jovana Popovica 9, Novi Sad");
		Lokacija l3 = new Lokacija(45.253836, 19.807212, "Vladike Cirica 27, Novi Sad");
		Lokacija l4 = new Lokacija(45.245143, 19.812051, "Jovana Popovica 9, Novi Sad");
		Lokacija l5 = new Lokacija(45.253836, 19.807212, "Vladike Cirica 27, Novi Sad");
		Lokacija l6 = new Lokacija(45.245143, 19.812051, "Jovana Popovica 9, Novi Sad");
		Lokacija l7 = new Lokacija(45.253836, 19.807212, "Vladike Cirica 27, Novi Sad");
		Lokacija l8 = new Lokacija(45.245143, 19.812051, "Jovana Popovica 9, Novi Sad");
		Lokacija l9 = new Lokacija(45.253836, 19.807212, "Vladike Cirica 27, Novi Sad");
		lokacijaRepo.save(l1);
		lokacijaRepo.save(l2);
		lokacijaRepo.save(l3);
		lokacijaRepo.save(l4);
		lokacijaRepo.save(l5);
		lokacijaRepo.save(l6);
		lokacijaRepo.save(l7);
		lokacijaRepo.save(l8);
		lokacijaRepo.save(l9);
		
		
		Apoteka a1 = new Apoteka("Benu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 3, 8, l2);
		Apoteka a2 = new Apoteka("Lilly", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 0, 0, l1);
		Apoteka a3 = new Apoteka("Moja apoteka", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 1, 1, l3);
		apotekaRepo.save(a1);
		apotekaRepo.save(a2);
		apotekaRepo.save(a3);
		
		AdminApoteke adma1 = new AdminApoteke("Stefan", "Bacic", "admin", "admin", "adm@gmail.com", true, "123123123", l1, ZaposlenjeKorisnika.ADMIN_APOTEKE, a1);
		korisnici.save(adma1);
		a1.addAdmin(adma1);
		apotekaRepo.save(a1);
		
		AdminApoteke adma2 = new AdminApoteke("Marko", "Cupic", "marko", "marko", "adm@gmail.com", true, "123123123", l1, ZaposlenjeKorisnika.ADMIN_APOTEKE, a2);
		korisnici.save(adma2);
		a2.addAdmin(adma2);
		apotekaRepo.save(a2);
		
		AdminApoteke adma3 = new AdminApoteke("Petar", "Markovic", "peki1", "peki1", "adm3@gmail.com", true, "123123123", l1, ZaposlenjeKorisnika.ADMIN_APOTEKE, a3);
		korisnici.save(adma3);
		a3.addAdmin(adma3);
		apotekaRepo.save(a3);

		AdminSistema as1 = new AdminSistema("Petar", "Markovic", "peki2", "peki2", "admsys@gmail.com", true, "123123123", l1, ZaposlenjeKorisnika.ADMIN_SISTEMA);
		korisnici.save(as1);

		Pacijent p1 =  new Pacijent("Ivan", "Mrsulja", "ivan", "ivan","email@gmail.com",true,"069069069",ZaposlenjeKorisnika.PACIJENT, l4, StatusNaloga.AKTIVAN, 200, tk1);
		Pacijent p2 =  new Pacijent("Ivan", "Ivanovic", "ivan1", "ivan1","email@gmail.com",true,"069887557",ZaposlenjeKorisnika.PACIJENT, l5, StatusNaloga.AKTIVAN, 0, tk1);
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
		
		
		Dobavljac d = new Dobavljac("Pera", "Peric", "dobavljac", "dobavljac", "pera@gmail.com", true, "069655655", null, ZaposlenjeKorisnika.DOBAVLJAC);
		Narudzbenica n = new Narudzbenica(LocalDate.parse("2020-04-07"), null);
		korisnici.save(d);
		narudzbenicaRepo.save(n);
		adminRepo.save(new Ponuda(StatusPonude.CEKA_NA_ODGOVOR, 400.23, LocalDate.parse("2020-04-04"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.PRIHVACENA, 500.23, LocalDate.parse("2020-04-07"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.ODBIJENA, 500.23, LocalDate.parse("2020-04-06"), n, d));
		
		Dermatolog d1=new Dermatolog("Dusan", "Antic", "dusan123", "dusan123","email",true,"telefon",l6,ZaposlenjeKorisnika.DERMATOLOG,5,24);		
		Dermatolog d2=new Dermatolog("Pera", "Peric", "pera123", "pera123","email",true,"telefon",l7,ZaposlenjeKorisnika.DERMATOLOG,7,10);		
		korisnici.save(d1);
		korisnici.save(d2);
		

		Zaposlenje z1 = new Zaposlenje(LocalTime.parse("09:00"), LocalTime.parse("17:00"), a1, d1);
		Zaposlenje z2 = new Zaposlenje(LocalTime.parse("09:00"), LocalTime.parse("17:00"), a1, d2);
		Zaposlenje z3 = new Zaposlenje(LocalTime.parse("09:00"), LocalTime.parse("17:00"), a2, d2);
		zaposlenjeRepo.save(z1);
		zaposlenjeRepo.save(z2);
		zaposlenjeRepo.save(z3);
		d1.addZaposlenje(z1);
		d2.addZaposlenje(z2);
		d2.addZaposlenje(z3);
		
		korisnici.save(d1);
		korisnici.save(d2);

		Farmaceut f1=new Farmaceut("Marko", "Markovic", "farmaceut", "farmaceut","email",true,"telefon",l8,ZaposlenjeKorisnika.FARMACEUT,0,0, null);		
		Farmaceut f2=new Farmaceut("Pera", "Petrovic", "pera123", "pera123","email",true,"telefon",l9,ZaposlenjeKorisnika.FARMACEUT,0,0, null);		
		korisnici.save(f1);
		korisnici.save(f2);
		
		Zaposlenje z4 = new Zaposlenje(LocalTime.parse("09:00"), LocalTime.parse("17:00"), a1, f1);
		Zaposlenje z5 = new Zaposlenje(LocalTime.parse("09:00"), LocalTime.parse("17:00"), a2, f2);
		zaposlenjeRepo.save(z4);
		zaposlenjeRepo.save(z5);
		f1.setZaposlenje(z4);
		f2.setZaposlenje(z5);
		korisnici.save(f1);
		korisnici.save(f2);
		
		Preparat pr1 = new Preparat("Alirex", TipLeka.ANTIHISTAMINIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 2, 200, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 0, 0);
		Preparat pr2 = new Preparat("Andol", TipLeka.ANESTETIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 3, 300, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 3, 15);
		Preparat pr3 = new Preparat("Block Max", TipLeka.ANESTETIK, "Kontraindikacije.", "Lorem ipsum dolor sit amet.", 3, 400, "okrugao", "Galenika", RezimIzdavanja.BEZ_RECEPTA, 2, 4);
		preparatRepo.save(pr1);
		preparatRepo.save(pr2);
		pr3.addZamenskiPreparat(pr2);
		preparatRepo.save(pr3);
		
		p1.getAlergije().add(pr1);
		korisnici.save(p1);
		p1.getAlergije().add(pr2);
		korisnici.save(p1);
		p2.getAlergije().add(pr3);
		korisnici.save(p2);
		
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
		
		Rezervacija rez1 = new Rezervacija(StatusRezervacije.REZERVISANO, LocalDate.parse("2021-04-07"), p1, pr1);
		Rezervacija rez2 = new Rezervacija(StatusRezervacije.REZERVISANO, LocalDate.now(), p2, pr1);
		rezervacijaRepo.save(rez1);
		rezervacijaRepo.save(rez2);
		p1.addRezervacija(rez1);
		p1.addRezervacija(rez2);
		korisnici.save(p1);
		
		Pregled pre1 = new Pregled("", StatusPregleda.ZAKAZAN, TipPregleda.PREGLED, LocalDate.parse("2020-04-07"), LocalTime.parse("09:00"), 45, 5000, d1, p1, a1);
		Pregled pre2 = new Pregled("", StatusPregleda.ZAKAZAN, TipPregleda.SAVJETOVANJE, LocalDate.parse("2020-04-08"), LocalTime.parse("13:00"), 45, 4000, f1, p1, a1);
		Pregled pre3 = new Pregled("Lorem ipsum solor sit amet.", StatusPregleda.ZAVRSEN, TipPregleda.PREGLED, LocalDate.parse("2020-04-09"), LocalTime.parse("10:00"), 45, 5500, d1, p1, a1);
		Pregled pre4 = new Pregled("Lorem ipsum dolor sit amet.", StatusPregleda.ZAVRSEN, TipPregleda.SAVJETOVANJE, LocalDate.parse("2020-04-11"), LocalTime.parse("11:00"), 45, 5700, f2, p1, a2);
		Pregled pre5 = new Pregled("", StatusPregleda.SLOBODAN, TipPregleda.PREGLED, LocalDate.now(), LocalTime.parse("09:00"), 45, 5000, d1, null, a1);
		Pregled pre6 = new Pregled("", StatusPregleda.SLOBODAN, TipPregleda.PREGLED, LocalDate.parse("2020-04-13"), LocalTime.parse("13:00"), 45, 4000, d2, null, a2);
		Pregled pre7 = new Pregled("", StatusPregleda.SLOBODAN, TipPregleda.PREGLED, LocalDate.parse("2020-04-13"), LocalTime.parse("13:00"), 30, 4000, d2, null, a1);
		
		pregledRepo.save(pre1);
		pregledRepo.save(pre2);
		pregledRepo.save(pre3);
		pregledRepo.save(pre4);
		pregledRepo.save(pre5);
		pregledRepo.save(pre6);
		pregledRepo.save(pre7);
		
		AkcijaPromocija ap1 = new AkcijaPromocija("Lorem ipsum dolor sit amet.", adma1);
		AkcijaPromocija ap2 = new AkcijaPromocija("Lorem ipsum dolor sit amet.", adma2);
		akcijaRepo.save(ap1);
		akcijaRepo.save(ap2);
		
		p1.addApoteka(a1);
		p2.addApoteka(a2);
		
		LocalDate ld = LocalDate.now();
		Cena cena = new Cena(a1, ld);
		Set<DostupanProizvod> dostupni_proizvodi = new HashSet<DostupanProizvod>();
		DostupanProizvod dp1 = new DostupanProizvod(4, 1000, pr1);
		DostupanProizvod dp2 = new DostupanProizvod(7, 600, pr2);
		dostupni_proizvodi.add(dp1);
		dostupni_proizvodi.add(dp2);
		cena.setDostupniProizvodi(dostupni_proizvodi);
		a1.addCena(cena);
		
		apotekaRepo.save(a1);
		apotekaRepo.save(a2);
	}

}
