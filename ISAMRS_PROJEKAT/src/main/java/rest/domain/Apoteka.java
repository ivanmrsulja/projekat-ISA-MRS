package rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Apoteka implements Ocenjivo{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "naziv", nullable = false)
	private String naziv;
	@Column(name = "opis", nullable = false)
	private String opis;
	@Column(name = "brojOcena", nullable = false)
	private int brojOcena;
	@Column(name = "sumaOcena", nullable = false)
	private int sumaOcena;
	@Column(name = "ocena", nullable = true)
	private double ocena;
	
	@ManyToMany(mappedBy = "apoteke")
	private Set<Pacijent> pacijenti;
	@OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pregled> pregledi;
	@OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
	private Set<Cena> cene;
	@OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Zaposlenje> zaposlenja;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	private Lokacija lokacija;
	@OneToMany(mappedBy = "apoteka", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AdminApoteke> adminiApoteke;
	
	public Apoteka() {
		this.pacijenti = new HashSet<Pacijent>();
		this.pregledi = new HashSet<Pregled>();
		this.cene = new HashSet<Cena>();
		this.zaposlenja = new HashSet<Zaposlenje>();
		this.adminiApoteke = new HashSet<AdminApoteke>();
	}
	
	public Apoteka(String naziv, String opis, int brojOcena, int sumaOcena, Lokacija lokacija) {
		this();
		this.naziv = naziv;
		this.opis = opis;
		this.brojOcena = brojOcena;
		this.sumaOcena = sumaOcena;
		this.lokacija = lokacija;
		this.ocena = this.izracunajOcenu();
	}
	
	public void addZaposlenje(Zaposlenje z) {
		zaposlenja.add(z);
		z.setApoteka(this);
	}
	
	public void removeZaposlenje(Zaposlenje z) {
		zaposlenja.remove(z);
		z.setApoteka(null);
	}
	
	public void addCena(Cena c) {
		cene.add(c);
		c.setApoteka(this);
	}
	
	public void removeCena(Cena c) {
		cene.remove(c);
		c.setApoteka(null);
	}
	
	public double izracunajOcenu()
	{
		if(this.brojOcena!=0) {
			return this.sumaOcena/this.brojOcena;
		}
		return 0;
	}
	
	public void addPregled(Pregled p) {
		pregledi.add(p);
		p.setApoteka(this);
	}
	
	public void removePregled(Pregled p) {
		pregledi.remove(p);
		p.setApoteka(null);
	}
	
	public void addPacijent(Pacijent p) {
		pacijenti.add(p);
		p.getApoteke().add(this);
	}
	
	public void removePacijent(Pacijent p) {
		pacijenti.remove(p);
		p.getApoteke().remove(this);
	}
	
	public void addAdmin(AdminApoteke a) {
		adminiApoteke.add(a);
		a.setApoteka(this);
	}
	
	public void removeAdmin(AdminApoteke a) {
		adminiApoteke.remove(a);
		a.setApoteka(null);
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

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	
}
