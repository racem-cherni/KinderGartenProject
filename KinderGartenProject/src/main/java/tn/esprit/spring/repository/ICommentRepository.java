package tn.esprit.spring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Comment;



//import tn.esprit.spring.entity.Comment;

public interface ICommentRepository extends CrudRepository<Comment, Long> {
	
	@Query(value = "SELECT DISTINCT t_post.date_post,t_post.post_title, t_post.post_picture,"
			+ "req.pers, comment_contenu, comment_like, comment_deslike,comment_love,comment_rire"
			+ " from t_comment INNER JOIN t_post on t_comment.posts_post_id = t_post.post_id"
			+ " INNER Join (SELECT t_user.user_name As pers ,t_comment.useres_user_id As com"
			+ " FROM t_comment INNER JOIN t_user on t_comment.useres_user_id = t_user.user_id)"
			+ " As req on req.com = t_comment.useres_user_id WHERE t_comment.posts_post_id =:postId" , nativeQuery= true)
			public ArrayList  CommentQuery(@Param("postId")Long postId);
	
	@Query(value ="SELECT DISTINCT * FROM t_comment WHERE posts_post_id=:postId ORDER BY date_comment DESC" , nativeQuery= true)
	public List<Comment>allComments(@Param("postId")Long postId);
	
	@Query(value ="SELECT DISTINCT * FROM t_comment WHERE posts_post_id=:postId ORDER BY(comment_like + comment_love + comment_rire + comment_deslike) DESC" , nativeQuery= true)
	public List<Comment>CommentSorted(@Param("postId")Long postId);
	
			
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_like = 1+ comment_like where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void addLikeJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);
	
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_like = comment_like-1 where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void removeLikeJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);
	
	
	
	
	/*@Modifying
    @Transactional
    @Query("UPDATE Comment c set c.like = 1+c.like where (SELECT u FROM User u where u.id.id=?1) and c.id.id=?1")
    public void addLikeJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);*/
	
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_deslike = 1+ comment_deslike where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void addDesLikeJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_deslike = comment_deslike-1 where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void removeDesLikeJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);

	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_love = 1+ comment_love where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void addLoveJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_love =comment_love-1 where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void removeLoveJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);

	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_rire = 1+ comment_rire where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void addRireJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);
	@Modifying
    @Transactional
    @Query(value ="UPDATE t_comment SET comment_rire = comment_rire-1 where (SELECT user_id FROM t_user where t_user.user_id=:userId) and comment_id=:commentId", nativeQuery= true)
    public void removeRireJPQL(@Param("userId")Long userId , @Param("commentId")Long commentId);

	
	@Query(value = "SELECT DISTINCT req.pers, comment_contenu, comment_like, comment_deslike,"
	  +"comment_love,comment_rire from t_comment INNER JOIN t_post on t_comment.posts_post_id = t_post.post_id "
	  +"INNER Join (SELECT t_user.user_name As pers ,t_comment.useres_user_id As com FROM t_comment "
	  +"INNER JOIN t_user on t_comment.useres_user_id = t_user.user_id) As req on req.com = t_comment.useres_user_id "
	  +"WHERE (`comment_rire`= (SELECT MAX(`comment_rire`)from t_comment)) and t_comment.posts_post_id =:postId" , nativeQuery= true)
			public ArrayList  CommentFunny(@Param("postId")Long postId);
	
	@Query(value = "SELECT DISTINCT req.pers, comment_contenu, comment_like, comment_deslike,"
	  +"comment_love,comment_rire from t_comment INNER JOIN t_post on t_comment.posts_post_id = t_post.post_id "
	  +"INNER Join (SELECT t_user.user_name As pers ,t_comment.useres_user_id As com FROM t_comment "
	  +"INNER JOIN t_user on t_comment.useres_user_id = t_user.user_id) As req on req.com = t_comment.useres_user_id "
	  +"WHERE t_comment.posts_post_id =:postId ORDER BY comment_like DESC ", nativeQuery= true)
			public ArrayList  CommentPertinent(@Param("postId")Long postId);
	
	//SELECT `comment_contenu`,`comment_like`, t_post.post_title,t_post.post_picture,t_post.date_post from t_comment JOIN t_post on t_comment.posts_post_id = t_post.post_id WHERE t_comment.posts_post_id = 18

}
