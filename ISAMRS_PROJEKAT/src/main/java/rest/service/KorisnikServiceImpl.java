package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Korisnik;
import rest.repository.InMemoryKorisnikRepository;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	private InMemoryKorisnikRepository korisnikRepository;
	
	@Autowired
	public KorisnikServiceImpl(InMemoryKorisnikRepository imkr) {
		this.korisnikRepository = imkr;
	}

	@Override
	public Collection<Korisnik> findAll() {
		Collection<Korisnik> users = korisnikRepository.findAll();
		return users;
	}

	@Override
	public Korisnik findOne(int id) {
		Korisnik user = korisnikRepository.findOne(id);
		return user;
	}

	@Override
	public Korisnik create(Korisnik user) throws Exception {
		if (user.getId() != 0) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Korisnik savedUser = korisnikRepository.create(user);
		return savedUser;
	}

	@Override
	public Korisnik update(Korisnik user) throws Exception {
		Korisnik userToUpdate = findOne(user.getId());
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		Korisnik updatedUSer = korisnikRepository.create(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		korisnikRepository.delete(id);
	}

}
