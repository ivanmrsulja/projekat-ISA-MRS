package rest.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import rest.domain.Cena;
import rest.domain.DostupanProizvod;

public class CenovnikDTO {
	
	private ArrayList<DostupanProizvodDTO> dostupniProizvodi;
	private LocalDate pocetakVazenja;
	private LocalDate krajVazenja;
	private String promoTekst;

	public CenovnikDTO() {}

	public CenovnikDTO(Cena c) {
		this.dostupniProizvodi = new ArrayList<DostupanProizvodDTO>();
		for (DostupanProizvod dp : c.getDostupniProizvodi())
			this.dostupniProizvodi.add(new DostupanProizvodDTO(dp));
		this.pocetakVazenja = c.getPocetakVazenja();
		this.krajVazenja = c.getKrajVazenja();
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

	public LocalDate getKrajVazenja() {
		return krajVazenja;
	}

	public void setKrajVazenja(LocalDate krajVazenja) {
		this.krajVazenja = krajVazenja;
	}

	public String getPromoTekst() {
		return promoTekst;
	}

	public void setPromoTekst(String promoTekst) {
		this.promoTekst = promoTekst;
	}

	@Override
	public String toString() {
		return "CenovnikDTO [dostupniProizvodi=" + dostupniProizvodi + ", pocetakVazenja=" + pocetakVazenja + "]";
	}
	
}
