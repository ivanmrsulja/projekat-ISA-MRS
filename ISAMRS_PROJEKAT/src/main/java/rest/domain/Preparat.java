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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Preparat implements Ocenjivo{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "naziv", nullable = false)
	private String naziv;
	@Column(name = "tip", nullable = false)
	private TipLeka tip;
	@Column(name = "kontraindikacije", nullable = false)
	private String kontraindikacije;
	@Column(name = "sastav", nullable = false)
	private String sastav;
	@Column(name = "preporuceniUnos", nullable = false)
	private int preporuceniUnos;
	@Column(name = "poeni", nullable = false)
	private int poeni;
	@Column(name = "oblik", nullable = false)
	private String oblik;
	@Column(name = "proizvodjac", nullable = false)
	private String proizvodjac;
	@Column(name = "izdavanje", nullable = false)
	private RezimIzdavanja izdavanje;
	@Column(name = "brojOcena", nullable = false)
	private double brojOcena;
	@Column(name = "sumaOcena", nullable = false)
	private double sumaOcena;
	@Column(name = "ocena", nullable = false)
	private double ocena;
	
	@ManyToMany
	@JoinTable(name = "zamenski_preparati", joinColumns = @JoinColumn(name = "id_prvog", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_drugog", referencedColumnName = "id"))
	private Set<Preparat> zamjenskiPreparati;
	
	public Preparat(){
		this.zamjenskiPreparati = new HashSet<Preparat>();
	}

	public Preparat(String naziv, TipLeka tip, String kontraindikacije, String sastav, int preporuceniUnos,
			int poeni, String oblik, String proizvodjac, RezimIzdavanja izdavanje, double brojOcena, double sumaOcena) {
		this();
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
		return 0.0;
	}
	
	public void addZamenskiPreparat(Preparat p) {
		zamjenskiPreparati.add(p);
		p.getZamjenskiPreparati().add(this);
	}
	
	public void removeZamenskiPreparat(Preparat p) {
		zamjenskiPreparati.remove(p);
		p.getZamjenskiPreparati().remove(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer sifra) {
		this.id = sifra;
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
