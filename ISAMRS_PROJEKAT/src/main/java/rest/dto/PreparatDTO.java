package rest.dto;

import java.util.ArrayList;

import rest.domain.Preparat;
import rest.domain.RezimIzdavanja;
import rest.domain.TipLeka;

public class PreparatDTO {

	private Integer id;
	private String naziv;
	
	private String kontraindikacije;
	private String sastav;
	private int preporuceniUnos;
	private String oblik;
	private String proizvodjac;
	private RezimIzdavanja rezim;
	private TipLeka tip;
	private double ocena;
	private int poeni;
	private double brojOcena;
	private double sumaOcena;
	private ArrayList<Integer> zamenskiPreparati = new ArrayList<Integer>();
	
	public PreparatDTO()
	{}
	public PreparatDTO(Preparat preparat)
	{
		this.id=preparat.getId();
		this.naziv=preparat.getNaziv();
		this.tip = preparat.getTip();
		this.brojOcena = preparat.getBrojOcena();
		this.sumaOcena = preparat.getSumaOcena();
		this.poeni = preparat.getPoeni();
		this.kontraindikacije=preparat.getKontraindikacije();
		this.sastav=preparat.getSastav();
		this.preporuceniUnos=preparat.getPreporuceniUnos();
		this.oblik=preparat.getOblik();
		this.proizvodjac=preparat.getProizvodjac();
		this.rezim=preparat.getIzdavanje();
		this.ocena=preparat.getOcena();
		this.tip = preparat.getTip();
		this.zamenskiPreparati = new ArrayList<>();
//		for (Preparat p : preparat.getZamjenskiPreparati()) {
//			zamenskiPreparati.add(p.getId());
//		}
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
	public int getPoeni() {
		return poeni;
	}
	public void setPoeni(int poeni) {
		this.poeni = poeni;
	}

	public double getBrojOcena() {
		return brojOcena;
	}
	public void setBrojOcena(double brojOcena) {
		this.brojOcena = brojOcena;
	}
	public double getSumaOcena() {
		return sumaOcena;
	}
	public void setSumaOcena(double sumaOcena) {
		this.sumaOcena = sumaOcena;
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
	public TipLeka getTip() {
		return tip;
	}
	public void setTip(TipLeka tip) {
		this.tip = tip;
	}
	public ArrayList<Integer> getZamenskiPreparati() {
		return zamenskiPreparati;
	}
	public void setZamenskiPreparati(ArrayList<Integer> zamenskiPreparati) {
		this.zamenskiPreparati = zamenskiPreparati;
	}
	
	
	
	
}
