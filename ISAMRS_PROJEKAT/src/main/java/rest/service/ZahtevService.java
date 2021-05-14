package rest.service;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Zahtjev;
import rest.dto.FarmaceutDTO;
import rest.dto.ZahtevDTO;

public interface ZahtevService {

	Collection<Zahtjev> findAllForPharmacy(Collection<Integer> pharmacistIds);

	Zahtjev update(ZahtevDTO zahtev) throws Exception;

	@Async
	void notifyViaEmail(ZahtevDTO z, String obrazlozenje);

	void addZahtjev(Zahtjev p,int id);
}
