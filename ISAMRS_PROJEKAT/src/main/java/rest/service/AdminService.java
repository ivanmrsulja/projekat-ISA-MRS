package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Narudzbenica;
import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.dto.TipKorisnikaDTO;


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
	
	public void createType(TipKorisnikaDTO t);
	
	public Collection<NarudzbenicaDTO> getAvailableNarudzbenice(int id);
	
	public NarudzbenicaDTO getNarudzbenicaById(int id);
	
	public void createOffer(PonudaDTO p);
}
