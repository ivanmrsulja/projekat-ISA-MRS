package rest.dto;

import rest.domain.Preparat;
import rest.domain.RezimIzdavanja;

public class PreparatDTO {

	private Integer id;
	private String naziv;
	
	private String kontraindikacije;
	private String sastav;
	private int preporuceniUnos;
	private String oblik;
	private String proizvodjac;
	private RezimIzdavanja rezim;
	private double ocena;
	
	public PreparatDTO()
	{}
	public PreparatDTO(Preparat preparat)
	{
		this.id=preparat.getId();
		this.naziv=preparat.getNaziv();
		
		this.kontraindikacije=preparat.getKontraindikacije();
		this.sastav=preparat.getSastav();
		this.preporuceniUnos=preparat.getPreporuceniUnos();
		this.oblik=preparat.getOblik();
		this.proizvodjac=preparat.getProizvodjac();
		this.rezim=preparat.getIzdavanje();
		this.ocena=preparat.getOcena();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getKontraindikacije() {
		return kontraindikacije;
	}
	public void setKontraindikacije(String kontraindikacije) {
		this.kontraindikacije = kontraindikacije;
	}
	public String getSastav() {
		return sastav;
	}
	public void setSastav(String sastav) {
		this.sastav = sastav;
	}
	public int getPreporuceniUnos() {
		return preporuceniUnos;
	}
	public void setPreporuceniUnos(int preporuceniUnos) {
		this.preporuceniUnos = preporuceniUnos;
	}
	public String getOblik() {
		return oblik;
	}
	public void setOblik(String oblik) {
		this.oblik = oblik;
	}
	public String getProizvodjac() {
		return proizvodjac;
	}
	public void setProizvodjac(String proizvodjac) {
		this.proizvodjac = proizvodjac;
	}
	public RezimIzdavanja getRezim() {
		return rezim;
	}
	public void setRezim(RezimIzdavanja rezim) {
		this.rezim = rezim;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	
}
