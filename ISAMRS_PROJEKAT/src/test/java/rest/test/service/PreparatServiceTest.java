package rest.test.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import rest.domain.Rezervacija;
import rest.dto.CenaDTO;
import rest.service.PreparatService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreparatServiceTest {

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
}
