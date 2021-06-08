package rest.dto;

public class RacunDTO {
	private int cenaId;
	private String lekoviId;
	public RacunDTO(int cenaId, String lekoviId) {
		super();
		this.cenaId = cenaId;
		this.lekoviId = lekoviId;
	}
	public RacunDTO() {
		super();
	}
	public int getCenaId() {
		return cenaId;
	}
	public void setCenaId(int cenaId) {
		this.cenaId = cenaId;
	}
	public String getLekoviId() {
		return lekoviId;
	}
	public void setLekoviId(String lekoviId) {
		this.lekoviId = lekoviId;
	}
	
	

}
