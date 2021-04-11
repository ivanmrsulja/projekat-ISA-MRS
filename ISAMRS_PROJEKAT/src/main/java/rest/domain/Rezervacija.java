package rest.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rezervacija {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "status", nullable = false)
	private StatusRezervacije status;
	@Column(name = "datumPreuzimanja", nullable = false)
	private LocalDate datumPreuzimanja;
	@Column(name = "cena", nullable = false)
	private double cena;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@ManyToOne(fetch = FetchType.EAGER)
	private Preparat preparat;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Apoteka apoteka;
	
	public Rezervacija() {}
	
	public Rezervacija(StatusRezervacije status, LocalDate datumPreuzimanja, Pacijent pacijent, Preparat preparat, Apoteka a, double cena) {
		super();
		this.status = status;
		this.datumPreuzimanja = datumPreuzimanja;
		this.pacijent = pacijent;
		this.preparat = preparat;
		this.apoteka = a;
		this.cena = cena;
	}
	
	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusRezervacije getStatus() {
		return status;
	}

	public void setStatus(StatusRezervacije status) {
		this.status = status;
	}

	public LocalDate getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(LocalDate datumPreuzimanja) {
		this.datumPreuzimanja = datumPreuzimanja;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Preparat getPreparat() {
		return preparat;
	}

	public void setPreparat(Preparat preparat) {
		this.preparat = preparat;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}	
}
