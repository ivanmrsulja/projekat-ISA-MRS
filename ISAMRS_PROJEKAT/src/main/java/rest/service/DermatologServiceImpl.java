package rest.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Dermatolog;
import rest.domain.Korisnik;
import rest.domain.Pregled;
import rest.domain.Preparat;
import rest.domain.StatusPregleda;
import rest.dto.KorisnikDTO;
import rest.dto.PregledDTO;
import rest.dto.PreparatDTO;
import rest.repository.DermatologRepository;
import rest.repository.PregledRepository;
import rest.repository.PreparatRepository;

@Service
@Transactional
public class DermatologServiceImpl implements DermatologService {

	private DermatologRepository dermatologRepository;
	private PregledRepository pregledRepository;
	private PreparatRepository preparatRepository;
	
	@Autowired
	public DermatologServiceImpl(DermatologRepository imdr,PreparatRepository pr,PregledRepository pp) {
		this.dermatologRepository = imdr;
		this.preparatRepository=pr;
		this.pregledRepository=pp;
	}

	@Override
	public Collection<Dermatolog> findAll() {
		Collection<Dermatolog> users = dermatologRepository.findAll();
		return users;
	}

	@Override
	public Dermatolog findOne(int id) {
		Optional<Dermatolog> userOpt = dermatologRepository.findById(id);
		if (userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
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

		Optional<Pregled> pOpt = pregledRepository.findById(id);
		Pregled p = null;
		if (pOpt.isPresent()) {
			p = pOpt.get();
		}
		else {
			return;
		}

		p.setIzvjestaj(pregled.getIzvjestaj());
		p.setStatus(StatusPregleda.ZAVRSEN);
		
		for(PreparatDTO pr : pregled.getTerapija()) {
			Preparat tmp=preparatRepository.getOne(pr.getId());
			p.getTerapija().add(tmp);
		}
		pregledRepository.save(p);
	}
}