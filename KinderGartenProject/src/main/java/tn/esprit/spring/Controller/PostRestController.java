package tn.esprit.spring.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Post;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.IPostRepository;
import tn.esprit.spring.Service.IPostService;

@RestController
public class PostRestController {
@Autowired
IPostService postService;
@Autowired
IPostRepository postRepo;
//affichage lazemch fih l id 
@GetMapping("/retrieve-all-posts")
@ResponseBody
public List<Post> getPosts() {
	List<Post> list = postService.retrieveAllPosts();
	return list;
 }

@GetMapping("/retrieve-post/{post-id}")
@ResponseBody
public Post retrievePost(@PathVariable("post-id") Long postId) {
	return postService.retrievePost(postId);
   }
//ajout date lezem automatique wfel ajout lezem nekhdhou esm mtaa luser li ajouta
@PostMapping("/add-post/{user-id}")
@ResponseBody
public Post addPost(@RequestBody Post p , @PathVariable("user-id") Long userId) {
	Post post = postService.addPost(p,userId);
	return post;
}
@DeleteMapping("/remove-post/{post-id}")
@ResponseBody
public void removePost(@PathVariable("post-id") String postId) {
	postService.deletePost(postId);
  }
@PutMapping("/modify-post")
@ResponseBody
public Post modifyPost(@RequestBody Post post) {
	return postService.updatePost(post);
	}
@GetMapping("/best-post")
@ResponseBody
public List<Post> getAllbestPostJPQL() {
	List<Post> list = postService.getAllbestPostJPQL();
	return list;
 }
String nbr="1";
@DeleteMapping("/remove-auto")
@ResponseBody
public void deleteAutoPost() {
	postService.deleteAutoPost(nbr);
  }
@GetMapping("/search")
public List<Post> searchPost(@RequestParam String title){
	return postRepo.findByTitle(title);
}
}
