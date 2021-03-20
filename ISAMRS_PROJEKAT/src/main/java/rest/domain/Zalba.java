package rest.domain;

public class Zalba {
	private int Id;
	private String text;
	
	private AdminSistema adminSistema;
	private Pacijent pacijent;
	
	public Zalba() {}
	
	public Zalba(int id, String text, AdminSistema adminSistema, Pacijent pacijent) {
		super();
		Id = id;
		this.text = text;
		this.adminSistema = adminSistema;
		this.pacijent = pacijent;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
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
