package rest.dto;

import rest.domain.Dermatolog;

import java.time.LocalTime;
import java.util.ArrayList;
import rest.domain.Zaposlenje;

public class DermatologDTO extends KorisnikDTO implements Comparable<DermatologDTO>{
	
	private double ocjena;
	private ArrayList<String> naziviApoteka = new ArrayList<String>();
	private String kriterijum;
	LocalTime pocetakRadnogVremena;
	LocalTime krajRadnogVremena;
	
	public DermatologDTO() {
		super();
		kriterijum = "IME";
	}
	public DermatologDTO(Dermatolog d) {
		super(d);
		ocjena = d.getOcena();
		for (Zaposlenje z : d.getZaposlenja())
			naziviApoteka.add(z.getApoteka().getNaziv());
		kriterijum = "IME";
	}

	public DermatologDTO(Dermatolog d, String kriterijum) {
		super(d);
		this.ocjena = d.getOcena();
		for (Zaposlenje z : d.getZaposlenja())
			naziviApoteka.add(z.getApoteka().getNaziv());
		this.kriterijum = kriterijum;
	}
	
	public double getOcjena() {
		return ocjena;
	}
	
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}

	public ArrayList<String> getNaziviApoteka() {
		return naziviApoteka;
	}

	public void setNaziviApoteka(ArrayList<String> naziviApoteka) {
		this.naziviApoteka = naziviApoteka;
	}

	public String getKriterijum() {
		return kriterijum;
	}
	public void setKriterijum(String kriterijum) {
		this.kriterijum = kriterijum;
	}
	public LocalTime getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}
	public void setPocetakRadnogVremena(LocalTime pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}
	public LocalTime getKrajRadnogVremena() {
		return krajRadnogVremena;
	}
	public void setKrajRadnogVremena(LocalTime krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((krajRadnogVremena == null) ? 0 : krajRadnogVremena.hashCode());
		result = prime * result + ((kriterijum == null) ? 0 : kriterijum.hashCode());
		result = prime * result + ((naziviApoteka == null) ? 0 : naziviApoteka.hashCode());
		long temp;
		temp = Double.doubleToLongBits(ocjena);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((pocetakRadnogVremena == null) ? 0 : pocetakRadnogVremena.hashCode());
		return result;
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
		if (other.getId() == this.getId()) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(DermatologDTO f) {
		if (this.kriterijum.equals("IME"))
			return this.getIme().compareTo(f.getIme());
		else if (this.kriterijum.equals("PREZIME"))
			return this.getPrezime().compareTo(f.getPrezime());
		else
			return Double.toString(this.getOcjena()).compareTo(Double.toString(f.getOcjena()));
	}
	
}
