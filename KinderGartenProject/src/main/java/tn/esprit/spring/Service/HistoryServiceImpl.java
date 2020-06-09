package tn.esprit.spring.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Comment;
import tn.esprit.spring.entities.History;
import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ICommentRepository;
import tn.esprit.spring.repository.IHistoryRepository;
import tn.esprit.spring.repository.IPostRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class HistoryServiceImpl implements IHistoryService{
	@Autowired
	IHistoryRepository hr;
	@Autowired
	UserRepository UserRepository;
	@Autowired
	IPostRepository PostRepository;
	@Autowired
	ICommentRepository CommentRepository;
	private static final Logger L = LogManager.getLogger(HistoryServiceImpl.class);
	
	@Override
	public History addHistory(History h,Long userId,Long commentId) {
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
	   UserApp user = UserRepository.findById(userId).get();
	   h.setUsers(user);
	//   Post postToUpdate = PostRepository.findById(p.getId()).get(); 	
	 //   Optional<Comment> comment = CommentRepository.findById(commentId);
	  
	  Comment comment = CommentRepository.findById(commentId).get();
		h.setDateHistory(date);
		h.setComments(comment);
		
		return hr.save(h);
	
	}
	@Override
	public void deleteReact(Long userId,Long postId){
		 hr.deleteHistoryLike(userId, postId);
		
		
	}
	@Override
	public History addHistoryPost(History h,Long userId) {
		Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
	   UserApp user = UserRepository.findById(userId).get();
	   h.setUsers(user);
	//   Post postToUpdate = PostRepository.findById(p.getId()).get(); 	
	 //   Optional<Comment> comment = CommentRepository.findById(commentId);
	  
	//  Comment comment = CommentRepository.findById(commentId).get();
		h.setDateHistory(date);
		
		
		return hr.save(h);
	
	}
	
}
