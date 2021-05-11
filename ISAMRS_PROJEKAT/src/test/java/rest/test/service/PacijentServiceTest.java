package rest.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.Pacijent;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.dto.PreparatDTO;
import rest.repository.PacijentRepository;
import rest.repository.PenalRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;
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
	
}
