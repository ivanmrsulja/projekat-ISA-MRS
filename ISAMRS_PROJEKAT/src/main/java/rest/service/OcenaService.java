package rest.service;

public interface OcenaService {
	
	int ocenjivaApoteka(int idApoteke, int idPacijenta);
	
	int ocenjivDermatolog(int idDermatologa, int idPacijenta);
	
	int ocenjivFarmaceut(int idFarmaceuta, int idPacijenta);
	
	int ocenjivPreparat(int idPreparata, int idPacijenta);
	
	void oceniApoteku(int idApoteke, int ocena, int idPacijenta) throws Exception;
	
	void oceniDermatologa(int idDermatologa, int idPacijenta, int ocena) throws Exception;
	
	void oceniFarmaceuta(int idFarmaceuta, int idPacijenta, int ocena) throws Exception;
	
	void oceniPreparat(int idPreparata, int idPacijenta, int ocena) throws Exception;
}
