package rest.controller;



import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Ponuda;
import rest.domain.Preparat;
import rest.dto.KorisnikDTO;
import rest.dto.PonudaDTO;
import rest.dto.PreparatDTO;
import rest.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private AdminService adminService;
	
	@Autowired
	public AdminController(AdminService as) {
		this.adminService = as;
	}
	
	@GetMapping(value = "cures/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getStatusPonuda(HttpServletRequest request, @PathVariable("status") String status) {
		KorisnikDTO u = (KorisnikDTO) request.getSession().getAttribute("user");
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			if(p.getDobavljac().getUsername().equals(u.getUsername())) {
				if(p.getStatus().toString().equals(status) || status.equals("SVI")) {
					ponude.add(new PonudaDTO(p));
				}

			}
		}
		return new ResponseEntity<Collection<PonudaDTO>>(ponude, HttpStatus.OK);
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getOffers(HttpServletRequest request) {
		KorisnikDTO u = (KorisnikDTO) request.getSession().getAttribute("user");
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			if(p.getDobavljac().getUsername().equals(u.getUsername())) {
				ponude.add(new PonudaDTO(p));
			}
		}
		return new ResponseEntity<Collection<PonudaDTO>>(ponude, HttpStatus.OK);
	}
}