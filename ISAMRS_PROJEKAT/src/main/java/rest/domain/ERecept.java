package rest.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class ERecept {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "datumIzdavanja", nullable = false)
	private LocalDate datumIzdavanja;
	@Column(name = "status", nullable = false)
	private StatusERecepta status;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Set<StavkaRecepta> stavkaRecepata;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Apoteka apoteka;
	
	public ERecept() {
		this.stavkaRecepata = new HashSet<StavkaRecepta>();
	}

	public ERecept(LocalDate datumIzdavanja, Pacijent pacijent, StatusERecepta status, Apoteka apoteka) {
		this();
		this.datumIzdavanja = datumIzdavanja;
		this.pacijent = pacijent;
		this.status = status;
		this.apoteka = apoteka;
	}

	public StatusERecepta getStatus() {
		return status;
	}

	public void setStatus(StatusERecepta status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer sifra) {
		this.id = sifra;
	}

	public LocalDate getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(LocalDate datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public Set<StavkaRecepta> getStavkaRecepata() {
		return stavkaRecepata;
	}

	public void setStavkaRecepata(Set<StavkaRecepta> stavkaRecepata) {
		this.stavkaRecepata = stavkaRecepata;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Apoteka getApoteka() {
		return apoteka;
	}

	public void setApoteka(Apoteka apoteka) {
		this.apoteka = apoteka;
	}
	
}
