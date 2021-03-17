package rest.domain;

public class DostupanProizvod {
	private Long id;
	private int kolicina;
	private double cena;
	
	private Preparat preparat;
	
	public DostupanProizvod() {}
	
	public DostupanProizvod(Long id,int kolicina, double cena,Preparat preparat) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.cena = cena;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	
	
}
