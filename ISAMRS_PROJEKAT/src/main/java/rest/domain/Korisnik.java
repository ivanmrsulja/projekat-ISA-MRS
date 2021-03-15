package rest.domain;

public class Korisnik {
	private Long Id;
	private String ime;
	private String prezime;
	private String username;
	private String password;
	
	public Korisnik() {}

	public Korisnik(Long Id, String ime, String prezime, String username, String password) {
		super();
		this.Id = Id;
		this.ime = ime;
		this.prezime = prezime;
		this.username = username;
		this.password = password;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
