package rest.domain;

public class AkcijaPromocija {
	
	private Long id;
	private String tekst;
	
	private AdminApoteke adminApoteke;
	
	public AkcijaPromocija() {}

	public AkcijaPromocija(Long id,String tekst, AdminApoteke adminApoteke) {
		super();
		this.id=id;
		this.tekst = tekst;
		this.adminApoteke = adminApoteke;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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
