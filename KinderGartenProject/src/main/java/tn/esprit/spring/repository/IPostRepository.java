package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Post;

public interface IPostRepository extends CrudRepository<Post, Long>{
	@Query(value = "SELECT * FROM t_Post WHERE total_Points = (SELECT MAX(total_Points) FROM t_Post)" , nativeQuery= true)
	public List<Post> bestPost();
	
	@Query("select p from Post p where title like %?1%")
	public List<Post> findByTitle(String title);
	
	@Query(value ="SELECT DISTINCT * FROM t_post  ORDER BY date_post DESC" , nativeQuery= true)
	public List<Post>allPostByDate();
	
	@Query(value ="SELECT DISTINCT * FROM t_post  ORDER BY total_points DESC" , nativeQuery= true)
	public List<Post>allPostByPoint();
	

/*@Query("SELECT p FROM Post p WHERE  p.totalPoints=MAX(p.totalPoints)")
public List<Post> bestPost();*/
}
