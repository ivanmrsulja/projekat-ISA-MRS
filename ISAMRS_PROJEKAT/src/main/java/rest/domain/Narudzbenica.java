package rest.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Narudzbenica {
	private int id;
	private LocalDate rok;

	private Set<NaruceniProizvod> naruceniProizvodi;
	private AdminApoteke adminApoteke;
	private Set<Ponuda> ponude;
	
	public Narudzbenica() {
		this.naruceniProizvodi = new HashSet<NaruceniProizvod>();
		this.ponude = new HashSet<Ponuda>();
	}
	
	public Narudzbenica(int id,LocalDate rok, AdminApoteke adminApoteke) {
		this();
		this.id=id;
		this.rok = rok;
		this.adminApoteke = adminApoteke;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
