package rest.domain;

import java.util.Set;

public class Apoteka implements Ocenjivo{
	private Long id;
	private String naziv;
	private String opis;
	private int brojOcena;
	private int sumaOcena;
	
	private Set<Pacijent> pacijenti;
	private Set<Pregled> pregledi;
	private Set<Cena> cene;
	private Set<Zaposlenje> zaposlenja;
	private Lokacija lokacija;
	private Set<AdminApoteke> adminiApoteke;
	
	public Apoteka() {}
	
	public Apoteka(Long id,String naziv, String opis, int brojOcena, int sumaOcena, Set<Pacijent> pacijenti,
			Set<Pregled> pregledi, Set<Cena> cene, Set<Zaposlenje> zaposlenja, Lokacija lokacija,
			Set<AdminApoteke> adminiApoteke) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.opis = opis;
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.pacijenti = pacijenti;
		this.pregledi = pregledi;
		this.cene = cene;
		this.zaposlenja = zaposlenja;
		this.lokacija = lokacija;
		this.adminiApoteke = adminiApoteke;
	}
	
	public int izracunajOcenu()
	{
		if(this.brojOcena!=0) {
			return this.sumaOcena/this.brojOcena;
		}
		return 0;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
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

	public Set<Pacijent> getPacijenti() {
		return pacijenti;
	}

	public void setPacijenti(Set<Pacijent> pacijenti) {
		this.pacijenti = pacijenti;
	}

	public Set<Pregled> getPregledi() {
		return pregledi;
	}

	public void setPregledi(Set<Pregled> pregledi) {
		this.pregledi = pregledi;
	}

	public Set<Cena> getCene() {
		return cene;
	}

	public void setCene(Set<Cena> cene) {
		this.cene = cene;
	}

	public Set<Zaposlenje> getZaposlenja() {
		return zaposlenja;
	}

	public void setZaposlenja(Set<Zaposlenje> zaposlenja) {
		this.zaposlenja = zaposlenja;
	}

	public Lokacija getLokacija() {
		return lokacija;
	}

	public void setLokacija(Lokacija lokacija) {
		this.lokacija = lokacija;
	}

	public Set<AdminApoteke> getAdminiApoteke() {
		return adminiApoteke;
	}

	public void setAdminiApoteke(Set<AdminApoteke> adminiApoteke) {
		this.adminiApoteke = adminiApoteke;
	}
	
}
