package rest.domain;

public class Zalba {
	private Long Id;
	private String text;
	
	private AdminSistema adminSistema;
	private Pacijent pacijent;
	
	public Zalba() {}
	
	public Zalba(Long id, String text, AdminSistema adminSistema, Pacijent pacijent) {
		super();
		Id = id;
		this.text = text;
		this.adminSistema = adminSistema;
		this.pacijent = pacijent;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public AdminSistema getAdminSistema() {
		return adminSistema;
	}

	public void setAdminSistema(AdminSistema adminSistema) {
		this.adminSistema = adminSistema;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
}
