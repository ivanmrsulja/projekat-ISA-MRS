package rest.domain;

public class TeloAkcijePromocije {

	private String tekst;
	private int idAdmina;

	public TeloAkcijePromocije() {}

	@Override
	public String toString() {
		return "TeloAkcijePromocije [tekst=" + tekst + ", idAdmina=" + idAdmina + "]";
	}

	public TeloAkcijePromocije(String tekst, int idAdmina) {
		super();
		this.tekst = tekst;
		this.idAdmina = idAdmina;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public int getIdAdmina() {
		return idAdmina;
	}
	public void setIdAdmina(int idAdmina) {
		this.idAdmina = idAdmina;
	}


}
