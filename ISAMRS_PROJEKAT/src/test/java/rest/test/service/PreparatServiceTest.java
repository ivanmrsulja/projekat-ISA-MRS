package rest.test.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.Pacijent;
import rest.domain.Preparat;
import rest.domain.Rezervacija;
import rest.dto.CenaDTO;
import rest.repository.PreparatRepository;
import rest.service.PreparatService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreparatServiceTest {

	@Mock
	private Preparat preparatMock;
	
	@Mock
	private PreparatRepository preparatRepositoryMock;
	
	@Autowired	
	private PreparatService service;
	
	
	@Test
	public void rezervisiTest() throws Exception {
		Rezervacija retVal = service.rezervisi(1, 6, 1, LocalDate.of(2021, 11, 11));
		
		assertTrue(retVal.getDatumPreuzimanja().equals(LocalDate.of(2021, 11, 11)));
	}
	
	@Test
	public void getPharmaciesForDrugTest() {
		Collection<CenaDTO> retVal = service.getPharmaciesForDrug(1);
		
		assertTrue(retVal.size() == 1);
	}
	
	@Test
	public void rezervisiTestFail() {
		Rezervacija retVal;
		try {
			retVal = service.rezervisi(1, 5, 1, LocalDate.of(2021, 11, 11));
		} catch (Exception e) {
			assertTrue(e.getMessage().startsWith("Imate 3 penala"));
		}
	}
	
	@Test
	public void getOneTest() {
		preparatMock = new Preparat();
		preparatMock.setId(1);
		when(preparatRepositoryMock.findById(1)).thenReturn(Optional.of(preparatMock));
		
		Preparat retVal = service.getOne(1);
		
		assertEquals(preparatMock, retVal);
	}
}
