package rest.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rest.domain.Ponuda;
import rest.repository.PonudaRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	private PonudaRepository ponudaRepository;
	
	@Autowired
	public AdminServiceImpl(PonudaRepository imar) {
		this.ponudaRepository = imar;
	}

	@Override
	public Collection<Ponuda> findAllOffers() {
		Collection<Ponuda> offers = ponudaRepository.findAll();
		return offers;
	}

		
}
