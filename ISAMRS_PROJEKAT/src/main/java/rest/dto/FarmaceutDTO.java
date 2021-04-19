package rest.dto;

import java.time.LocalTime;

import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.dto.KorisnikDTO;

public class FarmaceutDTO extends KorisnikDTO{

	private int brojOcena;
	private int sumaOcena;
	private double ocena;
	private LocalTime pocetakRadnogVremena;
	private LocalTime krajRadnogVremena;
	private int apoteka;
	
	public FarmaceutDTO() {
		super();
	}
	
	public FarmaceutDTO(Farmaceut farmaceut) {
		super(farmaceut);
		ocena = farmaceut.getOcena();
		apoteka = farmaceut.getZaposlenje().getApoteka().getId();
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

}
