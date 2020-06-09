package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.spring.entities.Comment;


public interface ICommentService {
	public Comment addComment(Long userId,Long postId,Comment c);
//	Comment addCommentCrud(Comment c);
	public ArrayList  CommentQuery(Long postId);
	public void deleteComment(String id);
	public Comment updateComment(Comment c);
	
	
	public void addLike(Long commentId ,Long userId);
	public void addDesLike(Long commentId ,Long userId);
	public void addLove(Long commentId ,Long userId);
	public void addRire(Long commentId ,Long userId);
	public ArrayList  CommentFunny(Long postId);
	public ArrayList  CommentPertinentQuery(Long postId);
//	public int adddd(Comment c);
	public List<Comment> allComments(Long postId);
	public void removeLike(Long commentId ,Long userId);
	public void removeDesLike(Long commentId ,Long userId);
	public void removeLove(Long commentId ,Long userId);
	public void removeRire(Long commentId ,Long userId);
	public List<Comment> allCommentsSorted(Long postId);
}
