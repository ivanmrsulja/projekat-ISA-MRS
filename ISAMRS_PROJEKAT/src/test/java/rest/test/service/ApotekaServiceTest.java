package rest.test.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.Apoteka;
import rest.domain.Lokacija;
import rest.dto.ApotekaDTO;
import rest.repository.AdminApotekeRepository;
import rest.repository.ApotekeRepository;
import rest.repository.DermatologRepository;
import rest.repository.FarmaceutRepository;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.ZaposlenjeRepository;
import rest.service.ApotekaServiceImpl;
import rest.util.ApotekaSearchParams;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApotekaServiceTest {
	
	@Mock
	private ApotekeRepository apotekeMock;
	@Mock
	private AdminApotekeRepository adminMock;
	@Mock
	private DermatologRepository dermatoloziMock;
	@Mock
	private ZaposlenjeRepository zaposlenjaMock;
	@Mock
	private PregledRepository preglediMock;
	@Mock
	private FarmaceutRepository farmaceutiMock;
	@Mock
	private PacijentRepository pacijentiMock;
	@Mock
	private PenalRepository penaliMock;
	
	@InjectMocks
	private ApotekaServiceImpl service;
	
	@Mock
	private Apoteka apotekaMock;
	
	@Test
	public void getAllDrugStoresTest() {
		PageRequest pageRequest = PageRequest.of(0, 10, Direction.ASC, "none");
		ApotekaSearchParams params = new ApotekaSearchParams("NAZIV", "ADRESA", 1.0, 1.0, 50000.0, "None", false);
		Apoteka a1 = new Apoteka("BlaBla", "Blabla", 2, 10, new Lokacija(), 2000.0);
		a1.setId(1);
		Apoteka a2 = new Apoteka("BlaBla", "Blabla", 2, 10, new Lokacija(), 2000.0);
		a2.setId(2);
		
		when(apotekeMock.findAll(pageRequest, "NAZIV", "ADRESA", 1.0, 1.0, 12.2, 12.2, 50000.0)).thenReturn(new PageImpl<Apoteka>(Arrays.asList(a1, a2)));
		
		Page<ApotekaDTO> retVal = service.getAllDrugStores(0, params, 12.2, 12.2);
		
		assertTrue(retVal.getNumberOfElements() == 2);
	}
}
