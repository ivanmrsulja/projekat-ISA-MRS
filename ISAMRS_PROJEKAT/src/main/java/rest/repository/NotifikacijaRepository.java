package rest.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rest.domain.Notifikacija;

@Repository
public interface NotifikacijaRepository extends JpaRepository<Notifikacija, Integer>{

	@Query("select n from Notifikacija n where n.apoteka.id = ?1")
	public ArrayList<Notifikacija> getAllForPharmacy(int pharmacyId);

	@Transactional
	@Modifying
	@Query("update Notifikacija n set n.pregledana = true where n.apoteka.id = ?1")
	public void updateNotificationsStatusesForPharmacy(int pharmacyId);

	@Transactional
	@Modifying
	@Query("delete from Notifikacija n where n.korisnik.id = ?1")
	public void deleteNotificationsOfUser(int pharmacistId);

}
