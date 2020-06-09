package tn.esprit.spring.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.Rate;
import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.Service.IRatingService;
@RestController
public class RatingRestController {
	@Autowired
	IRatingService ratingservice;
	
	@GetMapping("/retrieve-rates")
	@ResponseBody
	public List<Rating> getRating() {
		List<Rating> list = ratingservice.AllRates();
		return list;
	 }
	// http://localhost:8081/SpringMVC/servlet/ajouterTimesheet
    //{"missionId":1,"employeId":2,"dateDebut":"2020-03-01","dateFin":"2021-03-01"}
	
	@PostMapping("/ajouterRating/{iduser}/{idpost}/{rate}")
	@ResponseBody
	public void addRate(@PathVariable("iduser") Long userId, @PathVariable("idpost") Long postId, @PathVariable("rate") Rate r) {
		ratingservice.addRate(userId, postId, r);

	}

}
