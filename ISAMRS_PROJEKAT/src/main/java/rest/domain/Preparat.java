package rest.domain;

import java.util.HashSet;
import java.util.Set;

public class Preparat implements Ocenjivo{
	private int sifra;
	private String naziv;
	private TipLeka tip;
	private String kontraindikacije;
	private String sastav;
	private int preporuceniUnos;
	private int poeni;
	private String oblik;
	private String proizvodjac;
	private RezimIzdavanja izdavanje;
	private int brojOcena;
	private int sumaOcena;
	private double ocena;
	
	private Set<Preparat> zamjenskiPreparati;
	
	public Preparat(){
		this.zamjenskiPreparati = new HashSet<Preparat>();
	}

	public Preparat(int sifra, String naziv, TipLeka tip, String kontraindikacije, String sastav, int preporuceniUnos,
			int poeni, String oblik, String proizvodjac, RezimIzdavanja izdavanje, int brojOcena, int sumaOcena) {
		this();
		this.sifra = sifra;
		this.naziv = naziv;
		this.tip = tip;
		this.kontraindikacije = kontraindikacije;
		this.sastav = sastav;
		this.preporuceniUnos = preporuceniUnos;
		this.poeni = poeni;
		this.oblik = oblik;
		this.proizvodjac = proizvodjac;
		this.izdavanje = izdavanje;
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.ocena=izracunajOcenu();
	}
	
	public double izracunajOcenu()
	{
		if(this.brojOcena!=0) {
			return this.sumaOcena/this.brojOcena;
		}
		return 0;
	}

	public int getSifra() {
		return sifra;
	}

	public void setSifra(int sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public TipLeka getTip() {
		return tip;
	}

	public void setTip(TipLeka tip) {
		this.tip = tip;
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

	public int getPoeni() {
		return poeni;
	}

	public void setPoeni(int poeni) {
		this.poeni = poeni;
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

	public RezimIzdavanja getIzdavanje() {
		return izdavanje;
	}

	public void setIzdavanje(RezimIzdavanja izdavanje) {
		this.izdavanje = izdavanje;
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

	public Set<Preparat> getZamjenskiPreparati() {
		return zamjenskiPreparati;
	}

	public void setZamjenskiPreparati(Set<Preparat> zamjenskiPreparati) {
		this.zamjenskiPreparati = zamjenskiPreparati;
	}

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	
}
