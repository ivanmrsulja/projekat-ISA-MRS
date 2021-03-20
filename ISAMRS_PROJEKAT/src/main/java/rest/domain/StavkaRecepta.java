package rest.domain;


public class StavkaRecepta {
	private int id;
	private int kolicina;
	
	private Preparat preparat;
	
	public StavkaRecepta() {}

	public StavkaRecepta(int id,int kolicina, Preparat preparat) {
		super();
		this.id=id;
		this.kolicina = kolicina;
		this.preparat = preparat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}
	
}
