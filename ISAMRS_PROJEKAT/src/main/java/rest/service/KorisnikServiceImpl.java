package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Korisnik;
import rest.repository.KorisnikRepository;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	private KorisnikRepository korisnikRepository;
	
	@Autowired
	public KorisnikServiceImpl(KorisnikRepository imkr) {
		this.korisnikRepository = imkr;
	}

	@Override
	public Collection<Korisnik> findAll() {
		Collection<Korisnik> users = korisnikRepository.findAll();
		return users;
	}

	@Override
	public Korisnik findOne(int id) {
		Korisnik user = korisnikRepository.findById(id).get();
		return user;
	}

	@Override
	public Korisnik create(Korisnik user) throws Exception {
		if (user.getId() != 0) {
			throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
		}
		Korisnik savedUser = korisnikRepository.save(user);
		return savedUser;
	}

	@Override
	public Korisnik update(Korisnik user) throws Exception {
		Korisnik userToUpdate = findOne(user.getId());
		if (userToUpdate == null) {
			throw new Exception("Trazeni entitet nije pronadjen.");
		}
		userToUpdate.setIme(user.getIme());
		Korisnik updatedUSer = korisnikRepository.save(userToUpdate);
		return updatedUSer;
	}

	@Override
	public void delete(int id) {
		korisnikRepository.deleteById(id);
	}

}
