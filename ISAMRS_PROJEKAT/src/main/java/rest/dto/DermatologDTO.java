package rest.dto;

import java.util.ArrayList;

import rest.domain.Dermatolog;
import rest.domain.Zaposlenje;

public class DermatologDTO implements Comparable<DermatologDTO>{

	private KorisnikDTO korisnik;
	private double ocena;
	private ArrayList<String> naziviApoteka = new ArrayList<String>();
	private String kriterijum;

	public DermatologDTO() {
		super();
		kriterijum = "IME";
	}

	public DermatologDTO(Dermatolog d) {
		this.korisnik = new KorisnikDTO(d);
		this.ocena = d.getOcena();
		for (Zaposlenje z : d.getZaposlenja())
			naziviApoteka.add(z.getApoteka().getNaziv());
		kriterijum = "IME";
	}

	public DermatologDTO(Dermatolog d, String kriterijum) {
		this.korisnik = new KorisnikDTO(d);
		this.ocena = d.getOcena();
		for (Zaposlenje z : d.getZaposlenja())
			naziviApoteka.add(z.getApoteka().getNaziv());
		this.kriterijum = kriterijum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DermatologDTO other = (DermatologDTO) obj;
		if (other.getKorisnik().getId() == this.korisnik.getId()) {
			return true;
		}
		return false;
	}

	public KorisnikDTO getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(KorisnikDTO korisnik) {
		this.korisnik = korisnik;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public ArrayList<String> getNaziviApoteka() {
		return naziviApoteka;
	}

	public void setNaziviApoteka(ArrayList<String> naziviApoteka) {
		this.naziviApoteka = naziviApoteka;
	}

	@Override
	public int compareTo(DermatologDTO f) {
		if (this.kriterijum.equals("IME"))
			return this.korisnik.getIme().compareTo(f.korisnik.getIme());
		else if (this.kriterijum.equals("PREZIME"))
			return this.korisnik.getPrezime().compareTo(f.korisnik.getPrezime());
		else
			return Double.toString(this.getOcena()).compareTo(Double.toString(f.getOcena()));
	}

}
