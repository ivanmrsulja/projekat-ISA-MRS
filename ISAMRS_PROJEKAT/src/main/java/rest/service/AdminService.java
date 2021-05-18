package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;


public interface AdminService {
	Collection<Ponuda> findAllOffers();

	@Async
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo);

	public ArrayList<NarudzbenicaDTO> findOrdersForPharmacy(int idAdmina);

	@Async
	public void notifySuppliersViaEmail(Collection<PonudaDTO> offers);

	public void registerPricelist(CenovnikDTO cenovnikDTO, int idApoteke) throws Exception;

	public void updateStocks(int orderId, int adminId);
	
	public void updateZalba(int id);
}
