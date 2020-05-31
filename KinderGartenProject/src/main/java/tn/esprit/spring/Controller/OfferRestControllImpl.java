package tn.esprit.spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.Service.OfferService;


@RestController
public class OfferRestControllImpl {

	@Autowired
	OfferService OfferService;

	@PostMapping("/add-offer")
	@ResponseBody
	public Offer addOffer(@RequestBody Offer p) {
		Offer offer = OfferService.addOffer(p);
		return offer;
	}

	@PutMapping("/update-offer")
	@ResponseBody
	public Offer updateOffer(@RequestBody Offer p) {
		Offer offer = OfferService.updateOffer(p);
		return offer;
	}

	@DeleteMapping("/remove-offer/{id}")
	@ResponseBody
	public void deleteOfferById(@PathVariable("id") int id) {
		OfferService.deleteOffer(id);
	}

	@GetMapping("/retrieve-offer/{id}")
	@ResponseBody
	public Offer retrieveUser(@PathVariable("id") int id) {
		return OfferService.retrieveOffer(id);
	}

	@GetMapping("/retrieve-all-offer")
	@ResponseBody
	public List<Offer> getUsers() {
		List<Offer> list = OfferService.retrieveAllOffers();
		return list;

	}


}