package rest.dto;

import rest.domain.Dermatolog;
import java.util.ArrayList;
import rest.domain.Zaposlenje;

public class DermatologDTO extends KorisnikDTO implements Comparable<DermatologDTO>{
	
	private double ocjena;
	private ArrayList<String> naziviApoteka = new ArrayList<String>();
	private String kriterijum;
	
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
