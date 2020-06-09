package tn.esprit.spring.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.IPostRepository;
import tn.esprit.spring.repository.IRatingRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PostServiceImpl implements IPostService{
	@Autowired
	IPostRepository PostRepository;
	@Autowired
	IRatingRepository RatingRepository;
	@Autowired
	IRatingService ratingservice;
	@Autowired
	UserRepository UserRepository;
	
	private static final Logger L = LogManager.getLogger(PostServiceImpl.class);
	@Override
	public Post addPost(Post p,Long userId) {
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
	    UserApp user = UserRepository.findById(userId).get();
	    p.setUsers(user);
	 	p.setDatePost(date);
		p.setTotalPoints(0);
		
		return PostRepository.save(p);
		
		
	}
	@Override
	public List<Post> retrieveAllPosts() {
		List<Post> posts = (List<Post>) PostRepository.allPostByDate();
		for (Post post : posts ) {
			L.info("Posts Are +++++++ : " + post);
		}
		return posts;
	}
	@Override
	public List<Post> retrieveAllPostsByPoints() {
		List<Post> posts = (List<Post>) PostRepository.allPostByPoint();
		for (Post post : posts ) {
			L.info("Posts Are +++++++ : " + post);
		}
		return posts;
	}
	
	
	@Override
	public void deletePost(String id) {
		PostRepository.deleteById(Long.parseLong(id));
		
	}
		
	
	@Override
	public Post updatePost(Post p) {
		Post postToUpdate = PostRepository.findById(p.getId()).get(); 			
		postToUpdate.setTitle(p.getTitle());
		postToUpdate.setPicture(p.getPicture());
		
		return PostRepository.save(postToUpdate);
	}
	@Override
	public Post retrievePost(Long id) {
		Post p = new Post();
		p=PostRepository.findById(id).get();
	//	u=UserRepository.findById(id).orElse(null);
		L.info("the badddddd one" + p);
		  return p;
	}
	
	@Override
	public void deleteAutoPost(String nbr) {
		List<Post> posts = (List<Post>) PostRepository.findAll();
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
		for (Post post : posts) {
			if ((post.getTotalPoints()< Long.parseLong(nbr))&&((date.getTime()-post.getDatePost().getTime())/1000)>691200){
				deletePost(Long.toString(post.getId()));
			}
		}
	}
	@Override
	public List<Post> test() {
		List<Post> posts = (List<Post>) PostRepository.findAll();
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
		for (Post post : posts) {
			long delai = (date.getTime() - post.getDatePost().getTime()) / 1000;
			L.info("Posts Are +++++++ : " + delai);
		}
		return posts;
		
	}
	

		
	

	@Override
	public Post addTotalPoint(Long postId) {
		float x = ratingservice.sommeParPost(postId);
		//int x= 4;
		L.info("AAAAAAAAAAAAAAAAAAAA++++++++++++"+x);
		Post postToUpdateTotalPoint = PostRepository.findById(postId).get();
		
		postToUpdateTotalPoint.setTotalPoints(x);
		return PostRepository.save(postToUpdateTotalPoint);
	}
	@Override
	public List<Post> getAllbestPostJPQL() {
		return PostRepository.bestPost();
		
		
	}
/*	public List<Post> search(String recherche) {
        String rechNonSpace = recherche.replaceAll("\\s+", "");
        String parames = "1=0";
        String[] parts = recherche.split(" ");
        if (rechNonSpace.length() > 0) {
            for (int i = 0; i < parts.length; i++) {
                parames = parames + " or c.title LIKE :param" + i;

            }
        }
        SessionFactory.getCurrentSession().beginTransaction();
        Query query = SessionFactory.getCurrentSession().createQuery("  from Post c where " + parames + " order by c.datePost desc");
        if (rechNonSpace.length() > 0) {
            for (int i = 0; i < parts.length; i++) {

                query.setParameter("param" + i, "%" + parts[i] + "%");
            }
        }
        return query.list();
    }
	*/
	@Override
	public List<Post> search(String recherche) {
		
		return PostRepository.findByTitle(recherche);
	}
	
	
	
	}
