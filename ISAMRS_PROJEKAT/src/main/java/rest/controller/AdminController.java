package rest.controller;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.domain.Ponuda;
import rest.dto.PonudaDTO;
import rest.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private AdminService adminService;
	
	@Autowired
	public AdminController(AdminService as) {
		this.adminService = as;
	}
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaDTO>> getOffers() {
		Collection<Ponuda> offers = adminService.findAllOffers();
		ArrayList<PonudaDTO> ponude = new ArrayList<PonudaDTO>();
		for(Ponuda p : offers) {
			ponude.add(new PonudaDTO(p));
		}
		return new ResponseEntity<Collection<PonudaDTO>>(ponude, HttpStatus.OK);
	}
}