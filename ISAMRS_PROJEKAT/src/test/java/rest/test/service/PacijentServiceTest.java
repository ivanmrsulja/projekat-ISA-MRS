package rest.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.AdminSistema;
import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.Zalba;
import rest.dto.PreparatDTO;
import rest.dto.ZalbaDTO;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
import rest.repository.ZalbaRepository;
import rest.service.PacijentServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PacijentServiceTest {
	
	@Mock
	private PacijentRepository pacijentRepositoryMock;
	@Mock
	private PreparatRepository preparatRepositoryMock;
	@Mock
	private PregledRepository pregledRepositoryMock;
	@Mock
	private PenalRepository penaliRepositoryMock;
	@Mock
	private ZalbaRepository zalbeRepositoryMock;
	
	@Mock
	private Pacijent pacijentMock;
	

	
	@InjectMocks
	private PacijentServiceImpl service;
	
	
	@Test
	public void allergiesTest() {
		
		when(pacijentRepositoryMock.getAllergiesForUser(1)).thenReturn(Arrays.asList(new Preparat(), new Preparat()));
		
		Collection<PreparatDTO> retVal = service.allergies(1);
		
		assertThat(retVal).hasSize(2);
	}
	
	@Test
	public void getOneTest() {
		
		when(pacijentRepositoryMock.findById(1)).thenReturn(Optional.of(pacijentMock));
		
		Pacijent retVal = service.getOne(1);
		
		assertEquals(pacijentMock, retVal);
	}
	
	@Test
	public void ZalbeTest() {
		Zalba z1 = new Zalba("Zalba broj 1", null, pacijentMock);
		Zalba z2 = new Zalba("Zalba broj 2", null, pacijentMock);
		Zalba z3 = new Zalba("Zalba broj 3", null, pacijentMock);
		z1.setId(1);
		z2.setId(2);
		z3.setId(3);
		when(pacijentRepositoryMock.getPatientZalbe(1)).thenReturn(Arrays.asList(z1, z2, z3));
		Collection<ZalbaDTO> retVal = service.getZalbeForPatient(1);
		assertThat(retVal).hasSize(3);
		
	}
	
	@Test
	public void getOneZalbaTest() {

		Zalba z1 = new Zalba("Zalba broj 1", null, pacijentMock);
		Zalba z2 = new Zalba("Zalba broj 2", null, pacijentMock);
		Zalba z3 = new Zalba("Zalba broj 3", null, pacijentMock);
		z1.setId(1);
		z2.setId(2);
		z3.setId(3);
		when(pacijentRepositoryMock.getPatientZalbe(1)).thenReturn(Arrays.asList(z1, z2, z3));
		ZalbaDTO z = service.getZalbaForPatient(1, 2);
		assertEquals(z.getTekst(), "Zalba broj 2");
	}
	
}
