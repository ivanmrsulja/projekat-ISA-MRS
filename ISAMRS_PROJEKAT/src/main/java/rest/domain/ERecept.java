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

@Entity
public class ERecept {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "datumIzdavanja", nullable = false)
	private LocalDate datumIzdavanja;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<StavkaRecepta> stavkaRecepata;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	
	public ERecept() {
		this.stavkaRecepata = new HashSet<StavkaRecepta>();
	}

	public ERecept(int sifra, LocalDate datumIzdavanja, Pacijent pacijent) {
		this();
		this.id = sifra;
		this.datumIzdavanja = datumIzdavanja;
		this.pacijent = pacijent;
	}



	public int getId() {
		return id;
	}

	public void setId(int sifra) {
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
	
	
}
