package tn.esprit.spring.Controller;

import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Comment;

import tn.esprit.spring.Service.ICommentService;

@RestController
public class CommentRestController {
@Autowired
ICommentService commentService;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/all-comment/{postId}")
	@ResponseBody
	public ArrayList  CommentQuery(@PathVariable("postId") Long postId) {
		return commentService.CommentQuery(postId);
		 }
	
	
	@DeleteMapping("/remove-comment/{comment-id}")
	@ResponseBody
	public void removeComment(@PathVariable("comment-id") String commentId) {
		commentService.deleteComment(commentId);
	}
	@PostMapping("/add-comment/{user-id}/{post-id}")
	@ResponseBody
	public Comment addComment(@RequestBody Comment c , @PathVariable("user-id") Long userId, @PathVariable("post-id") Long postId) {
	Comment comment = commentService.addComment(userId, postId, c );
	return comment;
	}
	@PutMapping("/modify-comment")
	@ResponseBody
	public Comment modifyComment(@RequestBody Comment comment) {
	return commentService.updateComment(comment);
	}
	

	@PutMapping(value = "/modifyLike/{id}/{idCom}") 
	@ResponseBody
	public void addLike(@PathVariable("idCom") Long commentId,@PathVariable("id") Long userId) {
		
		commentService.addLike(commentId , userId);
	
	}
	@PutMapping(value = "/modifyDelike/{id}/{idCom}") 
	@ResponseBody
	public void addDesLike(@PathVariable("idCom") Long commentId,@PathVariable("id") Long userId) {
		
		commentService.addDesLike(commentId , userId);
	
	}
	@PutMapping(value = "/modifyLove/{id}/{idCom}") 
	@ResponseBody
	public void addLove(@PathVariable("idCom") Long commentId,@PathVariable("id") Long userId) {
		
		commentService.addLove(commentId , userId);
	
	}
	@PutMapping(value = "/modifyRire/{id}/{idCom}") 
	@ResponseBody
	public void addRire(@PathVariable("idCom") Long commentId,@PathVariable("id") Long userId) {
		
		commentService.addRire(commentId , userId);
	
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/funny-comment/{postId}")
	@ResponseBody
	public ArrayList  CommentFunny(@PathVariable("postId") Long postId) {
		return commentService.CommentFunny(postId);
		 }
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/pert-comment/{postId}")
	@ResponseBody
	public ArrayList  CommentPertinent(@PathVariable("postId") Long postId) {
		return commentService.CommentPertinentQuery(postId);
	}
	
	
	/*	@PutMapping("/modify-like/{user-id}")
	@ResponseBody
	public Comment modifyLike(@RequestBody Comment comment, @PathVariable("user-id") Long userId) {
	//	idUsersReacted.add(userId);
	return commentService.addLike(comment, userId);
	}*/
	
	
	
	
	
	
	
	
}
