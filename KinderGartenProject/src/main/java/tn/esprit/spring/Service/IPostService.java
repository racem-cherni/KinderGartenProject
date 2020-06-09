package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Post;

public interface IPostService {
	
	 
	Post addPost(Post p,Long userId);
	List <Post> test();
	void deletePost(String id);
	Post updatePost(Post p);
	Post retrievePost(Long id);
	void deleteAutoPost(String nbr);
	List <Post> retrieveAllPosts();
	Post  addTotalPoint(Long postId);
	public List<Post> getAllbestPostJPQL();
//	public int getRateByPostId(Post post);
	public List<Post> search(String recherche);
	public List<Post> retrieveAllPostsByPoints() ;
}
