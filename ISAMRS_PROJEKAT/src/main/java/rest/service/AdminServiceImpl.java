package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Korisnik;
import rest.domain.Ponuda;
import rest.repository.InMemoryAdminRepository;
import rest.repository.InMemoryKorisnikRepository;

@Service
public class AdminServiceImpl implements AdminService {

	private InMemoryAdminRepository adminRepository;
	
	@Autowired
	public AdminServiceImpl(InMemoryAdminRepository imar) {
		this.adminRepository = imar;
	}

	@Override
	public Collection<Ponuda> findAllOffers() {
		// TODO Auto-generated method stub
		Collection<Ponuda> offers = adminRepository.findAllOffers();
		return offers;
	}

		
}
