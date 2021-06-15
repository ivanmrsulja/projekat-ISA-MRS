package rest.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.AdminApoteke;
import rest.domain.Dobavljac;
import rest.domain.Narudzbenica;
import rest.domain.Ponuda;
import rest.domain.Preparat;
import rest.dto.CenovnikDTO;
import rest.dto.DermatologDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.IzvestajValueDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.CenaRepository;
import rest.repository.DermatologRepository;
import rest.repository.DobavljacRepository;
import rest.repository.DostupanProizvodRepository;
import rest.repository.EReceptRepository;
import rest.repository.KorisnikRepository;
import rest.repository.NarudzbenicaRepozitory;
import rest.repository.NotifikacijaRepository;
import rest.repository.PacijentRepository;
import rest.repository.PonudaRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.RezervacijaRepository;
import rest.repository.TipKorisnikaRepository;
import rest.repository.ZahtevRepository;
import rest.repository.ZalbaRepository;
import rest.repository.ZaposlenjeRepository;
import rest.service.AdminServiceImpl;
import rest.service.AkcijaPromocijaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

	@Mock
	private PonudaRepository ponudaRepositoryMock;
	@Mock
	private NarudzbenicaRepozitory narudzbenicaRepositoryMock;
	@Mock
	private DobavljacRepository dobavljacRepositoryMock;
	@Mock
	private ApotekeRepository apotekeRepositoryMock;
	@Mock
	private CenaRepository cenaRepositoryMock;
	@Mock
	private DostupanProizvodRepository dostupanProizvodRepositoryMock;
	@Mock
	private PreparatRepository preparatRepositoryMock;
	@Mock
	private AdminApotekeRepository adminApotekeRepositoryMock;
	@Mock
	private AkcijaPromocijaService akcijaPromocijaServiceMock;
	@Mock
	private PacijentRepository pacijentRepositoryMock;
	@Mock
	private PregledRepository pregledRepositoryMock;
	@Mock
	private RezervacijaRepository rezervacijaRepositoryMock;
	@Mock
	private NotifikacijaRepository notifikacijaRepositoryMock;
	@Mock
	private KorisnikRepository korisnikRepositoryMock;
	@Mock
	private ZaposlenjeRepository zaposlenjeRepositoryMock;
	@Mock
	private ZahtevRepository zahtevRepositoryMock;
	@Mock
	private EReceptRepository ereceptRepositoryMock;
	@Mock
	private DermatologRepository dermatologRepositoryMock;
	@Mock
	private TipKorisnikaRepository tipRepositoryMock;
	@Mock
	private ZalbaRepository zalbaRepositoryMock;
	@Mock
	private Environment envMock;
	@Mock
	private JavaMailSender javaMailSenderMock;

	@InjectMocks
	private AdminServiceImpl serviceMock;

	@Autowired
	private AdminServiceImpl service;

	@Test
	public void getOffersForOrderTest() {
		Ponuda p1 = new Ponuda();
		p1.setId(1);
		p1.setNarudzbenica(new Narudzbenica());
		p1.getNarudzbenica().setId(1);
		p1.setDobavljac(new Dobavljac());
		p1.getDobavljac().setId(1);

		Ponuda p2 = new Ponuda();
		p2.setId(2);
		p2.setNarudzbenica(new Narudzbenica());
		p2.getNarudzbenica().setId(2);
		p2.setDobavljac(new Dobavljac());
		p2.getDobavljac().setId(2);


		when(ponudaRepositoryMock.getOffersForOrder(1)).thenReturn(Arrays.asList(p1, p2));

		Collection<PonudaDTO> retVal = serviceMock.getOffersForPharmacy(1);
		
		assertThat(retVal).hasSize(2);
	}

	@Test
	public void getPreparatByNameTest() {
		Preparat alirex = new Preparat();
		alirex.setNaziv("Alirex");
		when(preparatRepositoryMock.getPreparatByName("Alirex")).thenReturn(alirex);
		Preparat prep = preparatRepositoryMock.getPreparatByName("Alirex");
		
		assertTrue(prep.getNaziv().equals("Alirex"));
	}

	@Test
	public void getOneApotekaTest() {
		Narudzbenica n = new Narudzbenica();
		n.setId(1);
		n.setAdminApoteke(new AdminApoteke());
		n.getAdminApoteke().setId(1);
		when(narudzbenicaRepositoryMock.findById(1)).thenReturn(Optional.of(n));
		
		NarudzbenicaDTO retVal = serviceMock.getNarudzbenicaById(1);
		
		assertEquals((new NarudzbenicaDTO(n)).getId(), retVal.getId());
		assertEquals((new NarudzbenicaDTO(n)).getIdAdmina(), retVal.getIdAdmina());
	}

	@Test
	public void getDrugsForPharmacyTest() {
		ArrayList<DostupanProizvodDTO> retVal = service.getProductsForPharmacy(1);
		
		assertTrue(retVal.size() == 2);
	}

	@Test
	public void getPricelistForPharmacyFailTest() {
		ArrayList<DermatologDTO> derms = service.getDermatologistsOutsidePharmacy(1);

		assertEquals(derms.size(), 3);
		assertTrue(derms.get(0).getIme().equals("Dusan"));
	}

	@Test
	public void getNesto() {
		ArrayList<IzvestajValueDTO> examinations = service.getYearlyExaminations(2020, 1);

		assertEquals(examinations.size(), 12);
	}
}
