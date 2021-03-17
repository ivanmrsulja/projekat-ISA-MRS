package rest.domain;

import java.util.Date;
import java.util.Set;

public class Narudzbenica {
	private Long id;
	private Date rok;

	private Set<NaruceniProizvod> naruceniProizvodi;
	private AdminApoteke adminApoteke;
	private Set<Ponuda> ponude;
	
	public Narudzbenica() {}
	
	public Narudzbenica(Long id,Date rok, Set<NaruceniProizvod> naruceniProizvodi, AdminApoteke adminApoteke,
			Set<Ponuda> ponude) {
		super();
		this.id=id;
		this.rok = rok;
		this.naruceniProizvodi = naruceniProizvodi;
		this.adminApoteke = adminApoteke;
		this.ponude = ponude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Date getRok() {
		return rok;
	}

	public void setRok(Date rok) {
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
