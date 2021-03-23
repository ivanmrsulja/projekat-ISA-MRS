package rest.repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import rest.domain.Apoteka;
import rest.domain.Dermatolog;
import rest.domain.Dobavljac;
import rest.domain.Korisnik;
import rest.domain.Lokacija;
import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.StatusNaloga;
import rest.domain.StatusPonude;
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
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Lokacija l1 = new Lokacija(45.2474, 19.85112, "Bulevar Cara Lazara 3, Novi Sad");
		Lokacija l2 = new Lokacija(45.2474, 19.85112, "Bulevar Cara Lazara 4, Novi Sad");
		lokacijaRepo.save(l1);
		lokacijaRepo.save(l2);
		
		Apoteka a1 = new Apoteka("Benu", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 3, 8, l1);
		Apoteka a2 = new Apoteka("Lilly", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 0, 0, l2);
		apotekaRepo.save(a1);
		apotekaRepo.save(a2);
		
		Pacijent p1 =  new Pacijent("Ivan", "Mrsulja", "ivan", "ivan","email@gmail.com",true,"069069069",ZaposlenjeKorisnika.PACIJENT, l1, StatusNaloga.AKTIVAN, 0, null);
		Pacijent p2 =  new Pacijent("Ivan", "Ivanovic", "ivan1", "ivan1","email@gmail.com",true,"069887557",ZaposlenjeKorisnika.PACIJENT, l2, StatusNaloga.AKTIVAN, 0, null);
		korisnici.save(p1);
		korisnici.save(p2);
		
		Dobavljac d = new Dobavljac("Pera", "Peric", "perica99", "perhan123", "pera@gmail.com", true, "069655655", null, ZaposlenjeKorisnika.DOBAVLJAC);
		Narudzbenica n = new Narudzbenica(1, LocalDate.parse("2020-04-07"), null);
		korisnici.save(d);
		narudzbenicaRepo.save(n);
		adminRepo.save(new Ponuda(StatusPonude.CEKA_NA_ODGOVOR, 400.23, LocalDate.parse("2020-04-04"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.PRIHVACENA, 500.23, LocalDate.parse("2020-04-07"), n, d));
		adminRepo.save(new Ponuda(StatusPonude.ODBIJENA, 500.23, LocalDate.parse("2020-04-06"), n, d));
		
		Dermatolog d1=new Dermatolog("Dusan", "Antic", "dusan123", "dusan123","email",true,"telefon",l1,ZaposlenjeKorisnika.DERMATOLOG,0,0);		
		Dermatolog d2=new Dermatolog("Pera", "Peric", "pera123", "pera123","email",true,"telefon",l1,ZaposlenjeKorisnika.DERMATOLOG,0,0);
		
		korisnici.save(d1);
		korisnici.save(d2);
	}

}
