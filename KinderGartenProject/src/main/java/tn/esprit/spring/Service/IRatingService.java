package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Rate;
import tn.esprit.spring.entities.Rating;

public interface IRatingService {
	public void addRate(Long userId,Long postId,Rate r );
	public List <Rating>AllRates();
//	public int getRateByPostIdJPQL(Long postId);
	public float sommeParPost(Long postId);
	public String findByPostUser(Long userId,Long postId);
}
