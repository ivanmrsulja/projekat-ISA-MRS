package rest.dto;

import java.time.LocalDate;

import rest.domain.ERecept;
import rest.domain.StatusERecepta;

public class EReceptDTO {
	
	private Integer id;
	private LocalDate datumIzdavanja;
	private StatusERecepta status;
	
	public EReceptDTO() {}
	public EReceptDTO(ERecept e) {
		id = e.getId();
		datumIzdavanja = e.getDatumIzdavanja();
		status = e.getStatus();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getDatumIzdavanja() {
		return datumIzdavanja;
	}
	public void setDatumIzdavanja(LocalDate datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}
	public StatusERecepta getStatus() {
		return status;
	}
	public void setStatus(StatusERecepta status) {
		this.status = status;
	}
}
