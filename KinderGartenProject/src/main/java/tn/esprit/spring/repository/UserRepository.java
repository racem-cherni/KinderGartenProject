package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.UserApp;



public interface UserRepository extends JpaRepository<UserApp,Long> {
	
	@Query("select u from UserApp u where u.username= :username ")
	public UserApp findByUsername(@Param("username")String username);
	
	@Query("select u from UserApp u where u.parent= :parent ")
	public UserApp findUserByParent(@Param("parent")Parent parent);
	
	
	//@Query("select u.role from UserApp u where username= :x  ")
	//public Role findByRole(@Param("x") String username);
	
	@Transactional
	 @Modifying
	 @Query("UPDATE UserApp u set u.actived=false where u.point=0")
	    int updateUserPoint();
	
	
	@Transactional
	 @Modifying
	 @Query("UPDATE UserApp u set u.score=u.score+ :score where u.kindergarten= :kinder")
	    int updateUserScore(@Param("score") int score,@Param("kinder")KinderGarten kinder);
	
	
	@Query("select u.score from UserApp u where u.kindergarten= :kinder")
	public int findByKindergarten(@Param("kinder")KinderGarten kinder);
	
	@Query("select u from UserApp u where u.kindergarten= :kinder")
	public UserApp findByKinder(@Param("kinder")KinderGarten kinder);
	
	@Query("select u from UserApp u where u.username= :username ")
	public UserApp findByUsernametest(@Param("username")String username);
}
 