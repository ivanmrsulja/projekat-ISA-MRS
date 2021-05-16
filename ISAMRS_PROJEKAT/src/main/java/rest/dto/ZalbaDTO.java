package rest.dto;

import rest.domain.Zalba;

public class ZalbaDTO {
	private int id;
	private String tekst;
	private String nazivKorisnika;
	private String nazivAdmina;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazivKorisnika() {
		return nazivKorisnika;
	}
	public void setNazivKorisnika(String nazivKorisnika) {
		this.nazivKorisnika = nazivKorisnika;
	}
	public String getNazivAdmina() {
		return nazivAdmina;
	}
	public void setNazivAdmina(String nazivAdmina) {
		this.nazivAdmina = nazivAdmina;
	}
	
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public ZalbaDTO() {
	}
	
	public ZalbaDTO(Zalba z) {
		this.id = z.getId();
		if(z.getAdminSistema() == null) {
			this.nazivAdmina = "";
		} else {
			this.nazivAdmina = z.getAdminSistema().getUsername();
		}
		this.nazivKorisnika = z.getPacijent().getUsername();
		this.tekst = z.getText();
	}
	
	

}
