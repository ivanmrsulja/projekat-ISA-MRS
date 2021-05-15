package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;


public interface AdminService {
	Collection<Ponuda> findAllOffers();

	@Async
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo);

	public ArrayList<NarudzbenicaDTO> findOrdersForPharmacy(int idAdmina);

	@Async
	public void notifySuppliersViaEmail(Collection<PonudaDTO> offers);

	public void registerPricelist(CenovnikDTO cenovnikDTO, int idApoteke) throws Exception;

	public void updateStocks(int orderId, int adminId);

	public CenovnikDTO findPricelistForPharmacy(int pharmacyId);

	public void registerPromotion(TeloAkcijePromocije telo) throws Exception;

	public ArrayList<PonudaDTO> getOffersForPharmacy(int orderId);
	
	public void deleteProductFromPharmacy(int productId, int pharmacyId);
	
	public ArrayList<DostupanProizvodDTO> getProductsForPharmacy(int pharmacyId);
	
	public void updateStatusOfOrder(int orderId);
	
	public void updateStatusOfOffers(Collection<PonudaDTO> offers, int orderId, int offerId);
	
	public void addProductToPharmacy(PreparatDTO preparat, int pharmacyId, double price);

	public void registerOrder(NarudzbenicaDTO narudzbenicaDTO, int adminId);
	
	public ArrayList<PreparatDTO> getProductsOutsidePharmacy(int pharmacyId);
	
	public ArrayList<DostupanProizvodDTO> searhProductsOfPharmacy(int pharmacyId, String name);
}
