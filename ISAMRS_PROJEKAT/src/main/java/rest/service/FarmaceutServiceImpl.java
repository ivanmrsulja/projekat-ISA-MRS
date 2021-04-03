package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Farmaceut;
import rest.domain.Korisnik;
import rest.dto.KorisnikDTO;
import rest.repository.FarmaceutRepository;

@Service
public class FarmaceutServiceImpl implements FarmaceutService {

	private FarmaceutRepository farmaceutRepository;
	
	@Autowired
	public FarmaceutServiceImpl(FarmaceutRepository imfr) {
		this.farmaceutRepository = imfr;
	}

	@Override
	public Collection<Farmaceut> findAll() {
		Collection<Farmaceut> users = farmaceutRepository.findAll();
		return users;
	}

	@Override
	public Farmaceut findOne(int id) {
		Farmaceut user = farmaceutRepository.findById(id).get();
		return user;
	}

	@Override
	public Korisnik create(Farmaceut user) throws Exception {
		if (user.getId() != 0) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Korisnik savedUser = farmaceutRepository.save(user);
		return savedUser;
	}

	@Override
	public Farmaceut update(KorisnikDTO user) throws Exception {
		Farmaceut userToUpdate = findOne(user.getId());
		
		
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setTelefon(user.getTelefon());
		userToUpdate.setLokacija(user.getLokacija());
		
		Farmaceut updatedUSer = farmaceutRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		farmaceutRepository.deleteById(id);
	}

	@Override
	public Collection<Farmaceut> findAllForPharmacy(int id) {
		return farmaceutRepository.getWithEmployments(id);
	}

}