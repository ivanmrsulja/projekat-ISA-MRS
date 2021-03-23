package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AkcijaPromocija {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "tekst", nullable = false)
	private String tekst;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private AdminApoteke adminApoteke;
	
	public AkcijaPromocija() {}

	public AkcijaPromocija(String tekst, AdminApoteke adminApoteke) {
		super();
		this.tekst = tekst;
		this.adminApoteke = adminApoteke;
	}
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public AdminApoteke getAdminApoteke() {
		return adminApoteke;
	}

	public void setAdminApoteke(AdminApoteke adminApoteke) {
		this.adminApoteke = adminApoteke;
	}

	
}
