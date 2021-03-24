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
public class Narudzbenica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "rok", nullable = false)
	private LocalDate rok;
	@OneToMany(mappedBy = "narudzbenica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<NaruceniProizvod> naruceniProizvodi;
	@ManyToOne(fetch = FetchType.EAGER)
	private AdminApoteke adminApoteke;
	@OneToMany(mappedBy = "narudzbenica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ponuda> ponude;
	
	public Narudzbenica() {
		this.naruceniProizvodi = new HashSet<NaruceniProizvod>();
		this.ponude = new HashSet<Ponuda>();
	}
	
	public Narudzbenica(LocalDate rok, AdminApoteke adminApoteke) {
		this();
		this.rok = rok;
		this.adminApoteke = adminApoteke;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getRok() {
		return rok;
	}

	public void setRok(LocalDate rok) {
		this.rok = rok;
	}

	public Set<NaruceniProizvod> getNaruceniProizvodi() {
		return naruceniProizvodi;
	}

	public void setNaruceniProizvodi(Set<NaruceniProizvod> naruceniProizvodi) {
		this.naruceniProizvodi = naruceniProizvodi;
	}

	public AdminApoteke getAdminApoteke() {
		return adminApoteke;
	}

	public void setAdminApoteke(AdminApoteke adminApoteke) {
		this.adminApoteke = adminApoteke;
	}

	public Set<Ponuda> getPonude() {
		return ponude;
	}

	public void setPonude(Set<Ponuda> ponude) {
		this.ponude = ponude;
	}
	
	
	
}
