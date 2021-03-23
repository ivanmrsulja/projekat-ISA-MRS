package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.dto.KorisnikDTO;
import rest.repository.DermatologRepository;

@Service
public class DermatologServiceImpl implements DermatologService {

	private DermatologRepository dermatologRepository;
	
	@Autowired
	public DermatologServiceImpl(DermatologRepository imdr) {
		this.dermatologRepository = imdr;
	}

	@Override
	public Collection<Dermatolog> findAll() {
		Collection<Dermatolog> users = dermatologRepository.findAll();
		return users;
	}

	@Override
	public Dermatolog findOne(int id) {
		Dermatolog user = dermatologRepository.findById(id).get();
		return user;
	}

	@Override
	public Korisnik create(Dermatolog user) throws Exception {
		if (user.getId() != 0) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Korisnik savedUser = dermatologRepository.save(user);
		return savedUser;
	}

	@Override
	public Dermatolog update(KorisnikDTO user) throws Exception {
		Dermatolog userToUpdate = findOne(user.getId());
		
		
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		userToUpdate.setPrezime(user.getPrezime());
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setTelefon(user.getTelefon());
		userToUpdate.setLokacija(user.getLokacija());
		
		Dermatolog updatedUSer = dermatologRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		dermatologRepository.deleteById(id);
	}

}
