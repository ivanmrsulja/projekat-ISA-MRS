package rest.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.domain.Pregled;
import rest.domain.StatusPregleda;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.repository.DermatologRepository;
import rest.repository.PregledRepository;

@Service
@Transactional
public class DermatologServiceImpl implements DermatologService {

	private DermatologRepository dermatologRepository;
	private PregledRepository pregledRepository;
	
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

	@Override
	public Collection<Dermatolog> findAllForPharmacy(int id) {
		return dermatologRepository.getWithEmployments(id);
	}
	@Override
	public void zavrsi(PregledDTO pregled, int id){
		Pregled p=pregledRepository.findById(id).get();
		p.setIzvjestaj(pregled.getIzvjestaj());
		p.setStatus(StatusPregleda.ZAVRSEN);
		pregledRepository.save(p);
	}
}