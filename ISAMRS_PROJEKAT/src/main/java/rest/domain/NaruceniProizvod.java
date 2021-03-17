package rest.domain;

public class NaruceniProizvod {
	private Long id;
	private int kolicina;
	
	private Preparat preparat;
	private Narudzbenica narudzbenica;
	
	public NaruceniProizvod() {}

	public NaruceniProizvod(Long id,int kolicina, Preparat preparat, Narudzbenica narudzbenica) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.preparat = preparat;
		this.narudzbenica = narudzbenica;
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

	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}

	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	
}
