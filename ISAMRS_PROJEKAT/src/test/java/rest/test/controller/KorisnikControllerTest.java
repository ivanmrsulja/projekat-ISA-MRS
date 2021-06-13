package rest.test.controller;

import java.nio.charset.Charset;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.Status;
import rest.domain.Apoteka;
import rest.domain.ERecept;
import rest.domain.Korisnik;
import rest.domain.Lokacija;
import rest.domain.OcenaApoteke;
import rest.domain.Penal;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.domain.StatusNaloga;
import rest.domain.TipKorisnika;
import rest.domain.Zalba;
import rest.domain.ZaposlenjeKorisnika;
import rest.dto.KorisnikDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KorisnikControllerTest {
	
	private static final String URL_PREFIX = "/api/users";
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetAllUsers()  {
		try {
			mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.[*].id").value(hasItem(1)))
			.andExpect(jsonPath("$.[*].ime").value(hasItem("Pera")))
			.andExpect(jsonPath("$.[*].prezime").value(hasItem("Peric")))
			.andExpect(jsonPath("$.[*].username").value(hasItem("pera99")))
			.andExpect(jsonPath("$.[*].password").value(hasItem("pera123")))
			.andExpect(jsonPath("$.[*].email").value(hasItem("pera99@yahoo.com")))
			.andExpect(jsonPath("$.[*].loggedBefore").value(hasItem(true)))
			.andExpect(jsonPath("$.[*].telefon").value(hasItem("3432432234")))
			.andExpect(jsonPath("$.[*].zaposlenjeKorisnika").value(hasItem(ZaposlenjeKorisnika.PACIJENT)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser() {
		Korisnik k = new Korisnik();
		k.setIme("Mika");
		k.setPrezime("Mikic");
		k.setUsername("mika94");
		k.setPassword("mika123");
		k.setEmail("mika@gmail.com");
		k.setLoggedBefore(true);
		k.setTelefon("493084348092");
		k.setZaposlenjeKorisnika(ZaposlenjeKorisnika.PACIJENT);
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(k);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			this.mockMvc.perform(post(URL_PREFIX+"/register").contentType(contentType).content(json)).andExpect(status().isCreated());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetPacijentById() {
		try {
			mockMvc.perform(get(URL_PREFIX + "/?id=" + 1)).andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.ime").value("Marko"))
			.andExpect(jsonPath("$.prezime").value("Markovic"))
			.andExpect(jsonPath("$.username").value("marko1"))
			.andExpect(jsonPath("$.email").value("marko@hotmail.com"))
			.andExpect(jsonPath("$.telefon").value("4543543"))
			.andExpect(jsonPath("$.lokacija").value(new Lokacija()))
			.andExpect(jsonPath("$.zaposlenjeKorisnika").value(ZaposlenjeKorisnika.ADMIN_APOTEKE))
			.andExpect(jsonPath("$.stariPassw").value("stari"))
			.andExpect(jsonPath("$.noviPassw").value("novi"))
			.andExpect(jsonPath("$.loggedBefore").value(false))
			.andExpect(jsonPath("$.brojPoena").value(320))
			.andExpect(jsonPath("$.tip").value(new TipKorisnika()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
