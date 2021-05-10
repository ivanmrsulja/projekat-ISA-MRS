package rest.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import rest.domain.Pacijent;
import rest.domain.Ponuda;
import rest.domain.TeloAkcijePromocije;
import rest.dto.ApotekaDTO;
import rest.repository.PonudaRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	private PonudaRepository ponudaRepository;

	private Environment env;
	private JavaMailSender javaMailSender;
	
	@Autowired
	public AdminServiceImpl(PonudaRepository imar, Environment env, JavaMailSender jms) {
		this.ponudaRepository = imar;
		this.env = env;
		this.javaMailSender = jms;
	}

	@Override
	public Collection<Ponuda> findAllOffers() {
		Collection<Ponuda> offers = ponudaRepository.findAll();
		return offers;
	}

	@Override
	public void notifyPatientViaEmail(ApotekaDTO apoteka, Pacijent pacijent, TeloAkcijePromocije telo) {
		SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(pacijent.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Nove akcije i promocije apoteke " + apoteka.getNaziv());
        String teloString = telo.getTekst();
        mail.setText(teloString);
        javaMailSender.send(mail);
	}

		
}
