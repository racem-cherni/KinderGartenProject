package tn.esprit.spring.Service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.Rate;
import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.RatingPk;
import tn.esprit.spring.repository.IPostRepository;
import tn.esprit.spring.repository.IRatingRepository;

@Service
public class RatingServiceImpl implements IRatingService {
	@Autowired
	IRatingRepository RatingRepository;
	@Autowired
	IPostRepository PostRepository;
	private static final Logger L = LogManager.getLogger(RatingServiceImpl.class);

	@Override
	public void addRate(Long userId, Long postId, Rate r) {
		List<Rating> ratings = (List<Rating>) RatingRepository.findAll();
		for (Rating rat : ratings) {
		 if(!(rat.getUser().equals(userId)&&(rat.getPost().equals(postId))))
		   {
			RatingPk ratingPk = new RatingPk();
			ratingPk.setIdPost(postId);
			ratingPk.setIdUser(userId);
			Rating rating = new Rating();
			rating.setRatingPk(ratingPk);
			rating.setRate(r);
			RatingRepository.save(rating);}
		 
		
	}
		System.out.println("Tu as deja noter!");}

	@Override
	public List<Rating> AllRates() {
		List<Rating> rates = (List<Rating>) RatingRepository.findAll();
		for (Rating ra : rates) {
			L.info("Ratesss Are +++++++ : " + ra);
		}
		return rates;
	}

	// @Override
	// public int getRateByPostIdJPQL(Long postId) {
	// return RatingRepository.getRateByPostId(postId);
	// }
	@Override
	public float sommeParPost(Long postId) {
		List<Rating> rates = (List<Rating>) RatingRepository.findAll();
		float nb=(float)0 ;
		//Post postToUpdateTotalPoint = PostRepository.findById(postId).get();
		float nbToUpdate=(float)0;
		float x=(float)0;
		for (Rating ra : rates) {
			
			if ((ra.getPost().getId()).equals(postId)) {
				// int x=ra.getRate().ordinal();
				
				
				String rat = ra.getRate().toString();
				
				if (rat.equals("TERRIBLE")) {
					nb = (float)0;
				} else if (rat.equals("POOR")) {
					nb = (float)1;
				} else if (rat.equals("AVERAGE")) {
					nb = (float)2;
				} else if (rat.equals("GOOD")) {
					nb = (float)3;
				} else if (rat.equals("EXCELLENT")) {
					nb = (float)4;
				}
				
				
				nbToUpdate+=nb;
				x+=1;
			//	L.info("nombre de fois+++++++ "+x);
			}

		}
		L.info("le total de cette post"+(float)(nbToUpdate/x));

		return (float)(nbToUpdate/x);

	}

	@Override
	public String findByPostUser(Long userId, Long postId) {
		return  (RatingRepository.takeRate(userId, postId));}


}