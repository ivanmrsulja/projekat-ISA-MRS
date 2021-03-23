package rest.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ponuda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "status", nullable = false)
	private StatusPonude status;
	@Column(name = "ukupnaCena", nullable = false)
	private double ukupnaCena;
	@Column(name = "rokIsporuke", nullable = false)
	private LocalDate rokIsporuke;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Narudzbenica narudzbenica;
	@ManyToOne(fetch = FetchType.EAGER)
	private Dobavljac dobavljac;
	
	public Ponuda()	{};
	
	public Ponuda(StatusPonude status, double ukupnaCena, LocalDate rokIsporuke, Narudzbenica narudzbenica,
			Dobavljac dobavljac) {
		super();
		this.status = status;
		this.ukupnaCena = ukupnaCena;
		this.rokIsporuke = rokIsporuke;
		this.narudzbenica = narudzbenica;
		this.dobavljac = dobavljac;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusPonude getStatus() {
		return status;
	}

	public void setStatus(StatusPonude status) {
		this.status = status;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public LocalDate getRokIsporuke() {
		return rokIsporuke;
	}

	public void setRokIsporuke(LocalDate rokIsporuke) {
		this.rokIsporuke = rokIsporuke;
	}

	public Narudzbenica getNarudzbenica() {
		return narudzbenica;
	}

	public void setNarudzbenica(Narudzbenica narudzbenica) {
		this.narudzbenica = narudzbenica;
	}

	public Dobavljac getDobavljac() {
		return dobavljac;
	}

	public void setDobavljac(Dobavljac dobavljac) {
		this.dobavljac = dobavljac;
	}
		
}
