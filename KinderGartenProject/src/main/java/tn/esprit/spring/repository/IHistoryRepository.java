package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.History;

public interface IHistoryRepository extends CrudRepository<History, Long> {
	//"DELETE FROM `t_history` WHERE (`history_react` LIKE "like" AND `postess_post_id`=128 AND `users_user_id`=4)"
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM t_history WHERE (comments_comment_id =:post AND users_user_id=:user)", nativeQuery= true)
	void deleteHistoryLike (@Param("user") Long user ,@Param("post") Long post  );
}
