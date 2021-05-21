package rest.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.Pregled;
import rest.domain.Zaposlenje;
import rest.dto.ApotekaDTO;
import rest.dto.CenovnikDTO;
import rest.dto.DermatologDTO;
import rest.dto.DostupanProizvodDTO;
import rest.dto.IzvestajValueDTO;
import rest.dto.NarudzbenicaDTO;
import rest.dto.NotifikacijaDTO;
import rest.dto.PonudaDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;


public interface AdminService {
	Collection<Ponuda> findAllOffers();

	@Async
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, String text);

	public ArrayList<NarudzbenicaDTO> findOrdersForPharmacy(int idAdmina);

	@Async
	public void notifySuppliersViaEmail(Collection<PonudaDTO> offers);

	public void registerPricelist(CenovnikDTO cenovnikDTO, int idApoteke) throws Exception;

	public void updateStocks(int orderId, int adminId);

	public CenovnikDTO findPricelistForPharmacy(int pharmacyId);

	public void registerPromotion(CenovnikDTO pricelistDTO, int adminId, String text) throws Exception;

	public ArrayList<PonudaDTO> getOffersForPharmacy(int orderId);
	
	public void deleteProductFromPharmacy(int productId, int pharmacyId);
	
	public ArrayList<DostupanProizvodDTO> getProductsForPharmacy(int pharmacyId);
	
	public void updateStatusOfOrder(int orderId);
	
	public void updateStatusOfOffers(Collection<PonudaDTO> offers, int orderId, int offerId);
	
	public void addProductToPharmacy(PreparatDTO preparat, int pharmacyId, double price);

	public void registerOrder(NarudzbenicaDTO narudzbenicaDTO, int adminId);
	
	public ArrayList<PreparatDTO> getProductsOutsidePharmacy(int pharmacyId);
	
	public ArrayList<DostupanProizvodDTO> searhProductsOfPharmacy(int pharmacyId, String name);

	public ArrayList<IzvestajValueDTO> getYearlyExaminations(int year, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getQuarterlyExaminations(int year, int quarter, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getMonthlyExaminations(int year, int month, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getYearlyIncome(int year, int pharmacyId);
	
	public ArrayList<IzvestajValueDTO> getQuarterlyIncome(int year, int quarter, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getMonthlyIncome(int year, int month, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getYearlyUsage(int year, int pharmacyId);

	public ArrayList<IzvestajValueDTO> getQuarterlyUsage(int year, int quarter, int pharmacyId);
	
	public ArrayList<IzvestajValueDTO> getMonthlyUsage(int year, int month, int pharmacyId);

	public ArrayList<NotifikacijaDTO> getNotificationsForPharmacy(int pharmacyId);

	public void updatePharmacyNotifications(int pharmacyId);

	public ArrayList<PregledDTO> getOpenExaminationsForPharmacy(int pharmacyId);
	
	public void updateExaminationPrice(PregledDTO examinationDTO);

	public void deleteExamination(int examinationId);
	
	public Pregled registerExamination(int dermatologistId, int pharmacyId, PregledDTO examinationDTO);

	public void deleteOutdatedPromotion();

	public Zaposlenje employDermatologist(int pharmacyId, DermatologDTO dermatologistDTO);

	public ArrayList<DermatologDTO> getDermatologistsOutsidePharmacy(int pharmacyId);

	public Collection<Pregled> scheduledAppointmentsForDermatologist(int pharmacyId, int dermatologistId);

	public void removeDermatologist(int pharmacyId, int dermatologistId);
}
