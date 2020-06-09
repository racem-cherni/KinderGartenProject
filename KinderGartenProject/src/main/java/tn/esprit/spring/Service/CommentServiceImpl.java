package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ICommentRepository;
import tn.esprit.spring.repository.IPostRepository;
import tn.esprit.spring.repository.IRatingRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class CommentServiceImpl implements ICommentService{
	@Autowired
	ICommentRepository CommentRepository;
	@Autowired
	IPostRepository PostRepository;
	@Autowired
	IRatingRepository RatingRepository;
	
	@Autowired
	UserRepository UserRepository;
	private static final Logger L = LogManager.getLogger(CommentServiceImpl.class);
	

	@Override
	public Comment addComment(Long userId,Long postId,Comment c ) {
	
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
	    Post post = PostRepository.findById(postId).get();
	    UserApp user = UserRepository.findById(userId).get();
	  //  Comment c = new Comment();
	    c.setPosts(post);
	    c.setUseres(user);
	 	c.setDateComment(date);
	 	c.setLike(0);
	 	c.setLove(0);
	 	c.setRire(0);
	 	c.setDesLike(0);
//	 if(!(couleurs.contains(c.getContenuComment()))){
	 	return CommentRepository.save(c);}

	 	
	 	
//	}
/*	@Override
	public Comment addCommentCrud(Comment c) {
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
		Comment c1 = new Comment();
		c1.setDateComment(date);
	 	c1.setNbDeslike(0);
	 	c1.setNbRire(0);
	 	c1.setNbJadore(0);
	 	c1.setNbLike(0);
		return CommentRepository.save(c);
	}*/
	
	
	@Override
	public ArrayList  CommentQuery(Long postId) {
		
		return CommentRepository.CommentQuery(postId);
		
		
		
		
	}
	@Override
	public void deleteComment(String id) {
			CommentRepository.deleteById(Long.parseLong(id));
	}
	@Override
	public Comment updateComment(Comment c) {
		Comment commentToUpdate = CommentRepository.findById(c.getId()).get(); 			
		commentToUpdate.setContenuComment(c.getContenuComment());
		return CommentRepository.save(commentToUpdate);
	}
	// private ArrayList<Long> a1 = new ArrayList<Long>();
	int x=0;
	java.util.List<java.util.Map.Entry<Long,Long>> likeList= new java.util.ArrayList<>();

	@Override
	public void addLike(Long commentId ,Long userId) {
	//	Comment commentToUpdate = CommentRepository.findById(c.getId()).get(); 
	//	if (!(a1.contains(userId))){
//		java.util.Map.Entry<Long,Long> pair1=new java.util.AbstractMap.SimpleEntry<>(commentId,userId);
//		if(!(likeList.contains(pair1))){
		 CommentRepository.addLikeJPQL(userId,commentId);
	//	 likeList.add(pair1);
	//	 System.out.println("notre list++++++++++++++"+likeList);
//		 }

		// a1.add(userId);
	//	 }
	//	idUsersReacted.add(userId);
	}
	java.util.List<java.util.Map.Entry<Long,Long>> deslikeList= new java.util.ArrayList<>();

	@Override
	public void addDesLike(Long commentId, Long userId) {
//		java.util.Map.Entry<Long,Long> pair2=new java.util.AbstractMap.SimpleEntry<>(commentId,userId);
//		if(!(likeList.contains(pair2))){
		 CommentRepository.addDesLikeJPQL(userId,commentId);
//		 likeList.add(pair2);
	//	 System.out.println("notre list++++++++++++++"+likeList);
	//	 }
		
	}
	java.util.List<java.util.Map.Entry<Long,Long>> LoveList= new java.util.ArrayList<>();
	@Override
	public void addLove(Long commentId, Long userId) {
	//	java.util.Map.Entry<Long,Long> pair3=new java.util.AbstractMap.SimpleEntry<>(commentId,userId);
//		if(!(LoveList.contains(pair3))){
		 CommentRepository.addLoveJPQL(userId,commentId);
	//	 LoveList.add(pair3);
	//	 System.out.println("notre list++++++++++++++"+LoveList);
		 }
		
//	}
	java.util.List<java.util.Map.Entry<Long,Long>> RireList= new java.util.ArrayList<>();
	@Override
	public void addRire(Long commentId, Long userId) {
	//	java.util.Map.Entry<Long,Long> pair4=new java.util.AbstractMap.SimpleEntry<>(commentId,userId);
	//	if(!(RireList.contains(pair4))){
		 CommentRepository.addRireJPQL(userId,commentId);
	//	 RireList.add(pair4);
	//	 System.out.println("notre list++++++++++++++"+RireList);
	//	 }
	}
	
	@Override
	public ArrayList  CommentFunny(Long postId) {
		
		return CommentRepository.CommentFunny(postId);
	
	}
	
	@Override
	public ArrayList CommentPertinentQuery(Long postId) {
		return CommentRepository.CommentPertinent(postId);
	}



	@Override
	public List<Comment> allComments(Long postId) {
		// TODO Auto-generated method stub
		return CommentRepository.allComments(postId);
	}
	@Override
	public List<Comment> allCommentsSorted(Long postId) {
		// TODO Auto-generated method stub
		return CommentRepository.CommentSorted(postId);
	}


	@Override
	public void removeLike(Long commentId, Long userId) {
		// TODO Auto-generated method stub
		CommentRepository.removeLikeJPQL(userId, commentId);
	}



	@Override
	public void removeDesLike(Long commentId, Long userId) {
		// TODO Auto-generated method stub
		CommentRepository.removeDesLikeJPQL(userId, commentId);
	}



	@Override
	public void removeLove(Long commentId, Long userId) {
		// TODO Auto-generated method stub
		CommentRepository.removeLoveJPQL(userId, commentId);
	}



	@Override
	public void removeRire(Long commentId, Long userId) {
		// TODO Auto-generated method stub
		CommentRepository.removeRireJPQL(userId, commentId);
	}
}
