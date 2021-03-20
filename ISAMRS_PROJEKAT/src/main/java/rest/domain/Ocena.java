package rest.domain;

public class Ocena {
	private int id;
	private int ocena;
	
	private Pacijent pacijent;
	private Ocenjivo ocenjivo;
	
	public Ocena() {}

	public Ocena(int id,int ocena, Pacijent pacijent,Ocenjivo ocenjivo) {
		super();
		this.id=id;
		this.ocena = ocena;
		this.pacijent = pacijent;
		this.ocenjivo=ocenjivo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Ocenjivo getOcenjivo() {
		return ocenjivo;
	}

	public void setOcenjivo(Ocenjivo ocenjivo) {
		this.ocenjivo = ocenjivo;
	}
	
}
