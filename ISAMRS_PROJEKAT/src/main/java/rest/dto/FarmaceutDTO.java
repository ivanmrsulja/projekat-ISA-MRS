package rest.dto;

import java.time.LocalTime;

import rest.domain.Farmaceut;

public class FarmaceutDTO extends KorisnikDTO implements Comparable<FarmaceutDTO>{

	private int brojOcena;
	private int sumaOcena;
	private double ocena;
	private LocalTime pocetakRadnogVremena;
	private LocalTime krajRadnogVremena;
	private int apoteka;
	private String nazivApoteke;
	private String kriterijum;
	
	public FarmaceutDTO() {
		super();
		kriterijum = "IME";
	}
	
	public FarmaceutDTO(Farmaceut farmaceut) {
		super(farmaceut);
		ocena = farmaceut.getOcena();
		apoteka = farmaceut.getZaposlenje().getApoteka().getId();
		nazivApoteke = farmaceut.getZaposlenje().getApoteka().getNaziv();
		kriterijum = "IME";
	}

	public FarmaceutDTO(Farmaceut farmaceut, String kriterijum) {
		super(farmaceut);
		ocena = farmaceut.getOcena();
		apoteka = farmaceut.getZaposlenje().getApoteka().getId();
		nazivApoteke = farmaceut.getZaposlenje().getApoteka().getNaziv();
		this.kriterijum = kriterijum;
	}
	
	public int getBrojOcena() {
		return brojOcena;
	}
	
	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}
	
	public int getSumaOcena() {
		return sumaOcena;
	}
	
	public void setSumaOcena(int sumaOcena) {
		this.sumaOcena = sumaOcena;
	}
	
	public double getOcena() {
		return ocena;
	}
	
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}

	public String getNazivApoteke() {
		return nazivApoteke;
	}

	public void setNazivApoteke(String nazivApoteke) {
		this.nazivApoteke = nazivApoteke;
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

	public int getApoteka() {
		return apoteka;
	}
	
	public void setApoteka(int apoteka) {
		this.apoteka = apoteka;
	}

	public String getKriterijum() {
		return kriterijum;
	}

	public void setKriterijum(String kriterijum) {
		this.kriterijum = kriterijum;
	}

	@Override
	public int compareTo(FarmaceutDTO f) {
		if (this.kriterijum.equals("IME"))
			return this.getIme().compareTo(f.getIme());
		else if (this.kriterijum.equals("PREZIME"))
			return this.getPrezime().compareTo(f.getPrezime());
		else
			return Double.toString(this.getOcena()).compareTo(Double.toString(f.getOcena()));
	}

}
