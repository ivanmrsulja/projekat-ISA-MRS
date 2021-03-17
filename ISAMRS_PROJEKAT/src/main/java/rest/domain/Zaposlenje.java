package rest.domain;


public class Zaposlenje {
	private Long id;
	private int pocetakRadnogVremena;
	private int krajRadnogVremena;
	
	private Apoteka apoteka;
	private Korisnik korisnik;
	
	public Zaposlenje() {}
	
	public Zaposlenje(Long id,int pocetakRadnogVremena, int krajRadnogVremena, Apoteka apoteka,Korisnik korisnik) {
		super();
		this.id=id;
		this.pocetakRadnogVremena = pocetakRadnogVremena;
		this.krajRadnogVremena = krajRadnogVremena;
		this.apoteka = apoteka;
		this.korisnik=korisnik;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public int getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}

	public void setPocetakRadnogVremena(int pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}

	public int getKrajRadnogVremena() {
		return krajRadnogVremena;
	}

	public void setKrajRadnogVremena(int krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
}
