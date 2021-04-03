package rest.util;

public class ApotekaSearchParams {
	private String naziv;
	private String adresa;
	private double dOcena;
	private double gOcena;
	private double rastojanje;
	private String kriterijumSortiranja;
	private boolean opadajuce;
	
	public ApotekaSearchParams() {}
	
	public ApotekaSearchParams(String naziv, String adresa, double dOcena, double gOcena, double rastojanje,
			String kriterijumSortiranja, boolean opadajuce) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.dOcena = dOcena;
		this.gOcena = gOcena;
		this.rastojanje = rastojanje;
		this.kriterijumSortiranja = kriterijumSortiranja;
		this.opadajuce = opadajuce;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public double getdOcena() {
		return dOcena;
	}

	public void setdOcena(double dOcena) {
		this.dOcena = dOcena;
	}

	public double getgOcena() {
		return gOcena;
	}

	public void setgOcena(double gOcena) {
		this.gOcena = gOcena;
	}

	public double getRastojanje() {
		return rastojanje;
	}

	public void setRastojanje(double rastojanje) {
		this.rastojanje = rastojanje;
	}

	public String getKriterijumSortiranja() {
		return kriterijumSortiranja;
	}

	public void setKriterijumSortiranja(String kriterijumSortiranja) {
		this.kriterijumSortiranja = kriterijumSortiranja;
	}

	public boolean isOpadajuce() {
		return opadajuce;
	}

	public void setOpadajuce(boolean opadajuce) {
		this.opadajuce = opadajuce;
	}

	@Override
	public String toString() {
		return "ApotekaSearchParams [naziv=" + naziv + ", adresa=" + adresa + ", dOcena=" + dOcena + ", gOcena="
				+ gOcena + ", rastojanje=" + rastojanje + ", kriterijumSortiranja=" + kriterijumSortiranja
				+ ", opadajuce=" + opadajuce + "]";
	}
	
}
