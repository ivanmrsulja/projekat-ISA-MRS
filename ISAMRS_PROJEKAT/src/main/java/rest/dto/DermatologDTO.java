package rest.dto;

import rest.domain.Dermatolog;

public class DermatologDTO extends KorisnikDTO{
	
	double ocjena;
	
	public DermatologDTO() {}
	public DermatologDTO(Dermatolog d) {
		super(d);
		ocjena = d.getOcena();
	}
	
	public double getOcjena() {
		return ocjena;
	}
	
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}
	
}
