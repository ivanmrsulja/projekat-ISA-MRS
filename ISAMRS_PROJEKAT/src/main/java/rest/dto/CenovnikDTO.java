package rest.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import rest.domain.Cena;
import rest.domain.DostupanProizvod;

public class CenovnikDTO {
	
	private ArrayList<DostupanProizvodDTO> dostupniProizvodi;
	private LocalDate pocetakVazenja;

	public CenovnikDTO() {}

	public CenovnikDTO(Cena c) {
		this.dostupniProizvodi = new ArrayList<DostupanProizvodDTO>();
		for (DostupanProizvod dp : c.getDostupniProizvodi())
			this.dostupniProizvodi.add(new DostupanProizvodDTO(dp));
		this.pocetakVazenja = c.getPocetakVazenja();
	}

	public CenovnikDTO(ArrayList<DostupanProizvodDTO> dostupniProizvodi, LocalDate pocetakVazenja) {
		super();
		this.dostupniProizvodi = dostupniProizvodi;
		this.pocetakVazenja = pocetakVazenja;
	}

	public ArrayList<DostupanProizvodDTO> getDostupniProizvodi() {
		return dostupniProizvodi;
	}
	public void setDostupniProizvodi(ArrayList<DostupanProizvodDTO> dostupniProizvodi) {
		this.dostupniProizvodi = dostupniProizvodi;
	}
	public LocalDate getPocetakVazenja() {
		return pocetakVazenja;
	}
	public void setPocetakVazenja(LocalDate pocetakVazenja) {
		this.pocetakVazenja = pocetakVazenja;
	}
	@Override
	public String toString() {
		return "CenovnikDTO [dostupniProizvodi=" + dostupniProizvodi + ", pocetakVazenja=" + pocetakVazenja + "]";
	}
	
}
