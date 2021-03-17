package rest.domain;

import java.util.Set;

public class Pacijent extends Korisnik {
	private StatusNaloga statusNaloga;
	private int brojPoena;
	
	private TipKorisnika tipKorisnika;
	private Set<ERecept> eRecepti;
	private Set<Penal> penali;
	private Set<Zalba> zalbe;
	private Set<Ocena> ocene;
	private Set<Rezervacija> rezervacije;
	private Set<Preparat> alergije;
	private Set<Preparat> kupljeniPreparati;
	private Set<Apoteka> apoteke;
	private Set<Pregled> pregledi;
	
	public Pacijent() {}
	
	public Pacijent(Long id, String ime, String prezime, String username, String password, String email,
			Boolean loggedBefore, String telefon,ZaposlenjeKorisnika zaposlenjeKorisnika, Lokacija lokacija, StatusNaloga statusNaloga, int brojPoena,
			TipKorisnika tipKorisnika, Set<ERecept> eRecepti, Set<Penal> penali, Set<Zalba> zalbe, Set<Ocena> ocene,
			Set<Rezervacija> rezervacije, Set<Preparat> alergije, Set<Preparat> kupljeniPreparati, Set<Apoteka> apoteke,
			Set<Pregled> pregledi) {
		super(id, ime, prezime, username, password, email, loggedBefore, telefon, lokacija,zaposlenjeKorisnika);
		this.statusNaloga = statusNaloga;
		this.brojPoena = brojPoena;
		this.tipKorisnika = tipKorisnika;
		this.eRecepti = eRecepti;
		this.penali = penali;
		this.zalbe = zalbe;
		this.ocene = ocene;
		this.rezervacije = rezervacije;
		this.alergije = alergije;
		this.kupljeniPreparati = kupljeniPreparati;
		this.apoteke = apoteke;
		this.pregledi = pregledi;
	}

	public StatusNaloga getStatusNaloga() {
		return statusNaloga;
	}

	public void setStatusNaloga(StatusNaloga statusNaloga) {
		this.statusNaloga = statusNaloga;
	}

	public int getBrojPoena() {
		return brojPoena;
	}

	public void setBrojPoena(int brojPoena) {
		this.brojPoena = brojPoena;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public Set<ERecept> geteRecepti() {
		return eRecepti;
	}

	public void seteRecepti(Set<ERecept> eRecepti) {
		this.eRecepti = eRecepti;
	}

	public Set<Penal> getPenali() {
		return penali;
	}

	public void setPenali(Set<Penal> penali) {
		this.penali = penali;
	}

	public Set<Zalba> getZalbe() {
		return zalbe;
	}

	public void setZalbe(Set<Zalba> zalbe) {
		this.zalbe = zalbe;
	}

	public Set<Ocena> getOcene() {
		return ocene;
	}

	public void setOcene(Set<Ocena> ocene) {
		this.ocene = ocene;
	}

	public Set<Rezervacija> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<Rezervacija> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public Set<Preparat> getAlergije() {
		return alergije;
	}

	public void setAlergije(Set<Preparat> alergije) {
		this.alergije = alergije;
	}

	public Set<Preparat> getKupljeniPreparati() {
		return kupljeniPreparati;
	}

	public void setKupljeniPreparati(Set<Preparat> kupljeniPreparati) {
		this.kupljeniPreparati = kupljeniPreparati;
	}

	public Set<Apoteka> getApoteke() {
		return apoteke;
	}

	public void setApoteke(Set<Apoteka> apoteke) {
		this.apoteke = apoteke;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}
	
	
	
	
}
