package rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Zalba {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	@Column(name = "text", nullable = false)
	private String text;
	@ManyToOne(fetch = FetchType.EAGER)
	private AdminSistema adminSistema;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pacijent pacijent;
	
	public Zalba() {}
	
	public Zalba(String text, AdminSistema adminSistema, Pacijent pacijent) {
		super();
		this.text = text;
		this.adminSistema = adminSistema;
		this.pacijent = pacijent;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
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
